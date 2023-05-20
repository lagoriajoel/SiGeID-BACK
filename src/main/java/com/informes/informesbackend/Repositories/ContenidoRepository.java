package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    @Query(value = "SELECT * FROM db_informes.contenidos as informes where asignatura_id=:idAsignatura", nativeQuery=true)
    List<Contenido> findByAsignatura(Long idAsignatura);
}
