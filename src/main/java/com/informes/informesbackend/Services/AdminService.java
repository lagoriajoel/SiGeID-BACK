package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Repositories.AdminRepository;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Administrador> listar();
    Optional<Administrador> listarporId(Long id);

    Optional<Administrador> listarporDni(String id);
    Administrador guardar(Administrador admin);

    void eliminar(Long id);
    Optional<Administrador> porEmail(String email);

    List<Administrador> listarPorApellido(String apellido);
}
