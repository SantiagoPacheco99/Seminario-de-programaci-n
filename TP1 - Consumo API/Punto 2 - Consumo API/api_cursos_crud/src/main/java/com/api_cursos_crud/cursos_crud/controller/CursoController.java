package com.api_cursos_crud.cursos_crud.controller;

import org.springframework.web.bind.annotation.*;
import com.api_cursos_crud.cursos_crud.model.Curso;
import com.api_cursos_crud.cursos_crud.services.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // Importar desde api_cursos
    @PostMapping("/importar")
    public List<Curso> importar() {
        return service.importarCursos();
    }

    // Listar cursos
    @GetMapping
    public List<Curso> listar() {
        return service.getAll();
    }

    // Crear curso
    @PostMapping
    public Curso guardar(@RequestBody Curso curso) {
        return service.guardar(curso);
    }

    // Eliminar curso
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

