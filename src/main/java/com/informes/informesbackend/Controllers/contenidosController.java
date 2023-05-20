package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.Curso;
import com.informes.informesbackend.Services.AsignaturaService;
import com.informes.informesbackend.Services.contenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/contenidos")
@CrossOrigin("*")
public class contenidosController {

    @Autowired
    private contenidoService service;

    @GetMapping("/list")
    public ResponseEntity<List<Contenido>> listarContenidos(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<Contenido> obtenerContenidoPorId(@PathVariable long id){
        Contenido contenido = service.listarporId(id).get();

        if(contenido != null) {
            return new ResponseEntity<>(service.listarporId(id).get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardarContenido(@RequestBody Contenido contenido){
        return new ResponseEntity<>(service.guardar(contenido),HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarContenido(@RequestBody Contenido contenido, @PathVariable Long id){

        Optional<Contenido> optionalContenido = service.listarporId(id);
        if(!optionalContenido.isPresent()){


            return ResponseEntity.unprocessableEntity().build();
        }

        contenido.setId(optionalContenido.get().getId());
        service.guardar(contenido);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable long id){
        service.eliminarContenido(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/listOfAsignatura/{idAsignatura}")
    public ResponseEntity<List<Contenido>> contenidoPorAsignaturas(@PathVariable Long idAsignatura){

        return new ResponseEntity<>(service.listarPorAsignatura(idAsignatura), HttpStatus.OK);
    }
}
