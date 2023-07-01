package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class estrategiasEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String estrategias;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonBackReference
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;
}
