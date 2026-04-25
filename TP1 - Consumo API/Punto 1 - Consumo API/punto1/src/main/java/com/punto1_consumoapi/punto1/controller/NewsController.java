package com.punto1_consumoapi.punto1.controller;

import org.springframework.web.bind.annotation.*;

import com.punto1_consumoapi.punto1.model.Articulo;
import com.punto1_consumoapi.punto1.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<Articulo> obtenerNoticias() {
        return newsService.getNoticias();
    }

    @GetMapping("/buscar/{palabra}")
    public List<Articulo> buscarNoticias(@PathVariable String palabra) {
        return newsService.buscarNoticias(palabra);
    }
}

