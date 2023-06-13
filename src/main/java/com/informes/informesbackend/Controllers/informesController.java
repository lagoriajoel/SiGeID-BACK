package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.*;
import com.informes.informesbackend.Models.Entities.EntitiesDTO.InformesDTO;
import com.informes.informesbackend.Services.AlumnoService;
import com.informes.informesbackend.Services.informeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController()
@RequestMapping("/informes")
@CrossOrigin("*")
public class informesController {

    @Autowired
    private informeService service;
    @Autowired
    private AlumnoService alumnoService;


    @GetMapping("/list")
    @CrossOrigin("*")
    public ResponseEntity<List<InformeDesempenio>> listarInformes(){
        return (ResponseEntity<List<InformeDesempenio>>) ResponseEntity.ok(service.listar());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);
        if (informeDesempeñoOptional.isPresent()){
            return ResponseEntity.ok(informeDesempeñoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<?> listarPorAsignatura(@PathVariable Long idAsignatura){
        List<InformeDesempenio> informeDesempenioOptional = service.listarPorAsignatura(idAsignatura);
        if (!informeDesempenioOptional.isEmpty()){
            return ResponseEntity.ok(informeDesempenioOptional);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/save")
    public ResponseEntity<?> crearInforme(@Valid @RequestBody InformesDTO informe, BindingResult result){

       if (service.encontrarAlumno(informe.getAlumno().getId(), informe.getAsignatura().getAsignatura_id()).isPresent()) {
           return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El Informe para este alumno ya existe"));
       }

        //DateFormat formateador= new SimpleDateFormat("dd/M/yy");

        Calendar rightNow =  Calendar.getInstance();

        System.out.println((rightNow.getTime()));

       InformeDesempenio nuevoInforme= new InformeDesempenio();

       nuevoInforme.setAlumno(informe.getAlumno());
       nuevoInforme.setAsignatura(informe.getAsignatura());
       nuevoInforme.setFecha((rightNow.getTime()));

        if(result.hasErrors()){
            return validar(result);
        }
        System.out.println(nuevoInforme);
        InformeDesempenio informeDB = service.guardar(nuevoInforme);
        return ResponseEntity.status(HttpStatus.CREATED).body(informeDB);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> editarInforme(@RequestBody InformeDesempenio informe, @PathVariable Long id){

        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);
        if (!informeDesempeñoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        informe.setId(informeDesempeñoOptional.get().getId());
        service.guardar(informe);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);


        if(!informeDesempeñoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        service.eliminarInforme( informeDesempeñoOptional.get().getId());
        return ResponseEntity.noContent().build();

    }



    @PutMapping("/asignarContenidos/{id}")
    public InformeDesempenio asignarContenidoAdeudado(
            @PathVariable Long id,
           @RequestBody Set<ContenidoAdeudado> contenidos
    ){
        return  service.asignarContenidoAdeudado(id, contenidos);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }





}
