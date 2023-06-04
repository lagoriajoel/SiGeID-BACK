package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;
    @Override
    public List<Administrador> listar() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Administrador> listarporId(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Administrador> listarporDni(String id) {
        return adminRepository.findByDni(id);
    }

    @Override
    public Administrador guardar(Administrador admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void eliminar(Long id) {
         adminRepository.deleteById(id);
    }

    @Override
    public Optional<Administrador> porEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public List<Administrador> listarPorApellido(String apellido) {
        return adminRepository.findByApellido(apellido);
    }
}
