package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.InformeDesempeño;

import java.util.List;
import java.util.Optional;

public interface informeService {
    InformeDesempeño asignarContenidoAdeudado(Long id, Long contenidoId);

    List<InformeDesempeño> listar();
    Optional<InformeDesempeño> listarporId(Long id);
    InformeDesempeño guardar(InformeDesempeño informe);
    void eliminarInforme(Long id);

    List<InformeDesempeño> listarPorAsignatura(Long idAsignatura);

    Optional<InformeDesempeño> encontrarAlumno(Long alumno_id, Long id_asignatura);

}
