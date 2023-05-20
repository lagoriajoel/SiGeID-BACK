package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.InformeDesempeño;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformeRepository extends JpaRepository<InformeDesempeño, Long> {
}
