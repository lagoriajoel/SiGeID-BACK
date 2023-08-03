package com.informes.informesbackend.Repositories;
import com.informes.informesbackend.Models.Entities.Directivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DirectivoRepository extends JpaRepository<Directivo, Long> {

    List<Directivo> findByApellido(String apellido);
    Optional<Directivo> findByDni(String dni);

    Optional<Directivo> findByEmail(String email);
}
