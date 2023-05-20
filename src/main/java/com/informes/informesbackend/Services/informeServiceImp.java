package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.InformeDesempeño;
import com.informes.informesbackend.Repositories.ContenidoRepository;
import com.informes.informesbackend.Repositories.InformeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class informeServiceImp implements informeService{

    @Autowired
    private InformeRepository informeRepository;
    @Autowired
    private ContenidoRepository contenidoRepository;

    @Override
    public InformeDesempeño asignarContenidoAdeudado(Long id, Long contenidoId) {

        Set<Contenido> contenidoSet=null;
        InformeDesempeño informeDesempeño= informeRepository.findById(id).get();
        Contenido contenidoAdeudado= contenidoRepository.findById(contenidoId).get();
       contenidoSet= informeDesempeño.getContenidosAdeudados();
       contenidoSet.add(contenidoAdeudado);
       informeDesempeño.setContenidosAdeudados(contenidoSet);
      return informeRepository.save(informeDesempeño);
    }

    @Override
    public List<InformeDesempeño> listar() {
        return informeRepository.findAll();
    }

    @Override
    public Optional<InformeDesempeño> listarporId(Long id) {
        return informeRepository.findById(id);
    }

    @Override
    public InformeDesempeño guardar(InformeDesempeño informe) {
        return informeRepository.save(informe);
    }

    @Override
    public void eliminarInforme(Long id) {
       informeRepository.deleteById(id);
    }


    
}
