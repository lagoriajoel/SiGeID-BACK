package com.informes.informesbackend.Models.Entities.EntitiesDTO;

import com.informes.informesbackend.Models.Entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
public class InformesDTO {

    String anio;
    String descripcion;
    Asignatura asignatura;
    Alumno alumno;
    Set<ContenidoAdeudado> contenidosAdeudados;
    String Profesor;


}
