package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Repositories.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ContenidoServiceImp implements contenidoService{

    @Autowired
    private ContenidoRepository contenidoRepository;
    @Override
    public List<Contenido> listar() {
        return contenidoRepository.findAll();
    }

    @Override
    public List<Contenido> listarPorAsignatura(Long idAsignatura) {
        return contenidoRepository.findByAsignatura(idAsignatura);
    }

    @Override
    public Optional<Contenido> listarporId(Long id) {
        return contenidoRepository.findById(id);
    }

    @Override
    public Contenido guardar(Contenido contenido) {
        return contenidoRepository.save(contenido);
    }

    @Override
    public void eliminarContenido(Long id) {
        contenidoRepository.deleteById(id);


    }
}
