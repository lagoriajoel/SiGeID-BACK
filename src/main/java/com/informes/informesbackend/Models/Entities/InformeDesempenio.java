package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "informes_desempenio")

public class InformeDesempenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "informe_id")
    private Long id;


    @OneToMany(mappedBy="informeDesempenio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<criterioInforme> criteriosEvaluacion= new HashSet<>();
    @OneToMany(mappedBy="informeDesempenio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<estrategiaInforme> estrategiasEvaluacion=new HashSet<>();

    private String profesorNombre;
    @Temporal(TemporalType.DATE)
    private Date fechaInstancia_1;
    @Temporal(TemporalType.DATE)
    private Date fechaInstancia_2;
    @Temporal(TemporalType.DATE)
    private Date fechaInstancia_3;
    @Temporal(TemporalType.DATE)
    private Date fechaInstancia_4;

    private String presidenteMesaInstancia_1="";
    private String presidenteMesaInstancia_2="";
    private String presidenteMesaInstancia_3="";
    private String presidenteMesaInstancia_4="";


    @Column(name = "Fecha_Creacion", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="asignatura_id", nullable=false)
    private Asignatura asignatura;



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="alumno_id", nullable=false)
    private Alumno alumno;




    @OneToMany(mappedBy="informeDesempenio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ContenidoAdeudado> contenidosAdeudados = new ArrayList<>();




}
