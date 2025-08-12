package com.codigopiojoso.desafio_literatura.service;

import com.codigopiojoso.desafio_literatura.dto.AutorDTO;
import com.codigopiojoso.desafio_literatura.dto.LibroDTO;
import com.codigopiojoso.desafio_literatura.model.Autor;
import com.codigopiojoso.desafio_literatura.model.Libro;
import com.codigopiojoso.desafio_literatura.repository.AutorRepository;
import com.codigopiojoso.desafio_literatura.repository.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void buscarLibroPorTitulo(String titulo) {
        JsonNode jsonResponse = gutendexService.buscarLibroPorTitulo(titulo);

        if (jsonResponse != null) {
            JsonNode results = jsonResponse.get("results");
            if (results != null && results.isArray() && results.size() > 0) {
                JsonNode firstBook = results.get(0);
                LibroDTO libroDTO = new LibroDTO();
                libroDTO.setTitulo(firstBook.get("title").asText());
                libroDTO.setTotalDeDescargas(firstBook.get("download_count").asInt());

                // Procesar autores
                List<AutorDTO> autoresDTO = new ArrayList<>();
                JsonNode autoresNode = firstBook.get("authors");
                if (autoresNode != null && autoresNode.isArray()) {
                    autoresNode.forEach(autorNode -> {
                        AutorDTO autorDTO = new AutorDTO();
                        autorDTO.setNombre(autorNode.get("name").asText());
                        autorDTO.setFechaDeNacimiento(autorNode.get("birth_year").asInt());
                        autorDTO.setFechaDeFallecimiento(autorNode.get("death_year").asInt());
                        autoresDTO.add(autorDTO);
                    });
                }
                libroDTO.setAutores(autoresDTO);

                // Procesar idiomas
                List<String> idiomas = new ArrayList<>();
                JsonNode idiomasNode = firstBook.get("languages");
                if (idiomasNode != null && idiomasNode.isArray()) {
                    idiomasNode.forEach(idiomaNode -> idiomas.add(idiomaNode.asText()));
                }
                libroDTO.setIdiomas(idiomas);

                // Verificar si el libro ya existe en la base de datos
                Libro libroExistente = libroRepository.findByTitulo(libroDTO.getTitulo());
                if (libroExistente == null) {
                    // Mapear DTO a entidad
                    Libro libro = new Libro();
                    libro.setTitulo(libroDTO.getTitulo());
                    libro.setTotalDeDescargas(libroDTO.getTotalDeDescargas());
                    libro.setIdioma(libroDTO.getIdiomas().get(0)); // Usar el primer idioma
                    Autor autor = encontrarOcrearAutor(libroDTO.getAutores().get(0));
                    libro.setAutor(autor);
                    libroRepository.save(libro);

                    System.out.println("Libro guardado exitosamente: " + libro.getTitulo());
                } else {
                    System.out.println("Error: El libro '" + libroDTO.getTitulo() + "' ya está registrado en la base de datos.");
                }
            } else {
                System.out.println("No se encontró ningún libro con ese título en la API.");
            }
        }
    }

    public List<Libro> listarLibrosRegistrados() {
        return libroRepository.findAll();
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(anio, anio);
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    // Métodos privados para manejar la lógica de la API y el mapeo
    private Autor encontrarOcrearAutor(AutorDTO autorDTO) {
        Autor autorExistente = autorRepository.findByNombreContainingIgnoreCase(autorDTO.getNombre());
        if (autorExistente != null) {
            return autorExistente;
        } else {
            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(autorDTO.getNombre());
            nuevoAutor.setFechaDeNacimiento(autorDTO.getFechaDeNacimiento());
            nuevoAutor.setFechaDeFallecimiento(autorDTO.getFechaDeFallecimiento());
            return autorRepository.save(nuevoAutor);
        }
    }
}
