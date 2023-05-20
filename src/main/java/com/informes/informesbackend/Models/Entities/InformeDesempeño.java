package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "informes_desempenio")

public class InformeDesempeño {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informe_id")
    private Long id;

    private String descripcion;

    @Column(name = "Fecha_Creacion", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="curso_id", nullable=false)
    private Curso curso;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="alumno_id", nullable=false)
    private Alumno alumno;



    @ManyToMany
    @JoinTable(name = "contenidos_adeudados",
            joinColumns = @JoinColumn(name = "informe_id",
                    referencedColumnName = "informe_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id",
                    referencedColumnName = "contenido_id"))
    private Set<Contenido> contenidosAdeudados = new HashSet<>();




}
