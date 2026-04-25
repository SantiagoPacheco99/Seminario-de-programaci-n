package com.api_cursos.cursos.controller;

import org.springframework.web.bind.annotation.*;
import com.api_cursos.cursos.model.Curso;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @GetMapping
    public List<Curso> getCursos() {
        return List.of(
            new Curso(1, "POO", "Perez"),
            new Curso(2, "Bases de Datos", "Gomez"),
            new Curso(3, "Redes y Telecomunicaciones", "Lopez"),
            new Curso(4, "Sistemas Operativos", "Martinez"),
            new Curso(5, "Laboratorio de Programación", "Diaz")
        );
    }
}

