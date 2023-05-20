package com.informes.informesbackend.Security.Service;

import com.informes.informesbackend.Security.Entity.Usuario;
import com.informes.informesbackend.Security.Entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).orElseThrow(()-> new UsernameNotFoundException("El Usuario "+nombreUsuario+" no existe" ));
        return UsuarioPrincipal.build(usuario);
    }
}
