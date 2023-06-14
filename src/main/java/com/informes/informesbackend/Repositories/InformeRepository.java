package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.InformeDesempenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InformeRepository extends JpaRepository<InformeDesempenio, Long> {

    @Query(value = "SELECT * FROM db_informes_nuevo.informes_desempenio WHERE alumno_id=:alumno_id AND id_asignatura=:id_asignatura", nativeQuery=true)
    Optional<InformeDesempenio> FindByAlumno(Long alumno_id, Long id_asignatura);

    @Query(value = "SELECT * FROM db_informes_nuevo.informes_desempenio WHERE id_asignatura=:idAsignatura", nativeQuery=true)
    List<InformeDesempenio> findByAsignatura(Long idAsignatura);

}
