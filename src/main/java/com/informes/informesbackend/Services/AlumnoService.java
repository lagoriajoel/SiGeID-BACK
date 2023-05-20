package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {

    List<Alumno> listar();
    Optional<Alumno> listarporId(Long id);

    Optional<Alumno> listarporDni(String id);
    Alumno guardar(Alumno alumno);
    void eliminar(Long id);
    Optional<Alumno> porEmail(String email);

    List<Alumno> listarPorApellido(String apellido);

    List<Alumno>listarPorCurso(Long idCurso);
}
