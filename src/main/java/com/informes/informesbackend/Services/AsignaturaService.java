package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Asignatura;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AsignaturaService {

    List<Asignatura> listar();
    Optional<Asignatura> listarporId(Long id);


    Asignatura guardar(Asignatura asignatura);

    void eliminar(Long id);

    List<Asignatura> listarPorProfesor(Long idProfesor);
    List<Asignatura> listarPorCurso(Long idCurso);


    public void GuardarAsignaturas(Set<Asignatura> asignaturas);

}
