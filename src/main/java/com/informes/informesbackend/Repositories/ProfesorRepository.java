package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    List<Profesor> findByApellido(String apellido);

    Optional<Profesor> findByDni(String dni);

    Optional<Profesor> findByEmail(String email);



}
