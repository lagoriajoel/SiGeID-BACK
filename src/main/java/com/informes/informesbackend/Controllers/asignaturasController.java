package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Profesor;
import com.informes.informesbackend.Services.AsignaturaService;
import com.informes.informesbackend.Services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/asignaturas")
@CrossOrigin("*")
public class asignaturasController {

    @Autowired
    private AsignaturaService asignaturaService;
    @Autowired
    private ProfesorService profesorService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("list")
    public ResponseEntity<Collection<Asignatura>> listarAsignaturas(){
        return new ResponseEntity<>(asignaturaService.listar(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")

    @GetMapping("list/{id}")
    public ResponseEntity<Asignatura> obtenerAsignaturas(@PathVariable long id){
        Asignatura asignatura = asignaturaService.listarporId(id).get();

        if(asignatura != null) {
            return new ResponseEntity<>(asignaturaService.listarporId(id).get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("listOfProfesor/{id}")
    public ResponseEntity<?> listarPorProfesor(@PathVariable Long id){
        List<Asignatura> asignaturas = asignaturaService.listarPorProfesor(id);

        if(!asignaturas.isEmpty()) {
            return new ResponseEntity<>(asignaturas,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("save")
    public ResponseEntity<?> guardarAsignatura(@RequestBody Asignatura asignatura){
        return new ResponseEntity<>(asignaturaService.guardar(asignatura),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable long id){
        asignaturaService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/asignar/{idProfesor}/Asignatura/{idAsignatura}")
    public ResponseEntity<?> asignarProfesor (@PathVariable Long idProfesor, @PathVariable Long idAsignatura){
        Optional<Profesor> profesorOptional= profesorService.listarporId(idProfesor);
        if (!profesorOptional.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("Mensaje", "El profesor no se encuentra en la base de datos"));
        }
        Optional<Asignatura> asignaturaOptional= asignaturaService.listarporId(idAsignatura);

        if (!asignaturaOptional.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("Mensaje", "La Asignatura no esta presente"));
        }
        if(profesorOptional.get().getAsignaturas().contains(asignaturaOptional.get())){
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("Mensaje", "El profesor ya tiene asignado esta asignatura"));
        }
        asignaturaOptional.get().setProfesor(profesorOptional.get());

        return ResponseEntity.ok().body( asignaturaService.guardar(asignaturaOptional.get()));


    }
}
