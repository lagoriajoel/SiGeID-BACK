package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.*;
import com.informes.informesbackend.Models.Entities.EntitiesDTO.InformesDTO;
import com.informes.informesbackend.Services.AlumnoService;
import com.informes.informesbackend.Services.PDFgeneradorService;
import com.informes.informesbackend.Services.informeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public ResponseEntity<List<InformeDesempeño>> listarInformes(){
        return (ResponseEntity<List<InformeDesempeño>>) ResponseEntity.ok(service.listar());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<InformeDesempeño> informeDesempeñoOptional = service.listarporId(id);
        if (informeDesempeñoOptional.isPresent()){
            return ResponseEntity.ok(informeDesempeñoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<?> listarPorAsignatura(@PathVariable Long idAsignatura){
        List<InformeDesempeño> informeDesempeñoOptional = service.listarPorAsignatura(idAsignatura);
        if (!informeDesempeñoOptional.isEmpty()){
            return ResponseEntity.ok(informeDesempeñoOptional);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/save")
    public ResponseEntity<?> crearInforme(@Valid @RequestBody InformesDTO informe, BindingResult result){

        System.out.println(informe.getAlumno().getId()+" id asignatura "+informe.getId_asignatura());
       if (service.encontrarAlumno(informe.getAlumno().getId(), informe.getId_asignatura()).isPresent()) {
           return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El Informe para este alumno ya existe"));
       }


        //DateFormat formateador= new SimpleDateFormat("dd/M/yy");




        Calendar rightNow =  Calendar.getInstance();

        System.out.println((rightNow.getTime()));


       InformeDesempeño nuevoInforme= new InformeDesempeño();

       nuevoInforme.setDescripcion(informe.getDescripcion());
       nuevoInforme.setCreated(true);
       nuevoInforme.setAlumno(informe.getAlumno());
       nuevoInforme.setId_asignatura(informe.getId_asignatura());
       nuevoInforme.setFecha((rightNow.getTime()));

       nuevoInforme.setContenidosAdeudados(informe.getContenidos());


        if(result.hasErrors()){
            return validar(result);
        }
        System.out.println(nuevoInforme);
        InformeDesempeño informeDB = service.guardar(nuevoInforme);
        return ResponseEntity.status(HttpStatus.CREATED).body(informeDB);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> editarInforme(@RequestBody InformeDesempeño informe, @PathVariable Long id){

        Optional<InformeDesempeño> informeDesempeñoOptional = service.listarporId(id);
        if (!informeDesempeñoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        informe.setId(informeDesempeñoOptional.get().getId());
        service.guardar(informe);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<InformeDesempeño> informeDesempeñoOptional = service.listarporId(id);


        if(!informeDesempeñoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        service.eliminarInforme( informeDesempeñoOptional.get().getId());
        return ResponseEntity.noContent().build();

    }



    @PutMapping("/list/{id}/contenido/{contenidoId}")
    public InformeDesempeño asignarContenidoAdeudado(
            @PathVariable Long id,
            @PathVariable Long contenidoId
    ){
        return  service.asignarContenidoAdeudado(id, contenidoId);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }





}
