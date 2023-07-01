package com.informes.informesbackend.Controllers;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Services.CriterioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/criterios")
@CrossOrigin("*")
public class criteriosController {

    @Autowired
    CriterioServiceImpl service;


    @GetMapping("/list")
    public ResponseEntity<List<criteriosEvaluacion>> listarContenidos(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<criteriosEvaluacion> obtenerContenidoPorId(@PathVariable Long id){
        criteriosEvaluacion criteriosEvaluacion = service.listarporId(id).get();

        if(criteriosEvaluacion != null) {
            return new ResponseEntity<>(service.listarporId(id).get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/save")
    public ResponseEntity<?> guardarContenido(@RequestBody criteriosEvaluacion criteriosEvaluacion){
        return new ResponseEntity<>(service.guardar(criteriosEvaluacion),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarContenido(@RequestBody criteriosEvaluacion contenido, @PathVariable Long id){

        Optional<criteriosEvaluacion> optionalContenido = service.listarporId(id);
        if(!optionalContenido.isPresent()){


            return ResponseEntity.unprocessableEntity().build();
        }

        contenido.setId(optionalContenido.get().getId());
        service.guardar(contenido);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarCriterio(@PathVariable Long id){

        Optional<criteriosEvaluacion> criterioOptional = service.listarporId(id);

        if(!criterioOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        service.eliminarContenido(criterioOptional.get().getId());
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<List<criteriosEvaluacion>> contenidoPorAsignaturas(@PathVariable Long idAsignatura){

        return new ResponseEntity<>(service.listarPorAsignatura(idAsignatura), HttpStatus.OK);
    }
}



