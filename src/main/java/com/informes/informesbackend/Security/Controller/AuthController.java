package com.informes.informesbackend.Security.Controller;


import com.informes.informesbackend.Security.DTO.JwtDto;
import com.informes.informesbackend.Security.DTO.LoginUsuario;
import com.informes.informesbackend.Security.DTO.Mensaje;
import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Enums.RolNombre;
import com.informes.informesbackend.Security.JWT.JwtProvider;
import com.informes.informesbackend.Security.Service.RolService;
import com.informes.informesbackend.Security.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
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
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult)  {
        if(bindingResult.hasErrors())
            //return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Campos mal puestos"));
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        JwtDto jwtDto = new JwtDto(jwt);

        return new ResponseEntity(jwtDto, HttpStatus.OK);


    }
    @PostMapping("/crearRoles/{crear}")
    public ResponseEntity<?> crearRoles(@PathVariable int crear){

        if(crear==1) {
            Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
            Rol rolUser = new Rol(RolNombre.ROLE_USER);
            Rol rolProfesor = new Rol(RolNombre.ROLE_PROFESOR);

            rolService.save(rolAdmin);
            rolService.save(rolUser);
            rolService.save(rolProfesor);
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
