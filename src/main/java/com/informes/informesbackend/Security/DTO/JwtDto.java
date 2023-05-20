package com.informes.informesbackend.Security.DTO;


import lombok.Data;

@Data
public class JwtDto {
    private String token;
    public JwtDto(String token) {
        this.token = token;

    }
}
