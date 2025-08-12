package com.codigopiojoso.desafio_literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {

    private String titulo;

    @JsonProperty("authors")
    private List<AutorDTO> autores;

    @JsonProperty("languages")
    private List<String> idiomas;

    @JsonProperty("download_count")
    private Integer totalDeDescargas;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getTotalDeDescargas() {
        return totalDeDescargas;
    }

    public void setTotalDeDescargas(Integer totalDeDescargas) {
        this.totalDeDescargas = totalDeDescargas;
    }
}