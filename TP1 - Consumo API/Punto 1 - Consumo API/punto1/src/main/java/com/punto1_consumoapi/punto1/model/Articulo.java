package com.punto1_consumoapi.punto1.model;

public class Articulo {

    private String title;
    private String description;
    private String url;
    private Source source;

    // getters y setters

    public static class Source {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Source getSource() { return source; }
    public void setSource(Source source) { this.source = source; }
}