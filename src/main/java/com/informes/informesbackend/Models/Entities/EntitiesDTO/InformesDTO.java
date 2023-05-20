package com.informes.informesbackend.Models.Entities.EntitiesDTO;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.Curso;

import java.util.HashSet;
import java.util.Set;

public class InformesDTO {

    String anio;
    String descripcion;
    Curso curso;
    Alumno alumno;

    Set<Contenido> contenidos;


    public InformesDTO(String anio, String descripcion, Curso curso, Alumno alumno, Set<Contenido> contenidos) {
        this.anio = anio;
        this.descripcion = descripcion;
        this.curso = curso;
        this.alumno = alumno;
        this.contenidos=contenidos;


    }

    public Set<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(Set<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


}
