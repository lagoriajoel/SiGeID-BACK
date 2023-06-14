package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    List<Profesor> findByApellido(String apellido);

    Optional<Profesor> findByDni(String dni);

    Optional<Profesor> findByEmail(String email);

    @Query(value = "SELECT profesor_id FROM db_informes_nuevo.asignaturas where asignatura_id=:idAsignatura", nativeQuery=true)
    Optional<Long> findProfesorByAsignatura(Long idAsignatura);

}
