package com.informes.informesbackend.Security.Controller;


import com.informes.informesbackend.Security.DTO.*;
import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Enums.RolNombre;
import com.informes.informesbackend.Security.JWT.JwtProvider;
import com.informes.informesbackend.Security.Service.RolService;
import com.informes.informesbackend.Security.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getNombreUsuario(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        if(nuevoUsuario.getRoles().contains("profesor"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_PROFESOR).get());
        if(nuevoUsuario.getRoles().contains("directivo"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DIRECTIVO).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult)  {
        if(bindingResult.hasErrors())
                     return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Campos mal puestos"));
        Authentication authentication =
                authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        JwtDto jwtDto = new JwtDto(jwt);

        return new ResponseEntity(jwtDto, HttpStatus.OK);


    }
    @GetMapping("/list/{username}")
        public ResponseEntity<?> listByNameUser(@PathVariable String username){

        return ResponseEntity.ok(usuarioService.getByNombreUsuario(username));


        }


    @PutMapping("/changePassword/{nombreUsuario}")
    public ResponseEntity<?> cambiarContraenia(@PathVariable String nombreUsuario , @RequestBody changePassword changePassword){
      Optional<Usuario> usuarioOptional= usuarioService.getByNombreUsuario(nombreUsuario);

      boolean check = passwordEncoder.matches(changePassword.getCurrentPassword(), usuarioOptional.get().getPassword());

        if (!check)
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "La contraseña actual es incorrecta"));
        if(!usuarioOptional.isPresent())
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Usuario no encontrado"));

        usuarioOptional.get().setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        usuarioService.save(usuarioOptional.get());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/blanquearPassword/{nombreUsuario}")
    public ResponseEntity<?> blanquearContraenia(@PathVariable String nombreUsuario , @RequestBody changePassword changePassword){
        Optional<Usuario> usuarioOptional= usuarioService.getByNombreUsuario(nombreUsuario);


        if(!usuarioOptional.isPresent())
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Usuario no encontrado"));

        usuarioOptional.get().setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        usuarioService.save(usuarioOptional.get());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/crearRoles/{crear}")
    public ResponseEntity<?> crearRoles(@PathVariable int crear){

        if(crear==1) {
            Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
            Rol rolUser = new Rol(RolNombre.ROLE_USER);
            Rol rolProfesor = new Rol(RolNombre.ROLE_PROFESOR);
            Rol rolDirectivo = new Rol(RolNombre.ROLE_DIRECTIVO);

            rolService.save(rolAdmin);
            rolService.save(rolUser);
            rolService.save(rolProfesor);
            rolService.save(rolDirectivo);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("Mensaje", "No se pudo crear los Roles"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        JwtDto jwt = new JwtDto(token);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }
}
