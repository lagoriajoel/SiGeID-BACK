package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignatura_id")
    private Long asignatura_id;
    @NotBlank
    private String nombre;
    @NotNull
    private String anioCurso;

    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;


    @OneToMany(mappedBy="asignatura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Contenido> contenidos=new HashSet<>();
//prueba para solucionar problema de asignatura sino aliminar "informe de desemepeño"
    @OneToMany(mappedBy="asignatura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<InformeDesempenio> informeDesempenios=new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    //genera problema multiple back reference
    //@JsonBackReference
    @JoinColumn(name="profesor_id")
    private Profesor profesor;

    private String criteriosEvaluacion;
    private String estrategiasEvaluacion;
}
