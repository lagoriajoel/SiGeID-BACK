package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Models.Entities.estrategiasEvaluacion;
import com.informes.informesbackend.Repositories.estrategiasRepository;
import com.informes.informesbackend.Services.estrategiasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/estrategias")
@CrossOrigin("*")
public class estrategiasController {
    @Autowired
    estrategiasServiceImpl service;

    @GetMapping("/list")
    public ResponseEntity<List<estrategiasEvaluacion>> listarContenidos(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<estrategiasEvaluacion> obtenerContenidoPorId(@PathVariable Long id){
        estrategiasEvaluacion estrategiasEvaluacion = service.listarporId(id).get();

        if(estrategiasEvaluacion != null) {
            return new ResponseEntity<>(service.listarporId(id).get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/save")
    public ResponseEntity<?> guardarContenido(@RequestBody estrategiasEvaluacion estrategiasEvaluacion){
        return new ResponseEntity<>(service.guardar(estrategiasEvaluacion),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarContenido(@RequestBody estrategiasEvaluacion contenido, @PathVariable Long id){

        Optional<estrategiasEvaluacion> optionalContenido = service.listarporId(id);
        if(!optionalContenido.isPresent()){


            return ResponseEntity.unprocessableEntity().build();
        }

        contenido.setId(optionalContenido.get().getId());
        service.guardar(contenido);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEstrategia(@PathVariable Long id){
        Optional<estrategiasEvaluacion> estrategiaOptional = service.listarporId(id);

        if(!estrategiaOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        service.eliminarEstrategia(estrategiaOptional.get().getId());
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<List<estrategiasEvaluacion>> contenidoPorAsignaturas(@PathVariable Long idAsignatura){

        return new ResponseEntity<>(service.listarPorAsignatura(idAsignatura), HttpStatus.OK);
    }
}
