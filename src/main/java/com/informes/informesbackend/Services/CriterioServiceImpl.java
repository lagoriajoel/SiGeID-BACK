package com.informes.informesbackend.Services;
import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Repositories.criterioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CriterioServiceImpl implements criterioService {

    @Autowired
    criterioRepository criterioRepository;
    @Override
    public List<criteriosEvaluacion> listar() {
        return criterioRepository.findAll();
    }

    @Override
    public List<criteriosEvaluacion> listarPorAsignatura(Long idAsignatura) {
        return criterioRepository.findByAsignatura(idAsignatura);
    }

    @Override
    public Optional<criteriosEvaluacion> listarporId(Long id) {
        return criterioRepository.findById(id);
    }

    @Override
    public criteriosEvaluacion guardar(criteriosEvaluacion criteriosEvaluacion) {
        return criterioRepository.save(criteriosEvaluacion);
    }

    @Override
    public void eliminarContenido(Long id) {
         criterioRepository.deleteCriterio(id);
    }
}
