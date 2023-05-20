package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByApellido(String apellido);
    Optional<Alumno> findByDni(String dni);

    Optional<Alumno> findByEmail(String email);

    @Query(value = "SELECT * FROM db_informes.alumnos as alumnos WHERE alumnos.curso=:idCurso", nativeQuery=true)
     List<Alumno> findBycurso(Long idCurso);




}
