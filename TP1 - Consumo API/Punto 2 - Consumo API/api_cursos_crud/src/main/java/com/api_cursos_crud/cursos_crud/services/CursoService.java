package com.api_cursos_crud.cursos_crud.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api_cursos_crud.cursos_crud.model.Curso;
import com.api_cursos_crud.cursos_crud.repository.CursoRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class CursoService {

    private final RestTemplate restTemplate;
    private final CursoRepository repository;

    public CursoService(RestTemplate restTemplate, CursoRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public List<Curso> importarCursos() {

        String url = "http://localhost:8081/api/cursos";

        Curso[] cursos = restTemplate.getForObject(url, Curso[].class);

        if (cursos == null) {
            throw new RuntimeException("No se recibieron cursos desde la API");
        }

        for (Curso c : cursos) {
            c.setId(null); 
        }

        return repository.saveAll(Arrays.asList(cursos));
    }

    // Listar Cursos
    public List<Curso> getAll() {
        return repository.findAll();
    }

    // Guardar Cursos
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    // Eliminar cursos
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

