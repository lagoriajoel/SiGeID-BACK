package com.informes.informesbackend.Services;

import com.informes.informesbackend.Security.DTO.NuevoUsuario;
import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Enums.RolNombre;
import com.informes.informesbackend.Security.Service.RolService;
import com.informes.informesbackend.Security.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class guardarUsuarioService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolService rolService;
    @Autowired
    UsuarioService usuarioService;

public void crearUsuario(NuevoUsuario nuevoUsuario){


    Usuario usuario =
            new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
                    passwordEncoder.encode(nuevoUsuario.getPassword()));
    Set<Rol> roles = new HashSet<>();
    roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
    if(nuevoUsuario.getRoles().contains("admin"))
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
    if(nuevoUsuario.getRoles().contains("profesor"))
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_PROFESOR).get());
    usuario.setRoles(roles);
    usuarioService.save(usuario);
}
}
