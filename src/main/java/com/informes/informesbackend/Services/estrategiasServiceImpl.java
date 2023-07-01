package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.estrategiasEvaluacion;
import com.informes.informesbackend.Repositories.estrategiasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class estrategiasServiceImpl implements estrategiasService{

    @Autowired
    estrategiasRepository estrategiasRepository;
    @Override
    public List<estrategiasEvaluacion> listar() {
        return estrategiasRepository.findAll();
    }

    @Override
    public List<estrategiasEvaluacion> listarPorAsignatura(Long idAsignatura) {
        return estrategiasRepository.findByAsignatura(idAsignatura);
    }

    @Override
    public Optional<estrategiasEvaluacion> listarporId(Long id) {
        return estrategiasRepository.findById(id);
    }

    @Override
    public estrategiasEvaluacion guardar(estrategiasEvaluacion estrategiasEvaluacion) {
        return estrategiasRepository.save(estrategiasEvaluacion);
    }

    @Override
    public void eliminarEstrategia(Long id) {
     estrategiasRepository.deleteEstrategia(id);
    }
}
