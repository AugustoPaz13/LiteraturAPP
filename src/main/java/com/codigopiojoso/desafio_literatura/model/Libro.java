package com.codigopiojoso.desafio_literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;
    private Integer totalDeDescargas;


    public Integer getTotalDeDescargas() {
        return totalDeDescargas;
    }

    public void setTotalDeDescargas(Integer totalDeDescargas) {
        this.totalDeDescargas = totalDeDescargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return """
                -----------------------------
                Libro: %s
                Autor: %s
                Idioma: %s
                Total de descargas: %d
                -----------------------------
                """.formatted(titulo, autor.getNombre(), idioma, totalDeDescargas);
    }
}