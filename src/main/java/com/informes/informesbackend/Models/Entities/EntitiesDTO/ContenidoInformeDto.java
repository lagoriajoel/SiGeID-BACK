package com.informes.informesbackend.Models.Entities.EntitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContenidoInformeDto {
    private Long id;
    private String instanciaEvaluacion = "";
    private boolean aprobado;
}

