package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contenidos")
public class Contenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "contenido_id")
     private Long id;
    @NotBlank
    private String nombre;

    private String descripcion;


     @JsonIgnore
    @ManyToMany
    //@JsonBackReference
    @JoinTable(name = "contenidos_adeudados",
            joinColumns = @JoinColumn(name = "contenido_id",
                    referencedColumnName = "contenido_id"),
            inverseJoinColumns = @JoinColumn(name = "informe_id",
                    referencedColumnName = "informe_id"))
    private Set<InformeDesempeño> Informes_desempeño = new HashSet<>();





    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

}


