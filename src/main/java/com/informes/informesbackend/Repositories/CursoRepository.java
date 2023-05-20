package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
