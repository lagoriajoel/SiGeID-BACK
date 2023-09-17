package com.informes.informesbackend.Models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cursos")

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;
    @NotBlank
    private String anio;
    @NotBlank
    private String division;
    @NotBlank
    private String turno;
    @NotBlank
    private String tecnicatura;
    @NotBlank
    private String cicloLectivo;

    @OneToMany(mappedBy="curso", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Alumno> alumnos = new HashSet();



}
