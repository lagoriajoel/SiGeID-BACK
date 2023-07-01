package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {


    @Query(value = "SELECT * FROM contenido as informes where asignatura_id=:idAsignatura", nativeQuery=true)
    List<Contenido> findByAsignatura(Long idAsignatura);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM contenido WHERE contenido_id =:id", nativeQuery=true)
    void deleteContenido(Long id);
}
