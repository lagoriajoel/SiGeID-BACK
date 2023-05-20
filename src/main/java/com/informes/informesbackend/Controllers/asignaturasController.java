package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@RequestMapping("/asignaturas")
@CrossOrigin("*")
public class asignaturasController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping("list")
    public ResponseEntity<Collection<Asignatura>> listarAsignaturas(){
        return new ResponseEntity<>(asignaturaService.listar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<Asignatura> obtenerAsignaturas(@PathVariable long id){
        Asignatura asignatura = asignaturaService.listarporId(id).get();

        if(asignatura != null) {
            return new ResponseEntity<>(asignaturaService.listarporId(id).get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("save")
    public ResponseEntity<?> guardarAsignatura(@RequestBody Asignatura asignatura){
        return new ResponseEntity<>(asignaturaService.guardar(asignatura),HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable long id){
        asignaturaService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
