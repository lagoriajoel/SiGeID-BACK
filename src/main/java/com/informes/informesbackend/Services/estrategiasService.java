package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Models.Entities.estrategiasEvaluacion;

import java.util.List;
import java.util.Optional;

public interface estrategiasService {
    List<estrategiasEvaluacion> listar();
    List<estrategiasEvaluacion> listarPorAsignatura(Long idAsignatura);
    Optional<estrategiasEvaluacion> listarporId(Long id);
    estrategiasEvaluacion guardar(estrategiasEvaluacion estrategiasEvaluacion);
    void eliminarEstrategia(Long id);
}