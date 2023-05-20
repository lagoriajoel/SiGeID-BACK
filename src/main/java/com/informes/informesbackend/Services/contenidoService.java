package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;

import java.util.List;
import java.util.Optional;

public interface contenidoService {
    List<Contenido> listar();
    List<Contenido> listarPorAsignatura(Long idAsignatura);
    Optional<Contenido> listarporId(Long id);
    Contenido guardar(Contenido contenido);
    void eliminarContenido(Long id);

}
