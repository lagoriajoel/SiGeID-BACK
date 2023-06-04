package com.informes.informesbackend.Security.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class LoginUsuario {


    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String password;




}
