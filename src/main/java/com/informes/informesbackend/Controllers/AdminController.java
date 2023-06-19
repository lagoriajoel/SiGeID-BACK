package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Profesor;
import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Service.UsuarioService;
import com.informes.informesbackend.Services.AdminService;
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
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
   private AdminService service;
    @Autowired
    private com.informes.informesbackend.Services.guardarUsuarioService guardarUsuarioService;
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Administrador>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Administrador> usuarioOptional= service.listarporId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> crear(@Valid @RequestBody Administrador administrador, BindingResult result) {
        if (!administrador.getEmail().isEmpty() && service.porEmail(administrador.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El email ingresado ya existe"));
        }
        if (!administrador.getDni().isEmpty() && service.listarporDni(administrador.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El administrador ingresado ya existe"));
        }

        if(usuarioService.existsByNombreUsuario(administrador.getDni())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El administrador ingresado ya tiene una cuenta creada"));
        }
        if (result.hasErrors()) {
            return validar(result);
        }

        Set<String> roles= new HashSet<>();
        roles.add("admin");

        Administrador AdminGuardado = service.guardar(administrador);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(AdminGuardado.getId()).toUri();

        // crear el usuario para el alumno

        NuevoUsuario nuevoUsuario= new NuevoUsuario();
        nuevoUsuario.setNombre(administrador.getNombreCompleto());
        nuevoUsuario.setNombreUsuario(administrador.getDni());
        nuevoUsuario.setPassword(administrador.getDni());
        nuevoUsuario.setRoles(roles);

        guardarUsuarioService.crearUsuario(nuevoUsuario);


        return ResponseEntity.created(ubicacion).body(administrador);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Administrador administrador, @PathVariable Long id) {



        Optional<Administrador> administradorOptional= service.listarporId(id);
        if (!administradorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        administrador.setId(administradorOptional.get().getId());
        service.guardar(administrador);

        return ResponseEntity.noContent().build();


    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Administrador> eliminar(@PathVariable Long id){
        Optional<Administrador> administradorOptional = service.listarporId(id);

        if(!administradorOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Usuario> usuario= usuarioService.getByNombreUsuario( administradorOptional.get().getDni());

        usuarioService.delete(usuario.get().getId());

        service.eliminar(administradorOptional.get().getId());
        return ResponseEntity.ok().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);}

}
