import java.util.Scanner;
import java.io.Serializable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogRecord;

@SuppressWarnings("unused")
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int fechaPublicacion;
    private int numPaginas;
    private boolean disponible;
    private String isbn;
    private String descripcion;
    
    // Constructor
    public Libro(int id, String titulo, String autor, int fechaPublicacion, int numPaginas, boolean disponible, String isbn, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.numPaginas = numPaginas;
        this.disponible = disponible;
        this.isbn = isbn;
        this.descripcion = descripcion;
    }
    private static final Logger LOGGER = Logger.getLogger(Libro.class.getName());
    
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
    private static void configurarLogger() {
        Logger rootLogger = Logger.getLogger("");
        ConsoleHandler handler = new ConsoleHandler();
        
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + ": " + record.getMessage() + "\n";
            }
        });

        // Eliminar otros handlers y agregar el personalizado
        rootLogger.setUseParentHandlers(false);
        rootLogger.addHandler(handler);
    }
    
    // Métodos getter y setter
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public int getfechaPublicacion() {
        return fechaPublicacion;
    }
    public void setfechaPublicacion(int fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public int getNumPaginas() {
        return numPaginas;
    }
    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }
    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    //FUNCIÓN PARA CONVERTIR UN LIBRO EN UN TEXTO

    public String aTexto() {
        return id + "," + titulo + "," + autor + "," + fechaPublicacion + "," + numPaginas + "," + disponible + "," + isbn + "," + descripcion;
    }

    

    //FUNCIÓN PARA CREAR UN LIBRO A PARTIR DE UNA CADENA DE TEXTO
    public static Libro aLibro(String texto) {
        String[] partes = texto.split(",");
        if (partes.length >= 8) {
            return new Libro(Integer.parseInt(partes[0]), partes[1], partes[2], Integer.parseInt(partes[3]), 
                             Integer.parseInt(partes[4]), Boolean.parseBoolean(partes[5]), partes[6], partes[7]);
        } else {
            LOGGER.info("Ha ocurrido un error (INPUT STRING), por favor borre el archivo .txt y vuelva a intentar");
            return null; 
        }
    }

    // Método para editar el libro
    public void editarLibro(Scanner scanner) {
        LOGGER.info("Información actual del libro:");
        LOGGER.info(this.toString());

        LOGGER.info("Ingrese el nuevo título del libro:");
        String nuevoTitulo = scanner.nextLine();
        this.setTitulo(nuevoTitulo);

        LOGGER.info("Ingrese el nuevo autor del libro:");
        String nuevoAutor = scanner.nextLine();
        this.setAutor(nuevoAutor);

        LOGGER.info("Ingrese el nuevo año de publicación:");
        int nuevaFechaPublicacion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        this.setfechaPublicacion(nuevaFechaPublicacion);

        LOGGER.info("Ingrese el nuevo número de páginas:");
        int nuevasPaginas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        this.setNumPaginas(nuevasPaginas);

        LOGGER.info("Actualizar estado del libro (true/false):");
        boolean libroDisponible = scanner.nextBoolean();
        scanner.nextLine(); // Consumir el salto de línea
        this.setDisponible(libroDisponible);

        LOGGER.info("Ingrese el nuevo ISBN del libro:");
        String nuevoIsbn = scanner.nextLine();
        this.setIsbn(nuevoIsbn);

        LOGGER.info("Ingrese una nueva descripción del libro:");
        String nuevaDescripcion = scanner.nextLine();
        this.setDescripcion(nuevaDescripcion);

        LOGGER.info("El libro ha sido editado exitosamente.");
    }

    // Método para ocultar/mostrar el libro
    public void cambiarDisponibilidad() {
        if (this.disponible) {
            this.setDisponible(false);
            LOGGER.info("Libro ocultado exitosamente.");
        } else {
            this.setDisponible(true);
            LOGGER.info("Libro mostrado exitosamente.");
        }
    }
    
    @Override
    public String toString() {
        return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", fechaPublicacion=" + fechaPublicacion + 
               ", numPaginas=" + numPaginas + ", disponible=" + disponible + ", isbn=" + isbn + 
               ", descripcion=" + descripcion + "]";
    }
}
