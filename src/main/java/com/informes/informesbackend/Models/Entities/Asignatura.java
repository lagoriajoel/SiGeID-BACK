package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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

    /**@JsonIgnore
    @ManyToMany
    @JoinTable(name = "profesores_asignaturas",
            joinColumns = @JoinColumn(name = "asignatura_id",
                    referencedColumnName = "asignatura_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id",
                    referencedColumnName = "profesor_id"))
    private Set<Profesor> profesores=new HashSet<>();**/

    @ManyToOne(fetch = FetchType.EAGER)
    //genera problema multiple back reference
    //@JsonBackReference
    @JoinColumn(name="profesor_id")
    private Profesor profesor;

}
