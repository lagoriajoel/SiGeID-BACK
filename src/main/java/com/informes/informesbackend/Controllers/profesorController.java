package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Curso;
import com.informes.informesbackend.Models.Entities.Profesor;
import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Service.UsuarioService;
import com.informes.informesbackend.Services.AsignaturaService;
import com.informes.informesbackend.Services.ProfesorService;
import com.informes.informesbackend.Services.guardarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController()
@RequestMapping("/profesor")
@CrossOrigin("*")
public class profesorController {

    @Autowired
    private ProfesorService service;
    @Autowired
    private guardarUsuarioService guardarUsuarioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignaturaService asignaturaService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Profesor>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Profesor> usuarioOptional= service.listarporId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("/listOfDni/{dni}")
    public ResponseEntity<?> detalle(@PathVariable String dni){
        Optional<Profesor> usuarioOptional= service.listarporDni(dni);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El profesor ingresado NO existe"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> crear(@Valid @RequestBody Profesor profesor, BindingResult result) {
        if (!profesor.getEmail().isEmpty() && service.porEmail(profesor.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El email ingresado ya existe"));
        }
        if (!profesor.getDni().isEmpty() && service.listarporDni(profesor.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El profesor ingresado ya existe"));
        }
        if (result.hasErrors()) {
            return validar(result);
        }

        if(usuarioService.existsByNombreUsuario(profesor.getDni())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El profesor ingresado ya tiene una cuenta creada"));
        }

        Set<String> roles= new HashSet<>();
        roles.add("profesor");

        Profesor ProfesorGuardado = service.guardar(profesor);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ProfesorGuardado.getId()).toUri();

        // crear el usuario para el alumno

        NuevoUsuario nuevoUsuario= new NuevoUsuario();
        nuevoUsuario.setNombre(profesor.getNombreCompleto());
        nuevoUsuario.setNombreUsuario(profesor.getDni());
        nuevoUsuario.setPassword(profesor.getDni());
        nuevoUsuario.setRoles(roles);

        //

        guardarUsuarioService.crearUsuario(nuevoUsuario);


        return ResponseEntity.created(ubicacion).body(ProfesorGuardado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Profesor profesor, @PathVariable Long id) {



        Optional<Profesor> profesorOptional= service.listarporId(id);
        if (!profesorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        profesor.setId(profesorOptional.get().getId());
        service.guardar(profesor);

        return ResponseEntity.noContent().build();


    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Profesor> eliminar(@PathVariable Long id){
        Optional<Profesor> profesorOptional = service.listarporId(id);

        if(!profesorOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Usuario> usuario= usuarioService.getByNombreUsuario( profesorOptional.get().getDni());

        usuarioService.delete(usuario.get().getId());

        service.eliminar(profesorOptional.get().getId());
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/asignar/{idProfesor}/Asignatura/{idAsignatura}")
    public ResponseEntity<?> asignarAsignatura (@PathVariable Long idProfesor, @PathVariable Long idAsignatura){
        Optional<Profesor> profesorOptional= service.listarporId(idProfesor);
        if (!profesorOptional.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Mensaje", "El profesor no se encuentra en la base de datos"));
        }
        Optional<Asignatura> asignaturaOptional= asignaturaService.listarporId(idAsignatura);

        if (!asignaturaOptional.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Mensaje", "La Asignatura no esta presente"));
        }
        if(profesorOptional.get().getAsignaturas().contains(asignaturaOptional.get())){
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Mensaje", "El profesor ya tiene asignado esta asignatura"));
        }
        Set<Asignatura> asignaturas=profesorOptional.get().getAsignaturas();

        asignaturas.add(asignaturaOptional.get());

        profesorOptional.get().setAsignaturas(asignaturas);

       return ResponseEntity.ok().body( service.guardar(profesorOptional.get()));


    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listarPorAsignatura/{idAsignatura}")
    public ResponseEntity<?> listarPorAsignatura(@PathVariable Long idAsignatura) {
        Optional<Long> idOpcioanl= (service.encontrarPorAsignatura(idAsignatura));
        if (!idOpcioanl.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Mensaje", "La asignatura no tiene asignado un profesor asignado" ));
        }
        Optional<Profesor> profesorOptional=service.listarporId(idOpcioanl.get());

        return ResponseEntity.ok(profesorOptional);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);}
}
