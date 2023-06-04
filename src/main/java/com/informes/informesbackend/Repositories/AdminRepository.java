package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Administrador, Long> {

    List<Administrador> findByApellido(String apellido);
    Optional<Administrador> findByDni(String dni);

    Optional<Administrador> findByEmail(String email);

}
