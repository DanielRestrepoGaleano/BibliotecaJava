package ORIENTADOAOBJETOS;

import java.util.Scanner;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase principal de la biblioteca que permite la gestión de los libros,
 * gestión de usuarios y realizar
 * prestamos
 * 
 * @autor Daniel Restrepo Galeano
 * @version 1.0A
 */
public class Biblioteca {
    // Logger para la clase
    private static final Logger LOGGER = Logger.getLogger(Biblioteca.class.getName());
    // Variables de conexion y gestion de usuarios
    private static Connection conexion;
    private static GestorUsuarios gestorUsuarios;
    private static Usuario usuarioActual;

    // El código abajo es un bloque estatico que configura los LOGGERS de java
    static {
        // Remover los handlers por defecto
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
    // Constante para generar el archivo .txt donde se da la persistencia de los
    // libros
    private static final String ARCHIVO_LIBROS = "libros.txt";

    /**
     * La función "mostrarLibros" Muestra los libros disponibles en la biblioteca
     * usando una LinkedList de Libro
     * objects.
     * 
     * @param biblioteca Una LinkedList de objetos Libro , Que represetan una
     *                   librería.
     */
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

    /**
     * La función `posicionLibro` itera sobre la LinkedList de libro, mostrando ID y
     * título,
     * e indicando si un libro está disponible o no.
     * 
     * @param biblioteca LinkedList<Libro> biblioteca: una linkedList Que contiene
     *                   los objetos de tipo libro.
     *                   el método "posicionLibro" Itera sobre cada uno de los
     *                   elementos en la linkedList y muestra la infomarción
     *                   sobre cada objeto Libro, como el ID y el título.
     */
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

    /**
     * La función `eliminarLibro` elimina un libro de la LinkedList basado en el ID,
     * actualiza el mensaje y muestra el mensaje.
     * 
     * @param biblioteca `biblioteca` es una LinkedList thque contiene objetos de
     *                   tipo `Libro`, representando
     *                   una libreria.
     * @param teclado    el párametro `teclado` en el método `eliminarLibro`es de
     *                   tipo `Scanner`. es usado
     *                   para leer el input del usuario, como el ID del libro a
     *                   eliminar. la clase `Scanner` en
     *                   Java es uasada para obtener el input de datos primitivos
     *                   como int,
     * @throws Exception exepecion general
     */
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

    /**
     * La función `guardarLibros` guarda la lista de libros en un archivo.
     * 
     * @param biblioteca el párametro `biblioteca` es una LinkedList que contiene
     *                   obetos tipo Libro. el
     *                   método `guardarLibros` toma la LinkedList como input e
     *                   itera sobre cada objeto Libro en la
     *                   lista para escribir sus atributos en un .txt.
     */
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

    /**
     * la función `cargarLibros` lee el archivo que contiene información del libro,
     * creando objetos `Libro` desde
     * su información, y los añade a una `LinkedList<Libro>`, mostrando todos los
     * errores (si ocurren)
     * en el proceso.
     * 
     * @param biblioteca es una LinkedList `biblioteca`que alamcena instancias de la
     *                   clase `Libro`,
     *                   reprensantando una colección de libros en la bibliotecas.
     *                   el método `cargarLibros`
     *                   lee la información de los libros
     *                   del archivo `ARCHIVO_LIBROS`, creando objetos `Libro` con
     *                   su información.
     */
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

    /**
     * la función `agregarLibro` permite ingresar un nuevo libro, valida
     * año de publicación, y crea multiples copias de un libro o una mientras que
     * guarda
     * la información en la base de datos.
     * 
     * @param biblioteca LinkedList<Libro> biblioteca - este párametro representa
     *                   una colección
     *                   de libros en la
     *                   librería.es una lista de objetos de tipo libro.
     * @param teclado    el párametro `teclado` en el método `agregarLibro` es de
     *                   tipo Scanner `Scanner`. es usado
     *                   para leer el input del usuario. La clase `Scanner` en Java
     *                   se usa para obtener
     *                   el input de datos primitivos
     */
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
            Libro nuevoLibro = new Libro(nuevoId, titulo, autor, fechaPublicacion, numPaginas, disponible, isbn,
                    descripcion);
            biblioteca.add(nuevoLibro);
            ConexionBD.crearLibro(nuevoLibro);
            LOGGER.info("Libro agregado exitosamente a la base de datos.");
        }

        guardarLibros(biblioteca);

    }

    /**
     * La función `editarLibro` permite editar un libro actualizando su
     * información en la base de datos despues de seleccionar la posición.
     * 
     * @param biblioteca biblioteca es una LinkedList que contiene objetos de tipo
     *                   libro.
     * @param teclado    el párametro `teclado` en el método `editarLibro` es tipo
     *                   `Scanner`. este
     *                   párametro es usado para leer el input del usuario, como la
     *                   posicion del libro a editar. la
     *                   clase `Scanner` en Java se usa para obtener datos
     *                   primitivos por consola,
     */
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
                guardarLibros(biblioteca);
                LOGGER.info("Libro actualizado exitosamente en la base de datos.");
            } else {
                LOGGER.warning("Posición no encontrada");
            }
        } else {
            LOGGER.warning("No hay libros en la biblioteca.");
        }
    }

    /**
     * esta función cambia la disponibilidad de un libro basado en el
     * input.
     * 
     * @param biblioteca `biblioteca` es una LinkedList que contiene objetos de tipo
     *                   Libro.
     * @param teclado    el párametro `teclado` en el método `cambiarEstado` es de
     *                   tipo `Scanner`. es
     *                   usadp para leer el input, seleccionando el número del libro
     *                   para
     *                   (ocultar/mostrar).
     */
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

        guardarLibros(biblioteca); // Guardar después de cambiar el estado
    }

    /**
     * la función `removeDefaultHandlers` revueme todos los manejadores del Logger
     * dado como instancia
     * 
     * @param logger el párametro `logger` es una instancia de la clase `Logger`.
     */
    private static void removeDefaultHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
    }

    /**
     * la clase CustomFormatter extiende a SimpleFormatter y sobreescribe el formato
     * del método y devuelve el log
     * record.
     */
    static class CustomFormatter extends SimpleFormatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }

    /**
     * La función `mostrarMenuInicial` muestra un menú para ingresar,
     * registrarse, o salir
     * según el input.
     * 
     * @param teclado `teclado` es un objeto de `Scanner` usado para leer el input
     *                del usuario en consola
     *                es pasado como párametro al método
     *                `mostrarMenuInicial` para permitir al usuario
     *                para seleccionar una opción
     */
    private static void mostrarMenuInicial(Scanner teclado) {
        LOGGER.info("1. Iniciar sesión");
        LOGGER.info("2. Registrarse");
        LOGGER.info("3. Salir");
        LOGGER.info("Seleccione una opción:");

        int opcion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        switch (opcion) {
            case 1:
                iniciarSesion(teclado);
                break;
            case 2:
                registrarUsuario(teclado);
                break;
            case 3:
                LOGGER.info("Gracias por usar la biblioteca. ¡Hasta pronto!");
                System.exit(0);
            default:
                LOGGER.warning("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    /**
     * la función `iniciarSesion` pide usuario y contraseña, intentando
     * autenticar al usuario.
     * 
     * @param teclado `teclado` es un objeto de `Scanner` usado para leer el input
     *                en el método `iniciarSesion`,es usado para leer el usuario y
     *                contraseña durante el proceso de inicio.
     */
    private static void iniciarSesion(Scanner teclado) {
        LOGGER.info("Ingrese su nombre de usuario:");
        String nombreUsuario = teclado.nextLine();
        LOGGER.info("Ingrese su contraseña:");
        String contrasena = teclado.nextLine();

        usuarioActual = gestorUsuarios.autenticarUsuario(nombreUsuario, contrasena);
        if (usuarioActual != null) {
            LOGGER.info("Inicio de sesión exitoso. Bienvenido, " + usuarioActual.getNombreUsuario());
        } else {
            LOGGER.warning("Nombre de usuario o contraseña incorrectos. Por favor, intente de nuevo.");
        }
    }

    /**
     * La función `registrarUsuario` solicita al usuario que ingrese un nombre de
     * usuario, una contraseña y un correo electrónico,
     * crea un nuevo objeto `Usuario` con la información proporcionada e intenta
     * registrar al usuario utilizando un
     * objeto `gestorUsuarios`, registrando mensajes de éxito o fallo según
     * corresponda.
     * 
     * @param teclado `teclado` es un objeto `Scanner` que se utiliza para leer la
     *                entrada del usuario en el método
     *                `registrarUsuario`. Generalmente se usa para leer la entrada
     *                del usuario desde la consola u otra fuente de entrada.
     *                En este método, `teclado` se utiliza para leer la entrada del
     *                usuario.
     */
    private static void registrarUsuario(Scanner teclado) {
        LOGGER.info("Ingrese un nombre de usuario:");
        String nombreUsuario = teclado.nextLine();
        LOGGER.info("Ingrese una contraseña:");
        String contrasena = teclado.nextLine();
        LOGGER.info("Ingrese su email:");
        String email = teclado.nextLine();

        Usuario nuevoUsuario = new Usuario(0, nombreUsuario, contrasena, email, email, false);
        if (gestorUsuarios.registrarUsuario(nuevoUsuario)) {
            LOGGER.info("Usuario registrado exitosamente. Por favor, inicie sesión.");
        } else {
            LOGGER.warning("Error al registrar el usuario. Por favor, intente de nuevo.");
        }
    }

    /**
     * La función `realizarPrestamo` en Java permite a un administrador crear un
     * nuevo préstamo de un libro a un usuario,
     * manejando la información del usuario y del libro, creando un registro de
     * préstamo, actualizando la disponibilidad del libro y
     * registrando la transacción.
     * 
     * @param teclado    `teclado` es un objeto `Scanner` utilizado para leer la
     *                   entrada del usuario.
     * @param biblioteca El método `realizarPrestamo` toma dos parámetros:
     */
    private static void realizarPrestamo(Scanner teclado, LinkedList<Libro> biblioteca) throws SQLException {
        if (usuarioActual == null || !usuarioActual.esAdministrador()) {
            LOGGER.warning("Solo los administradores pueden realizar préstamos.");
            return;
        }

        LOGGER.info("Ingrese el documento del usuario:");
        String documento = teclado.nextLine();

        Usuario usuario = ConexionBD.buscarUsuarioPorDocumento(documento);
        if (usuario == null) {
            LOGGER.info("Usuario no encontrado. Por favor, registre al nuevo usuario.");
            LOGGER.info("Ingrese el nombre del usuario:");
            String nombreUsuario = teclado.nextLine();
            LOGGER.info("Ingrese el email del usuario:");
            String email = teclado.nextLine();

            usuario = new Usuario(0, nombreUsuario, "", email, documento, false);
            if (!ConexionBD.crearUsuario(usuario)) {
                LOGGER.warning("No se pudo crear el usuario. Abortando el préstamo.");
                return;
            }
            LOGGER.info("Nuevo usuario registrado exitosamente.");
        } else {
            LOGGER.info("Usuario encontrado: " + usuario.getNombreUsuario());
        }

        LOGGER.info("Ingrese el ID del libro a prestar:");
        int idLibro = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        Libro libro = ConexionBD.leerLibro(idLibro);
        if (libro == null) {
            LOGGER.warning("No se encontró un libro con el ID proporcionado.");
            return;
        }

        if (!libro.isDisponible()) {
            LOGGER.warning("El libro seleccionado no está disponible para préstamo.");
            return;
        }

        Prestamo prestamo = new Prestamo(
                0, // El ID se generará automáticamente en la base de datos
                usuario.getNombreUsuario(),
                usuario.getDocumento(),
                libro.getId(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                new Date(System.currentTimeMillis()) // Fecha actual
        );

        ConexionBD.crearPrestamo(prestamo);
        ConexionBD.actualizarDisponibilidadLibro(libro.getId(), false);

        // Actualizar la disponibilidad del libro en la lista y en el archivo
        for (Libro l : biblioteca) {
            if (l.getId() == libro.getId()) {
                l.setDisponible(false);
                break;
            }
        }
        guardarLibros(biblioteca);

        LOGGER.info("Préstamo realizado con éxito. ID del préstamo: " + prestamo.getId());
        LOGGER.info("El libro ha sido marcado como no disponible en la base de datos y en el archivo.");
    }

    /**
     * Esta función Java `devolverLibro` maneja el proceso de devolución de un
     * libro, incluyendo la validación de entrada,
     * búsqueda de un préstamo activo, registro de la devolución y actualización de
     * la disponibilidad del libro en un sistema de biblioteca.
     * 
     * @param biblioteca LinkedList<Libro> biblioteca: Una lista enlazada que
     *                   contiene objetos de la clase Libro, que probablemente
     *                   representa la colección de libros de una biblioteca.
     * @param teclado    El parámetro `teclado` en el método `devolverLibro` es un
     *                   objeto `Scanner` utilizado para leer la entrada del
     *                   usuario. Se pasa como argumento al método para permitir que
     *                   el usuario ingrese la información necesaria, como el nombre
     *                   del usuario, documento e ID del libro.
     */
    private static void devolverLibro(LinkedList<Libro> biblioteca, Scanner teclado) {
        try {
            LOGGER.info("Ingrese el nombre del usuario:");
            String nombreUsuario = teclado.nextLine();
    
            LOGGER.info("Ingrese el documento del usuario:");
            String documento = teclado.nextLine();
    
            LOGGER.info("Ingrese el ID del libro a devolver:");
            int idLibro = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
    
            // Buscar el préstamo correspondiente
            Prestamo prestamo = ConexionBD.buscarPrestamoActivo(nombreUsuario, documento, idLibro);
    
            if (prestamo != null) {
                // Registrar la devolución
                ConexionBD.registrarDevolucion(prestamo.getId());
                boolean devolucionRegistrada = ConexionBD.registrarDevolucion(prestamo.getId());
                
                if (devolucionRegistrada) {
                    // Actualizar la disponibilidad del libro
                    ConexionBD.actualizarDisponibilidadLibro(idLibro, true);
    
                    // Eliminar el registro de préstamo
                    ConexionBD.eliminarPrestamo(prestamo.getId());
    
                    // Actualizar la disponibilidad del libro en la lista local
                    for (Libro libro : biblioteca) {
                        if (libro.getId() == idLibro) {
                            libro.setDisponible(true);
                            break;
                        }
                    }
    
                    guardarLibros(biblioteca);
                    LOGGER.info("Libro devuelto exitosamente. Se ha actualizado su disponibilidad y registrado la devolución.");
                } else {
                    LOGGER.warning("No se pudo registrar la devolución en la base de datos.");
                }
            } else {
                LOGGER.warning("No se encontró un préstamo activo para el libro especificado.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al procesar la devolución del libro", e);
        }
    }

    /**
     * La función `mostrarMenuPrincipal` muestra un menú con opciones relacionadas
     * con la gestión de un sistema de
     * biblioteca, como mostrar, agregar, editar y eliminar libros, cambiar el
     * estado de un libro y realizar préstamos
     * si el usuario es un administrador.
     * 
     * @param teclado    El parámetro `teclado` en el método `mostrarMenuPrincipal`
     *                   es de tipo `Scanner`. Este parámetro
     *                   se utiliza para leer la entrada del usuario en la consola.
     *                   Se usa para capturar la elección del usuario para
     *                   las opciones del menú mostradas y pasarla a la declaración
     *                   switch para un procesamiento posterior.
     * @param biblioteca El parámetro `biblioteca` en el método
     *                   `mostrarMenuPrincipal` parece ser una
     *                   `LinkedList` de objetos `Libro`. En este contexto,
     *                   probablemente representa una biblioteca o colección de
     *                   libros.
     *                   El método parece ser un menú para interactuar con esta
     *                   colección, permitiendo opciones como mostrar libros.
     * @throws SQLException
     */
    private static void mostrarMenuPrincipal(Scanner teclado, LinkedList<Libro> biblioteca) throws SQLException {
        LOGGER.info("1. Mostrar libros");
        LOGGER.info("2. Agregar libro");
        LOGGER.info("3. Eliminar libro");
        LOGGER.info("4. Editar libro");
        LOGGER.info("5. Cambiar estado de libro");
        LOGGER.info("6. Cerrar sesión");
        if (usuarioActual.esAdministrador()) {
            LOGGER.info("7. Realizar préstamo");
        }
        LOGGER.info("8. Buscar libros");
        LOGGER.info("9. Devolver Libro");
        LOGGER.info("Seleccione una opción:");

        int opcion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        switch (opcion) {
            case 1:
                mostrarLibros(biblioteca);
                break;
            case 2:
                try {
                    agregarLibro(biblioteca, teclado);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al agregar libro", e);
                }
                break;
            case 3:
                try {
                    eliminarLibro(biblioteca, teclado);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al eliminar libro", e);
                }
                break;
            case 4:
                try {
                    editarLibro(biblioteca, teclado);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al editar libro", e);
                }
                break;
            case 5:
                cambiarEstado(biblioteca, teclado);
                break;
            case 6:
                usuarioActual = null;
                LOGGER.info("Sesión cerrada.");
                break;
            case 7:
                if (usuarioActual.esAdministrador()) {
                    try {
                        realizarPrestamo(teclado, biblioteca);
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error al realizar el préstamo", e);
                    }
                } else {
                    LOGGER.warning("Opción no válida. Por favor, intente de nuevo.");
                }
                break;
            case 8:
                LOGGER.info("Ingrese el título o ISBN del libro a buscar:");
                String criterio = teclado.nextLine();
                List<Libro> resultados = ConexionBD.buscarLibros(criterio);
                if (resultados.isEmpty()) {
                    LOGGER.info("No se encontraron libros que coincidan con el criterio de búsqueda.");
                } else {
                    for (Libro libro : resultados) {
                        LOGGER.info(libro.getId() + " | " + libro.getTitulo() + " | " + libro.getIsbn()
                                + " | Disponible: " + libro.isDisponible());
                    }
                }
                break;
            case 9:
                devolverLibro(biblioteca, teclado);
                break;
            default:
                LOGGER.warning("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    /**
     * El código Java anterior es un método principal que establece una conexión con
     * una base de datos MySQL
     * llamada "biblioteca" que se ejecuta en localhost. Crea un objeto
     * `GestorUsuarios` para gestionar usuarios,
     * carga una lista de libros en un `LinkedList`, y luego entra en un bucle donde
     * muestra un menú de
     * inicio de sesión/registro si no hay un usuario actualmente conectado, o un
     * menú principal si un
     * usuario está autenticado. toma las `SQLExceptions` relacionadas con las
     * operaciones de base de datos,
     * registra los errores utilizando un `LOGGER`, y cierra la conexión con la base
     * de datos en un bloque
     * `finally` para garantizar la gestión adecuada de los recursos.
     */
    public static void main(String[] args) throws Exception {

        try {
            // Establecer conexión con la base de datos
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
            gestorUsuarios = new GestorUsuarios(conexion);

            Scanner teclado = new Scanner(System.in);
            LinkedList<Libro> biblioteca = new LinkedList<>();
            cargarLibros(biblioteca);

            while (true) {
                if (usuarioActual == null) {
                    mostrarMenuInicial(teclado); // Mostrar menú de login/registro
                } else {
                    mostrarMenuPrincipal(teclado, biblioteca); // Mostrar menú principal solo si el usuario está
                                                               // autenticado
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error al cerrar la conexión", e);
                }
            }
        }
    }

}
