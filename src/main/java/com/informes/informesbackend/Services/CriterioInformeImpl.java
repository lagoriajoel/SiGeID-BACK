package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.criterioInforme;
import com.informes.informesbackend.Models.Entities.criteriosEvaluacion;
import com.informes.informesbackend.Repositories.criterioAdeudadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class CriterioInformeImpl {
    @Autowired
    criterioAdeudadoRepository criterioRepository;

    public List<criterioInforme> listar() {
        return criterioRepository.findAll();
    }



    public Optional<criterioInforme> listarporId(Long id) {
        return criterioRepository.findById(id);
    }


    public criterioInforme guardar(criterioInforme criteriosEvaluacion) {
        return criterioRepository.save(criteriosEvaluacion);
    }
    public List<criterioInforme> guardarTodos(Set<criterioInforme> criterios) {

        return criterioRepository.saveAll(criterios);
    }



}
