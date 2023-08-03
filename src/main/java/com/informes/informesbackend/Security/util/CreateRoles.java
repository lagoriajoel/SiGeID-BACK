package com.informes.informesbackend.Security.util;

import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Enums.RolNombre;
import com.informes.informesbackend.Security.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 *
 */

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        /**  Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
         Rol rolProfesor = new Rol(RolNombre.ROLE_PROFESOR);
        Rol rolDirector = new Rol(RolNombre.ROLE_DIRECTIVO);


      rolService.save(rolAdmin);
        rolService.save(rolUser);
         rolService.save(rolProfesor);
        rolService.save(rolDirector);**/



    }
}
