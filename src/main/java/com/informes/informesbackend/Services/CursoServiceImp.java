package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Curso;
import com.informes.informesbackend.Repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CursoServiceImp implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {

        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        return Optional.empty();
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);

    }

    @Override
    public void eliminarCursoUsuarioPorId(Long id) {

    }

    @Override
    public Optional<Alumno> asignarAlumno(Alumno alumno, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Alumno> CrearAlumno(Alumno alumno, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Alumno> EliminarUsuario(Alumno alumno, Long cursoId) {
        return Optional.empty();
    }
}
