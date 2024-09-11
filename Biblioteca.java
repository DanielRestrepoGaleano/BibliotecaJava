import java.util.Scanner;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.*;



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

    //FUNCION PARA LA POSICIÓN DE CADA LIBRO2

    private static void posicionLibro(LinkedList<Libro> biblioteca) {
        if (biblioteca.isEmpty()) {
            LOGGER.warning("La biblioteca no tiene ningún libro.");
        } else {
            for (Libro libro : biblioteca) {
                LOGGER.info("[" + libro.getId() + "] " + libro.getTitulo());
                if (!libro.isDisponible()) {
                    LOGGER.info("(EL LIBRO " + libro.getTitulo() + " SE ENCUENTRA OCULTO)");
                }
            }
        }
    }

    //FUNCIÓN PARA BORRAR UN LIBRO SEGÚN SU POSICIÓN DE FORMA DEFINITIVA
    private static void eliminarLibro(LinkedList<Libro> biblioteca, Scanner teclado) throws Exception {
        if (!biblioteca.isEmpty()) {
            posicionLibro(biblioteca);
            LOGGER.info("Ingrese el ID del libro para eliminar");
            int id = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
            
            Libro libroAEliminar = null;
            for (Libro libro : biblioteca) {
                if (libro.getId() == id) {
                    libroAEliminar = libro;
                    break;
                }
            }
            
            if (libroAEliminar != null) {
                biblioteca.remove(libroAEliminar);
                ConexionBD.eliminarLibro(id);
                LOGGER.info("Libro eliminado exitosamente de la base de datos.");
                guardarLibros(biblioteca);
            } else {
                LOGGER.warning("ID no encontrado");
            }
        } else {
            LOGGER.warning("No hay libros en la biblioteca.");
        }
    }


    // FUNCION PARA GUARDAR LIBROS COMO .TXT
    private static void guardarLibros(LinkedList<Libro> biblioteca) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_LIBROS))) {
            for (Libro libro : biblioteca) {
                escritor.println(libro.getId() + "," +
                                 libro.getTitulo() + "," +
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
            int lineaNumero = 0;
            while ((linea = lector.readLine()) != null) {
                lineaNumero++;
                Libro libro = Libro.aLibro(linea);
                if (libro != null) {
                    biblioteca.add(libro);
                } else {
                    LOGGER.warning("No se pudo crear un libro a partir de la línea " + lineaNumero + ": " + linea);
                }
            }
            LOGGER.info("Libros cargados exitosamente. Total de libros válidos: " + biblioteca.size());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar los libros: " + e.getMessage(), e);
        }
    }

    // FUNCION PARA AGREGAR UN LIBRO NUEVO
    // Modificación en la función para agregar libros
private static void agregarLibro(LinkedList<Libro> biblioteca, Scanner teclado) throws Exception {
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

    LOGGER.info("Ingrese la cantidad de libros que desea agregar:");
    int cantidad = teclado.nextInt();
    teclado.nextLine(); // Consumir el salto de línea

    for (int i = 0; i < cantidad; i++) {
        int nuevoId = ConexionBD.obtenerSiguienteId();
        Libro nuevoLibro = new Libro(nuevoId, titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion);
        biblioteca.add(nuevoLibro);
        ConexionBD.crearLibro(nuevoLibro);
        LOGGER.info("Libro agregado exitosamente a la base de datos.");;
    }

    guardarLibros(biblioteca);
   
}

// Nueva función para editar libros por ISBN y cantidad
private static void editarLibroPorId(LinkedList<Libro> biblioteca, Scanner teclado) throws Exception {
    LOGGER.info("Ingrese el ID del libro a editar:");
    int idBuscado = teclado.nextInt();
    teclado.nextLine(); // Consumir el salto de línea

    Libro libroAEditar = null;
    for (Libro libro : biblioteca) {
        if (libro.getId() == idBuscado) {
            libroAEditar = libro;
            break;
        }
    }

    if (libroAEditar == null) {
        LOGGER.warning("No se encontró un libro con el ID " + idBuscado);
        return;
    }

    libroAEditar.editarLibro(teclado);
    ConexionBD.actualizarLibro(idBuscado, libroAEditar);
    LOGGER.info("Libro actualizado exitosamente en la base de datos.");

    guardarLibros(biblioteca);
}




    // FUNCIÓN PARA EDITAR LIBROS EN FUNCIÓN DE SU POSICIÓN
    private static void editarLibro(LinkedList<Libro> biblioteca, Scanner teclado) throws Exception {
        if (!biblioteca.isEmpty()) {
            posicionLibro(biblioteca);
            LOGGER.info("Ingrese la posición del libro para editar");
            int posicion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
            if (posicion > 0 && posicion <= biblioteca.size()) {
            Libro libroAEditar = biblioteca.get(posicion - 1);
            libroAEditar.editarLibro(teclado);
            ConexionBD.actualizarLibro(libroAEditar.getId(), libroAEditar);
            LOGGER.info("Libro actualizado exitosamente en la base de datos.");
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
    
  
    public static void main(String[] args) throws Exception {
        
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
            LOGGER.info("Por favor presione 7 para editar libros por ID");
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
                            eliminarLibro(biblioteca, teclado);
                            break;
                            case 7:
                            editarLibroPorId(biblioteca, teclado);
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
