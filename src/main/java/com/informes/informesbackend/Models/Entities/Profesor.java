package com.informes.informesbackend.Models.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.informes.informesbackend.Security.Entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Profesor {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "profesor_id")
   private Long id;
    @NotNull
    private String dni;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    @Email
    private String email;
    //@JsonIgnore
    /**@ManyToMany
    @JoinTable(name = "profesores_asignaturas",
                    joinColumns = @JoinColumn(name = "profesor_id",
                    referencedColumnName = "profesor_id"),
                    inverseJoinColumns = @JoinColumn(name = "asignatura_id",
                    referencedColumnName = "asignatura_id"))
    private Set<Asignatura> asignaturas=new HashSet<>();
    **/

    @OneToMany(mappedBy = "profesor",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    /**al aplicar jsonIgnore se soluciona el error multi-back reference **/
    @JsonIgnore
    private Set<Asignatura> asignaturas=new HashSet<>();

  public String getNombreCompleto(){
      return this.nombre+" "+this.apellido;
  }
}
