package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.ContenidoAdeudado;
import com.informes.informesbackend.Repositories.ContenidoAdeudadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ContenidoAdeudadoServiceImpl implements ContenidoAdeudadoService{
    @Autowired
    private ContenidoAdeudadoRepository repository;
    @Override
    public List<ContenidoAdeudado> listar() {
        return repository.findAll();
    }

    @Override
    public List<ContenidoAdeudado> listarPorAsignatura(Long idAsignatura) {
        return null;
    }

    @Override
    public List<ContenidoAdeudado> guardarTodos(Set<ContenidoAdeudado> contenidos) {
        return  repository.saveAll(contenidos);
    }

    @Override
    public Optional<ContenidoAdeudado> listarporId(Long id) {
        return repository.findById(id);
    }

    @Override
    public ContenidoAdeudado guardar(ContenidoAdeudado contenido) {
        return repository.save(contenido);
    }

    @Override
    public void eliminarContenido(Long id) {
             repository.deleteById(id);}
}
