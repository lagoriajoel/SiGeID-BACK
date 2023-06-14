package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {


    @Query(value = "UPDATE db_informes_nuevo.asignaturas SET profesor_id = 1 WHERE (asignatura_id = 1)", nativeQuery=true)
    Optional<Asignatura> asignarProfesor(Long idAsignatura);
    @Query(value = "SELECT * FROM db_informes_nuevo.asignaturas where profesor_id=:idProfesor", nativeQuery=true)
    List<Asignatura> asignaturasByProfesor(Long idProfesor);
}
