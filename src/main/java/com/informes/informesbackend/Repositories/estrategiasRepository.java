package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Models.Entities.estrategiasEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface estrategiasRepository extends JpaRepository<estrategiasEvaluacion, Long> {


    @Query(value = "SELECT * FROM estrategias_evaluacion where asignatura_id=:idAsignatura", nativeQuery=true)
    List<estrategiasEvaluacion> findByAsignatura(Long idAsignatura);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM estrategias_evaluacion WHERE id=:id", nativeQuery=true)
    void deleteEstrategia(Long id);
}