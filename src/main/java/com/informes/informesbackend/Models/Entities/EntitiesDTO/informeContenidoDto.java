package com.informes.informesbackend.Models.Entities.EntitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class informeContenidoDto {
    private Long id;
    private String fecha_instancia;
    private String presidente_mesa_instancia="";

}
