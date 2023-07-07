package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.criterioInforme;
import com.informes.informesbackend.Models.Entities.estrategiaInforme;
import com.informes.informesbackend.Repositories.estrategiaAdeudadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EstrategiaInformeServiceImpl {
    @Autowired
    estrategiaAdeudadaRepository estrategiaAdeudadaRepository;

    public List<estrategiaInforme> listar() {
        return estrategiaAdeudadaRepository.findAll();
    }



    public Optional<estrategiaInforme> listarporId(Long id) {
        return estrategiaAdeudadaRepository.findById(id);
    }


    public estrategiaInforme guardar(estrategiaInforme criteriosEvaluacion) {
        return estrategiaAdeudadaRepository.save(criteriosEvaluacion);
    }
    public List<estrategiaInforme> guardarTodos(Set<estrategiaInforme> criterios) {

        return estrategiaAdeudadaRepository.saveAll(criterios);
    }
}
