package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.criterioInforme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface criterioAdeudadoRepository extends JpaRepository<criterioInforme,Long> {
}
