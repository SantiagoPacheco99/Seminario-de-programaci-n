package com.api_cursos_crud.cursos_crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String profesor;

    public Curso(Long id, String nombre, String profesor) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
    }

    public Curso() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getProfesor() { return profesor; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}