package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Repositories.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AsignaturaServiceImp implements AsignaturaService{

    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @Override
    public List<Asignatura> listar() {
        return asignaturaRepository.findAll();
    }

    @Override
    public Optional<Asignatura> listarporId(Long id) {
        return asignaturaRepository.findById(id);
    }

    @Override
    public Asignatura guardar(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    @Override
    public void eliminar(Long id) {
        asignaturaRepository.deleteById(id);
    }
}
