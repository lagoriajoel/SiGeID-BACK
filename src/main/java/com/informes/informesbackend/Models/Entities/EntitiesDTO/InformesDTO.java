package com.informes.informesbackend.Models.Entities.EntitiesDTO;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.Curso;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
public class InformesDTO {

    String anio;
    String descripcion;
    Long id_asignatura;
    //Curso curso;
    Alumno alumno;
    Set<Contenido> contenidos;


}
