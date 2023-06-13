package com.informes.informesbackend.Repositories;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Asignatura;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.InformeDesempeño;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InformeRepository extends JpaRepository<InformeDesempeño, Long> {

    @Query(value = "SELECT * FROM db_informes2.informes_desempenio WHERE alumno_id=:alumno_id AND id_asignatura=:id_asignatura", nativeQuery=true)
    Optional<InformeDesempeño> FindByAlumno(Long alumno_id, Long id_asignatura);

    @Query(value = "SELECT * FROM db_informes2.informes_desempenio WHERE id_asignatura=:idAsignatura", nativeQuery=true)
    List<InformeDesempeño> findByAsignatura(Long idAsignatura);

}
