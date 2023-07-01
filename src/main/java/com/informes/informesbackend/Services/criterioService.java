package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;

import java.util.List;
import java.util.Optional;

public interface criterioService {
    List<criteriosEvaluacion> listar();
    List<criteriosEvaluacion> listarPorAsignatura(Long idAsignatura);
    Optional<criteriosEvaluacion> listarporId(Long id);
    criteriosEvaluacion guardar(criteriosEvaluacion criteriosEvaluacion);
    void eliminarContenido(Long id);
}
