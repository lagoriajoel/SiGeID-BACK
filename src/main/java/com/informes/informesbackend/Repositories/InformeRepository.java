package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.InformeDesempenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InformeRepository extends JpaRepository<InformeDesempenio, Long> {

    @Query(value = "SELECT * FROM informes_desempenio WHERE alumno_id=:alumno_id AND asignatura_id=:id_asignatura", nativeQuery=true)
    Optional<InformeDesempenio> FindByAlumno(Long alumno_id, Long id_asignatura);

    @Query(value = "SELECT * FROM informes_desempenio WHERE asignatura_id=:idAsignatura", nativeQuery=true)
    List<InformeDesempenio> findByAsignatura(Long idAsignatura);

    @Query(value = "SELECT * FROM informes_desempenio where asignatura_id in (SELECT asignatura_id FROM db_informes_nuevo.asignaturas where nombre like :asignatura and anio_curso=:curso);", nativeQuery=true)
    List<InformeDesempenio> findByNombreOfAsignatura(@Param("asignatura") String asignatura , String curso);

}
