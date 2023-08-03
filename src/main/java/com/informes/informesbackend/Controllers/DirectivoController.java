package com.informes.informesbackend.Controllers;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Directivo;
import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Service.UsuarioService;
import com.informes.informesbackend.Services.DirectivoService;
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
@RequestMapping("/directivo")
@CrossOrigin("*")
public class DirectivoController {
    @Autowired
    private DirectivoService service;
    @Autowired
    private com.informes.informesbackend.Services.guardarUsuarioService guardarUsuarioService;
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Directivo>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Directivo> usuarioOptional= service.listarporId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> crear(@Valid @RequestBody Directivo directivo, BindingResult result) {
        if (!directivo.getEmail().isEmpty() && service.porEmail(directivo.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El email ingresado ya existe"));
        }
        if (!directivo.getDni().isEmpty() && service.listarporDni(directivo.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El administrador ingresado ya existe"));
        }

        if(usuarioService.existsByNombreUsuario(directivo.getDni())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "El administrador ingresado ya tiene una cuenta creada"));
        }
        if (result.hasErrors()) {
            return validar(result);
        }

        Set<String> roles= new HashSet<>();
        roles.add("directivo");

        Directivo AdminGuardado = service.guardar(directivo);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(AdminGuardado.getId()).toUri();

        // crear el usuario para el director

        NuevoUsuario nuevoUsuario= new NuevoUsuario();
        nuevoUsuario.setNombre(directivo.getNombreCompleto());
        nuevoUsuario.setNombreUsuario(directivo.getDni());
        nuevoUsuario.setPassword(directivo.getDni());
        nuevoUsuario.setRoles(roles);

        guardarUsuarioService.crearUsuario(nuevoUsuario);


        return ResponseEntity.created(ubicacion).body(directivo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Directivo directivo, @PathVariable Long id) {



        Optional<Directivo> administradorOptional= service.listarporId(id);
        if (!administradorOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }


        directivo.setId(administradorOptional.get().getId());
        service.guardar(directivo);

        return ResponseEntity.noContent().build();


    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")

    public ResponseEntity<Directivo> eliminar(@PathVariable Long id){
        Optional<Directivo> administradorOptional = service.listarporId(id);

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
