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

}
