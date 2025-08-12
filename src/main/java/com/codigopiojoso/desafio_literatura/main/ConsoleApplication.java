package com.codigopiojoso.desafio_literatura.main;

import com.codigopiojoso.desafio_literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleApplication implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        mostrarMenu();
    }

    private void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("---------------------------------");
            System.out.println("             Menú");
            System.out.println("---------------------------------");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año determinado");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.println("---------------------------------");
            System.out.print("Elige una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Por favor, ingresa un número.");
            }
        }
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo();
            case 2 -> listarLibrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> listarAutoresVivosEnAnio();
            case 5 -> listarLibrosPorIdioma();
            case 0 -> {
                System.out.println("Saliendo de la aplicación. ¡Hasta la próxima!");
                System.exit(0);
            }

            default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Introduce el título del libro: ");
        String titulo = scanner.nextLine();
        libroService.buscarLibroPorTitulo(titulo);
    }

    private void listarLibrosRegistrados() {
        libroService.listarLibrosRegistrados().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        libroService.listarAutoresRegistrados().forEach(System.out::println);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Introduce el año para buscar autores vivos: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine());
            libroService.listarAutoresVivosEnAnio(anio).forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Por favor, ingresa un número.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Introduce el idioma (es, en, fr, pt): ");
        String idioma = scanner.nextLine();
        libroService.listarLibrosPorIdioma(idioma).forEach(System.out::println);
    }
}