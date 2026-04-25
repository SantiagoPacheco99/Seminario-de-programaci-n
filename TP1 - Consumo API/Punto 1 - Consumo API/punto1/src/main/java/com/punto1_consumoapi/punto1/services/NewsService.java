package com.punto1_consumoapi.punto1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.punto1_consumoapi.punto1.model.Articulo;
import com.punto1_consumoapi.punto1.model.NewsResponse;

import java.util.List;

@Service
public class NewsService {
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.url}")
    private String baseUrl;


    // Devuelve todas las noticias
    public List<Articulo> getNoticias() {
        String url = baseUrl + "/everything?q=technology&apiKey=" + apiKey;
        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
        return response != null ? response.getArticles() : List.of();
    }

    // Buscar noticias por palabra clave
    public List<Articulo> buscarNoticias(String palabra) {
        String url = baseUrl + "/everything?q=" + palabra + "&apiKey=" + apiKey;

        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);

        return response.getArticles();
    }
}
