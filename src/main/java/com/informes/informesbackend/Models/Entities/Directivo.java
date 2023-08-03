package com.informes.informesbackend.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directivo {

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
