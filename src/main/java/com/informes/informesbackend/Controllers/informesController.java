package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.*;
import com.informes.informesbackend.Models.Entities.EntitiesDTO.InformesDTO;
import com.informes.informesbackend.Services.AlumnoService;
import com.informes.informesbackend.Services.ContenidoAdeudadoService;
import com.informes.informesbackend.Services.informeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private ContenidoAdeudadoService contenidoAdeudadoService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
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

    @GetMapping("/listOfNombreAsignatura/{nombre}/{curso}")
    public ResponseEntity<?> listarPorNombreAsignatura(@PathVariable String nombre, @PathVariable String curso){
       return ResponseEntity.ok(service.listarPorNombreAsignatura(nombre, curso));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PostMapping("/save")
    public ResponseEntity<?> crearInforme(@Valid @RequestBody InformesDTO informe, BindingResult result){

     if (service.encontrarAlumno(informe.getAlumno().getId(), informe.getAsignatura().getAsignatura_id()).isPresent()) {
         return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El Informe para este alumno ya existe"));
       }
        System.out.println(informe);
        Calendar rightNow =  Calendar.getInstance();
        InformeDesempenio nuevoInforme= new InformeDesempenio();

        nuevoInforme.setAlumno(informe.getAlumno());
        nuevoInforme.setAsignatura(informe.getAsignatura());
        nuevoInforme.setCriteriosEvaluacion(informe.getCriteriosEvaluacion());
        nuevoInforme.setProfesorNombre(informe.getProfesorNombre());
        nuevoInforme.setFecha((rightNow.getTime()));
        System.out.println(nuevoInforme);
        InformeDesempenio informeDB = service.guardar(nuevoInforme);

        Set<Contenido> contenidosInforme=informe.getContenidosAdeudados();
        Set<ContenidoAdeudado> contenidoAdeudados=new HashSet<>();
        contenidosInforme.forEach(contenido -> {
            ContenidoAdeudado nuevoContenido=new ContenidoAdeudado();
            nuevoContenido.setNombre(contenido.getNombre());
            nuevoContenido.setDescripcion(contenido.getDescripcion());
            nuevoContenido.setAprobado(false);
            nuevoContenido.setInformeDesempenio(informeDB);
            contenidoAdeudados.add(nuevoContenido);
        });
        List<ContenidoAdeudado> ListaContenidos= contenidoAdeudadoService.guardarTodos(contenidoAdeudados);

        InformeDesempenio informeDesempenio=service.asignarContenidoAdeudado(informeDB.getId(), ListaContenidos);

        if(result.hasErrors()){
            return validar(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(informeDesempenio);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PutMapping("agregarContenidos/{idInforme}")
    public ResponseEntity<?> agregarContenidos(@RequestBody InformeDesempenio informe, @PathVariable Long idInforme){

        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(idInforme);
        if (!informeDesempeñoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        List<ContenidoAdeudado> contenidosInforme=informe.getContenidosAdeudados();
        Set<ContenidoAdeudado> contenidoAdeudados=new HashSet<>();
        contenidosInforme.forEach(contenido -> {
            ContenidoAdeudado nuevoContenido=new ContenidoAdeudado();
            nuevoContenido.setNombre(contenido.getNombre());
            nuevoContenido.setDescripcion(contenido.getDescripcion());
            nuevoContenido.setAprobado(false);
            contenidoAdeudados.add(nuevoContenido);
        });
        List<ContenidoAdeudado> ListaContenidos= contenidoAdeudadoService.guardarTodos(contenidoAdeudados);


        informe.setId(informeDesempeñoOptional.get().getId());
        service.guardar(informe);

        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('PROFESOR')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<InformeDesempenio> informeDesempeñoOptional = service.listarporId(id);


        if(!informeDesempeñoOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        service.eliminarInforme( informeDesempeñoOptional.get().getId());
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/asignarContenidos/{idInforme}")
    public InformeDesempenio asignarContenidoAdeudado(
            @PathVariable Long idInforme,
           @RequestBody List<ContenidoAdeudado> contenidos
    ){
        return  service.asignarContenidoAdeudado(idInforme, contenidos);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }





}
