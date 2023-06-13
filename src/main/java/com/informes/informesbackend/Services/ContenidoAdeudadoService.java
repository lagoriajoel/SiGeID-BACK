package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.ContenidoAdeudado;

import java.util.List;
import java.util.Optional;

public interface ContenidoAdeudadoService {

    List<ContenidoAdeudado> listar();
    List<ContenidoAdeudado> listarPorAsignatura(Long idAsignatura);
    Optional<ContenidoAdeudado> listarporId(Long id);
    ContenidoAdeudado guardar(ContenidoAdeudado contenido);
    void eliminarContenido(Long id);
}
