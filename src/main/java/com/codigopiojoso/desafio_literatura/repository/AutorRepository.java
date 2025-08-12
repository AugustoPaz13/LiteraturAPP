package com.codigopiojoso.desafio_literatura.repository;

import com.codigopiojoso.desafio_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    //Metodo para buscar autores por su nombre
    Autor findByNombreContainingIgnoreCase(String nombre);

    // Metodo para buscar autores que estaban vivos en un año específico
    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(Integer añoNacimiento, Integer añoFallecimiento);
}
