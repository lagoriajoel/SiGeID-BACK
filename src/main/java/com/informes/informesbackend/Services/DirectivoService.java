package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Administrador;
import com.informes.informesbackend.Models.Entities.Directivo;

import java.util.List;
import java.util.Optional;

public interface DirectivoService {
    List<Directivo> listar();
    Optional<Directivo> listarporId(Long id);

    Optional<Directivo> listarporDni(String id);
    Directivo guardar(Directivo admin);

    void eliminar(Long id);
    Optional<Directivo> porEmail(String email);
}
