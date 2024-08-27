import java.util.Scanner;
import java.io.*;
import java.util.LinkedList;

public class Biblioteca {
    private static final String ARCHIVO_LIBROS = "libros.txt"; // CONSTANTE PARA GENERAR EL ARCHIVO .TXT

    // FUNCIÓN PARA MOSTRAR LOS LIBROS
    private static void mostrarLibros(LinkedList<Libro> biblioteca) {
        System.out.println("--- LIBROS EN LA BIBLIOTECA ---");
        boolean hayLibrosDisponibles = false;
        for (Libro libro : biblioteca) {
            if (libro.isDisponible()) {
                hayLibrosDisponibles = true;
                break;
            }
        }

        if (!hayLibrosDisponibles) {
            System.out.println("No hay libros disponibles");
        } else {
            for (Libro libro : biblioteca) {
                if (libro.isDisponible()) {
                    System.out.println("");
                    System.out.println(libro.toString());
                }
            }
        }
    }

    //FUNCION PARA LA POSICIÓN DE CADA LIBRO
    private static void posicionLibro(LinkedList<Libro> biblioteca) {
        if (biblioteca.isEmpty()) {
            System.out.println("La biblioteca no tiene ningún libro.");
        } else {
            for (int l = 0; l < biblioteca.size(); l++) {
                Libro libro = biblioteca.get(l);
                int posicion = l + 1;
                System.out.print("[" + posicion + "] ");
                System.out.println(libro.getTitulo());
                if (!libro.isDisponible()) {
                    System.out.println("(EL LIBRO " + libro.getTitulo() + " SE ENCUENTRA OCULTO)");
                }
            }
        }
    }

    //FUNCIÓN PARA BORRAR UN LIBRO SEGÚN SU POSICIÓN DE FORMA DEFINITIVA
    private static void borrarLibroDefinitivo(LinkedList<Libro> biblioteca, Scanner scanner) {
        if (biblioteca.isEmpty()) {
            System.out.println("No hay libros disponibles");
        } else {
            System.out.println("Seleccione la posición del libro a eliminar:");
            int posicionEliminar = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicionEliminar > 0 && posicionEliminar <= biblioteca.size()) {
                biblioteca.remove(posicionEliminar - 1);
            } else {
                System.out.println("Posición inválida, intente nuevamente.");
            }
            guardarLibros(biblioteca);
        }
    }

    // FUNCION PARA GUARDAR LIBROS COMO .TXT
    private static void guardarLibros(LinkedList<Libro> biblioteca) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_LIBROS))) {
            for (Libro libro : biblioteca) {
                escritor.println(libro.getTitulo() + "," +
                                 libro.getAutor() + "," +
                                 libro.getfechaPublicacion() + "," +
                                 libro.getNumPaginas() + "," +
                                 libro.isDisponible() + "," +
                                 libro.getIsbn() + "," +
                                 libro.getDescripcion());
            }
            System.out.println("Libros guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // FUNCION PARA CARGAR LOS LIBROS CUANDO SE CIERRE EL IDE
    private static void cargarLibros(LinkedList<Libro> biblioteca) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_LIBROS))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 7) {
                    try {
                        String titulo = partes[0];
                        String autor = partes[1];
                        int fechaPublicacion = Integer.parseInt(partes[2]);
                        int numPaginas = Integer.parseInt(partes[3]);
                        boolean disponible = Boolean.parseBoolean(partes[4]);
                        String isbn = partes[5];
                        String descripcion = partes[6];
    
                        biblioteca.add(new Libro(titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion));
                    } catch (NumberFormatException e) {
                        System.out.println("Error al parsear datos numéricos en la línea: " + linea);
                    }
                } else {
                    System.out.println("Línea con formato incorrecto ignorada: " + linea);
                }
            }
            System.out.println("Libros cargados exitosamente. Total de libros: " + biblioteca.size() + "\n" );
        } catch (IOException e) {
            System.out.println("Error al cargar los libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // FUNCION PARA AGREGAR UN LIBRO NUEVO
    private static void agregarLibro(LinkedList<Libro> biblioteca, Scanner teclado) {
        System.out.println("Ingresar un nuevo libro");

        System.out.println("Ingrese el título del libro:");
        String titulo = teclado.nextLine().toLowerCase();

        System.out.println("Ingrese el autor del libro:");
        String autor = teclado.nextLine().toLowerCase();

        System.out.println("Ingrese el año de publicación:");
        int fechaPublicacion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        while (fechaPublicacion > 2024) {
            System.out.println("La fecha " + fechaPublicacion + " es superior al año actual, vuelva a intentarlo.");
            fechaPublicacion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
        }

        System.out.println("Ingrese el número de páginas:");
        int numPaginas = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        System.out.println("¿El libro está disponible? (true/false):");
        boolean disponible = teclado.nextBoolean();
        teclado.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese el ISBN del libro:");
        String isbn = teclado.nextLine();

        System.out.println("Agregue una descripción del libro:");
        String descripcion = teclado.nextLine();

        Libro nuevoLibro = new Libro(titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion);
        biblioteca.add(nuevoLibro);

        guardarLibros(biblioteca);
    }

    // FUNCIÓN PARA EDITAR LIBROS EN FUNCIÓN DE SU POSICIÓN
    private static void editarLibro(LinkedList<Libro> biblioteca, Scanner teclado) {
        if (!biblioteca.isEmpty()) {
            posicionLibro(biblioteca);
            System.out.println("Ingrese la posición del libro para editar");
            int posicion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
            if (posicion > 0 && posicion <= biblioteca.size()) {
                biblioteca.get(posicion - 1).editarLibro(teclado);
                guardarLibros(biblioteca);    
            } else {
                System.out.println("Posición no encontrada");
            }
        } else {
            System.out.println("No hay libros en la biblioteca.");
        }
    }

    //FUNCIÓN PARA CAMBIAR LA DISPONIBILIDAD DEL LIBRO SEGÚN SU POSICIÓN
    private static void cambiarEstado(LinkedList<Libro> biblioteca, Scanner teclado) {
        posicionLibro(biblioteca);
        System.out.println("Seleccione el número del libro para cambiar su estado (ocultar/mostrar):");
        int numLibro = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea
        if (numLibro > 0 && numLibro <= biblioteca.size()) {
            biblioteca.get(numLibro - 1).cambiarDisponibilidad();
        } else {
            System.out.println("Número de libro inválido.");
        }

        guardarLibros(biblioteca);  // Guardar después de cambiar el estado
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        LinkedList<Libro> biblioteca = new LinkedList<>();
        cargarLibros(biblioteca);

        int x = 1;

        do {
            System.out.println("Por favor presione 1 para ingresar un nuevo libro");
            System.out.println("Por favor presione 2 para ver la lista de libros");
            System.out.println("Por favor presione 3 para ver la posición de los libros");
            System.out.println("Por favor presione 4 para cambiar el estado del libro");
            System.out.println("Por favor presione 5 para editar un libro");
            System.out.println("Por favor presione 6 para borrar de forma definitiva un libro de la lista");
            System.out.println("Por favor presione 0 para salir" + "\n");
            System.out.println("Ingrese su opción");

            String input = teclado.nextLine();

            try {
                x = Integer.parseInt(input);
                if (x != 0) {
                    switch (x) {
                        case 1:
                            agregarLibro(biblioteca, teclado);
                            break;

                        case 2:
                            mostrarLibros(biblioteca);
                            break;

                        case 3:
                            posicionLibro(biblioteca);
                            break;

                        case 4:
                            cambiarEstado(biblioteca, teclado);
                            break;

                        case 5:
                            editarLibro(biblioteca, teclado);
                            break;

                        case 6:
                            borrarLibroDefinitivo(biblioteca, teclado);
                            break;

                        default:
                            System.out.println("Opción inválida, intente nuevamente.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        } while (x != 0);

        teclado.close();
    }
}