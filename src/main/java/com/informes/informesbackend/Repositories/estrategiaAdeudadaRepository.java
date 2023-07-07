package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.estrategiaInforme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface estrategiaAdeudadaRepository extends JpaRepository<estrategiaInforme, Long> {
}
