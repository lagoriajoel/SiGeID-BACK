package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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


    @OneToMany(mappedBy="asignatura", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Contenido> contenidos=new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesores_asignaturas",
            joinColumns = @JoinColumn(name = "asignatura_id",
                    referencedColumnName = "asignatura_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id",
                    referencedColumnName = "profesor_id"))
    private Set<Profesor> profesores=new HashSet<>();

}
