package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.ContenidoAdeudado;
import com.informes.informesbackend.Models.Entities.InformeDesempenio;
import com.informes.informesbackend.Repositories.AlumnoRepository;
import com.informes.informesbackend.Repositories.AsignaturaRepository;
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

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Override
    public InformeDesempenio asignarContenidoAdeudado(Long idInforme, Set<ContenidoAdeudado> contenidos) {

        Set<ContenidoAdeudado> contenidoSet=null;
        InformeDesempenio informeDesempenio = informeRepository.findById(idInforme).get();
       contenidoSet= informeDesempenio.getContenidosAdeudados();
       contenidoSet.addAll(contenidos);
       informeDesempenio.setContenidosAdeudados(contenidos);
      return informeRepository.save(informeDesempenio);
    }

    @Override
    public List<InformeDesempenio> listar() {
        return informeRepository.findAll();
    }

    @Override
    public Optional<InformeDesempenio> listarporId(Long id) {
        return informeRepository.findById(id);
    }

    @Override
    public InformeDesempenio guardar(InformeDesempenio informe) {
        return informeRepository.save(informe);
    }

    @Override
    public void eliminarInforme(Long id) {
       informeRepository.deleteById(id);
    }

    @Override
    public List<InformeDesempenio> listarPorAsignatura(Long id_asignatura) {

        return informeRepository.findByAsignatura(id_asignatura);
    }


    @Override
    public Optional<InformeDesempenio> encontrarAlumno(Long alumno_id, Long id_asignatura) {
        return informeRepository.FindByAlumno(alumno_id, id_asignatura);
    }




}
