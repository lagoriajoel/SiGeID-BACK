package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.ContenidoAdeudado;
import com.informes.informesbackend.Models.Entities.InformeDesempenio;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface informeService {
    InformeDesempenio asignarContenidoAdeudado(Long idInforme, List<ContenidoAdeudado> contenidos);

    List<InformeDesempenio> listar();
    Optional<InformeDesempenio> listarporId(Long id);
    InformeDesempenio guardar(InformeDesempenio informe);
    void eliminarInforme(Long id);

    List<InformeDesempenio> listarPorAsignatura(Long idAsignatura);
    List<InformeDesempenio> listarPorAlumno(Long idAlumno);

    Optional<InformeDesempenio> encontrarAlumno(Long alumno_id, Long id_asignatura);

    List<InformeDesempenio> listarPorNombreAsignatura(String asignatura, String anio);

    List<InformeDesempenio> listarPorAnio(String anio);

    int InformesPorAsignaturasAnio(String Asignatura, String anio);
    int NumAlumnosConInformesPorAnio(String anio);
}
