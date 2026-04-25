package com.api_cursos.cursos.model;

public class Curso {

    private int id;
    private String nombre;
    private String profesor;

    public Curso(int id, String nombre, String profesor) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
    }

    public Curso(){}

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getProfesor() { return profesor; }
}

