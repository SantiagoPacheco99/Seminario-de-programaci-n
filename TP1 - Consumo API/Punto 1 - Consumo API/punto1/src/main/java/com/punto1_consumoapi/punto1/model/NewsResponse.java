package com.punto1_consumoapi.punto1.model;

import java.util.List;

public class NewsResponse {

    private String status;
    private int totalResults;
    private List<Articulo> articles;

    public List<Articulo> getArticles() {
        return articles;
    }

    public void setArticles(List<Articulo> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

