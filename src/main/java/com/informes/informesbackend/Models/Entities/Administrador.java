package com.informes.informesbackend.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
@Entity
@Setter
@NoArgsConstructor
@Data
@Table(name = "administradores")
public class Administrador {

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

    public String getNombreCompleto() {

        return this.apellido+" "+this.nombres;

    }
}
