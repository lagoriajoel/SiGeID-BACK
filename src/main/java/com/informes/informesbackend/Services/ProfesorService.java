package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Profesor;

import java.util.List;
import java.util.Optional;

public interface ProfesorService {

    List<Profesor> listar();
    Optional<Profesor> listarporId(Long id);

    Optional<Profesor> listarporDni(String id);

    Profesor guardar(Profesor profesor);

    void eliminar(Long id);

    Optional<Profesor> porEmail(String email);

    List<Profesor> listarPorApellido(String apellido);


}
