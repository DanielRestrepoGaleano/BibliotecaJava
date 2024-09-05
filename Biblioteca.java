import java.util.Scanner;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogRecord;

public class Biblioteca {
    private static final Logger LOGGER = Logger.getLogger(Biblioteca.class.getName());
    
    static {
        // Remover los manejadores por defecto
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        // Configurar el logger para que escriba los mensajes en la consola
        Handler consoleHandler = new ConsoleHandler();
        Formatter formatter = new SimpleFormatter();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL); // Establecer el nivel de logueo
    }
    
    private static final String ARCHIVO_LIBROS = "libros.txt"; // CONSTANTE PARA GENERAR EL ARCHIVO .TXT

    // FUNCIÓN PARA MOSTRAR LOS LIBROS
    private static void mostrarLibros(LinkedList<Libro> biblioteca) {
        LOGGER.info("--- LIBROS EN LA BIBLIOTECA ---");
        boolean hayLibrosDisponibles = false;
        for (Libro libro : biblioteca) {
            if (libro.isDisponible()) {
                hayLibrosDisponibles = true;
                break;
            }
        }

        if (!hayLibrosDisponibles) {
            LOGGER.info("No hay libros disponibles");
        } else {
            for (Libro libro : biblioteca) {
                if (libro.isDisponible()) {
                    LOGGER.info(" ");
                    LOGGER.info(libro.toString());
                }
            }
        }
    }

    //FUNCION PARA LA POSICIÓN DE CADA LIBRO
    private static void posicionLibro(LinkedList<Libro> biblioteca) {
        if (biblioteca.isEmpty()) {
            LOGGER.warning("La biblioteca no tiene ningún libro.");
        } else {
            for (int l = 0; l < biblioteca.size(); l++) {
                Libro libro = biblioteca.get(l);
                int posicion = l + 1;
                LOGGER.info("[" + posicion + "] " + libro.getTitulo());
                if (!libro.isDisponible()) {
                    LOGGER.info("(EL LIBRO " + libro.getTitulo() + " SE ENCUENTRA OCULTO)");
                }
            }
        }
    }

    //FUNCIÓN PARA BORRAR UN LIBRO SEGÚN SU POSICIÓN DE FORMA DEFINITIVA
    private static void borrarLibroDefinitivo(LinkedList<Libro> biblioteca, Scanner scanner) {
        if (biblioteca.isEmpty()) {
            LOGGER.warning("No hay libros disponibles");
        } else {
            LOGGER.info("Seleccione la posición del libro a eliminar:");
            int posicionEliminar = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicionEliminar > 0 && posicionEliminar <= biblioteca.size()) {
                biblioteca.remove(posicionEliminar - 1);
            } else {
                LOGGER.warning("Posición inválida, intente nuevamente.");
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
            LOGGER.info("Libros guardados exitosamente.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar los libros: " + e.getMessage(), e);
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
                        LOGGER.log(Level.WARNING, "Error al parsear datos numéricos en la línea: " + linea, e);
                    }
                } else {
                    LOGGER.warning("Línea con formato incorrecto ignorada: " + linea);
                }
            }
            LOGGER.info("Libros cargados exitosamente. Total de libros: " + biblioteca.size() + "\n");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar los libros: " + e.getMessage(), e);
        }
    }

    // FUNCION PARA AGREGAR UN LIBRO NUEVO
    private static void agregarLibro(LinkedList<Libro> biblioteca, Scanner teclado) {
        LOGGER.info("Ingresar un nuevo libro");

        LOGGER.info("Ingrese el título del libro:");
        String titulo = teclado.nextLine();

        LOGGER.info("Ingrese el autor del libro:");
        String autor = teclado.nextLine();

        LOGGER.info("Ingrese el año de publicación:");
        int fechaPublicacion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        while (fechaPublicacion > 2024) {
            LOGGER.warning("La fecha " + fechaPublicacion + " es superior al año actual, vuelva a intentarlo.");
            fechaPublicacion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
        }

        LOGGER.info("Ingrese el número de páginas:");
        int numPaginas = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        LOGGER.info("¿El libro está disponible? (true/false):");
        boolean disponible = teclado.nextBoolean();
        teclado.nextLine(); // Consumir el salto de línea

        LOGGER.info("Ingrese el ISBN del libro:");
        String isbn = teclado.nextLine();

        LOGGER.info("Agregue una descripción del libro:");
        String descripcion = teclado.nextLine();

        Libro nuevoLibro = new Libro(titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion);
        biblioteca.add(nuevoLibro);

        guardarLibros(biblioteca);
    }

    // FUNCIÓN PARA EDITAR LIBROS EN FUNCIÓN DE SU POSICIÓN
    private static void editarLibro(LinkedList<Libro> biblioteca, Scanner teclado) {
        if (!biblioteca.isEmpty()) {
            posicionLibro(biblioteca);
            LOGGER.info("Ingrese la posición del libro para editar");
            int posicion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
            if (posicion > 0 && posicion <= biblioteca.size()) {
                biblioteca.get(posicion - 1).editarLibro(teclado);
                guardarLibros(biblioteca);
            } else {
                LOGGER.warning("Posición no encontrada");
            }
        } else {
            LOGGER.warning("No hay libros en la biblioteca.");
        }
    }

    //FUNCIÓN PARA CAMBIAR LA DISPONIBILIDAD DEL LIBRO SEGÚN SU POSICIÓN
    private static void cambiarEstado(LinkedList<Libro> biblioteca, Scanner teclado) {
        posicionLibro(biblioteca);
        LOGGER.info("Seleccione el número del libro para cambiar su estado (ocultar/mostrar):");
        int numLibro = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea
        if (numLibro > 0 && numLibro <= biblioteca.size()) {
            biblioteca.get(numLibro - 1).cambiarDisponibilidad();
        } else {
            LOGGER.warning("Número de libro inválido.");
        }

        guardarLibros(biblioteca);  // Guardar después de cambiar el estado
    }
    private static void removeDefaultHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
    }

    // Clase que define un formateador personalizado
    static class CustomFormatter extends SimpleFormatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        LinkedList<Libro> biblioteca = new LinkedList<>();
        cargarLibros(biblioteca);
        removeDefaultHandlers(LOGGER);
        LOGGER.setUseParentHandlers(false);
        CustomFormatter formatter = new CustomFormatter();

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        LOGGER.addHandler(handler);
        int x = 1;

        do {
            LOGGER.info("Por favor presione 1 para ingresar un nuevo libro");
            LOGGER.info("Por favor presione 2 para ver la lista de libros");
            LOGGER.info("Por favor presione 3 para ver la posición de los libros");
            LOGGER.info("Por favor presione 4 para cambiar el estado del libro");
            LOGGER.info("Por favor presione 5 para editar un libro");
            LOGGER.info("Por favor presione 6 para borrar de forma definitiva un libro de la lista");
            LOGGER.info("Por favor presione 0 para salir\n");
            LOGGER.info("Ingrese su opción");

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
                            LOGGER.warning("Opción inválida. Intente nuevamente.");
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Entrada inválida. Por favor ingrese un número.");
            }

        } while (x != 0);

        teclado.close();
    }
}
