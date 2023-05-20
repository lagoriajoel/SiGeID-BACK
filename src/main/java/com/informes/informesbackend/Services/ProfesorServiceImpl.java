package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Profesor;
import com.informes.informesbackend.Repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorServiceImpl implements ProfesorService{

    @Autowired
    private ProfesorRepository profesorRepository;
    @Override
    public List<Profesor> listar() {
        return profesorRepository.findAll();
    }

    @Override
    public Optional<Profesor> listarporId(Long id) {
        return profesorRepository.findById(id);
    }

    @Override
    public Optional<Profesor> listarporDni(String dni) {
        return profesorRepository.findByDni(dni);
    }

    @Override
    public Profesor guardar(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public void eliminar(Long id) {
           profesorRepository.deleteById(id);
    }

    @Override
    public Optional<Profesor> porEmail(String email) {
        return profesorRepository.findByEmail(email);
    }

    @Override
    public List<Profesor> listarPorApellido(String apellido) {
        return profesorRepository.findByApellido(apellido);
    }
}
