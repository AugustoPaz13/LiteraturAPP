package com.codigopiojoso.desafio_literatura.repository;

import com.codigopiojoso.desafio_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Metodo para buscar un libro por su título
    Libro findByTitulo(String titulo);

    // Metodo para listar libros por idioma
    List<Libro> findByIdioma(String idioma);

}