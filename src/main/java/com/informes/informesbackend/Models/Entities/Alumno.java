package com.informes.informesbackend.Models.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String dni;
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellido;

    @NotEmpty
    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso")
    private Curso curso;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InformeDesempenio> informeDesempenios=new HashSet<>();


    public String getNombreCompleto() {

        return this.apellido+" "+this.nombres;

    }
}
