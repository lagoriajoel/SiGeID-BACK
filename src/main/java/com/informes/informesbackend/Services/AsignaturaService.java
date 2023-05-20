package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Asignatura;

import java.util.List;
import java.util.Optional;

public interface AsignaturaService {

    List<Asignatura> listar();
    Optional<Asignatura> listarporId(Long id);


    Asignatura guardar(Asignatura asignatura);
    void eliminar(Long id);



}
