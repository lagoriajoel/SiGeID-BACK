package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class criterioInforme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

   private Long id;
    private String criterio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Informe_id", nullable = false)
    private InformeDesempenio informeDesempenio;



}
