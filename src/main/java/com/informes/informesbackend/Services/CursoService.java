package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);

    Optional<Curso> porIdConUsuarios(Long id);

    Curso guardar (Curso curso);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorId(Long id);
    Optional<Alumno> asignarAlumno(Alumno alumno, Long cursoId);
    Optional<Alumno> CrearAlumno(Alumno alumno, Long cursoId);
    Optional<Alumno> EliminarUsuario(Alumno alumno, Long cursoId);
}
