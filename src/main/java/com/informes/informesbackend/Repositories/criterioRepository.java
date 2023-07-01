package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface criterioRepository extends JpaRepository<criteriosEvaluacion, Long> {


    @Query(value = "SELECT * FROM criterios_evaluacion as informes where asignatura_id=:idAsignatura", nativeQuery=true)
    List<criteriosEvaluacion> findByAsignatura(Long idAsignatura);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM criterios_evaluacion WHERE id=:id", nativeQuery=true)
    void deleteCriterio(Long id);
}
