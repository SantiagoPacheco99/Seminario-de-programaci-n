package com.api_cursos_crud.cursos_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api_cursos_crud.cursos_crud.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
