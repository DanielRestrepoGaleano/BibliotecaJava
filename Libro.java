import java.util.Scanner;
import java.io.Serializable;

@SuppressWarnings("unused")
public class Libro {
    private String titulo;
    private String autor;
    private int fechaPublicacion;
    private int numPaginas;
    private boolean disponible;
    private String isbn;
    private String descripcion;
    
    // Constructor
    public Libro(String titulo, String autor, int fechaPublicacion, int numPaginas, boolean disponible, String isbn, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.numPaginas = numPaginas;
        this.disponible = disponible;
        this.isbn = isbn;
        this.descripcion = descripcion;
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

    //FUNCIÓN PARA CONVERTIR UN LIBRO EN UN TEXTO

    public String aTexto(){
        return  titulo  + autor +  fechaPublicacion  + numPaginas +  disponible + isbn  + descripcion ;
    }

    

    //FUNCIÓN PARA CREAR UN LIBRO A PARTIR DE UNA CADENA DE TEXTO
    public static Libro aLibro(String texto) {
        String[] partes = texto.split(",");
        if (partes.length >= 7) {
            return new Libro(partes[0], partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]), Boolean.parseBoolean(partes[4]), partes[5], partes[6]);
        } else {
            // Handle the case where the input string is malformed
            System.out.println("Ha ocurrido un error (INPUT STRING), por favor borre el archivo .txt y vuelva a intentar");
            return null; 
        }
    }

    // Método para editar el libro
    public void editarLibro(Scanner scanner) {
        System.out.println("Información actual del libro:");
        System.out.println(this.toString());

        System.out.println("Ingrese el nuevo título del libro:");
        String nuevoTitulo = scanner.nextLine();
        this.setTitulo(nuevoTitulo);

        System.out.println("Ingrese el nuevo autor del libro:");
        String nuevoAutor = scanner.nextLine();
        this.setAutor(nuevoAutor);

        System.out.println("Ingrese el nuevo año de publicación:");
        int nuevaFechaPublicacion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        this.setfechaPublicacion(nuevaFechaPublicacion);

        System.out.println("Ingrese el nuevo número de páginas:");
        int nuevasPaginas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        this.setNumPaginas(nuevasPaginas);

        System.out.println("Actualizar estado del libro (true/false):");
        boolean libroDisponible = scanner.nextBoolean();
        scanner.nextLine(); // Consumir el salto de línea
        this.setDisponible(libroDisponible);

        System.out.println("Ingrese el nuevo ISBN del libro:");
        String nuevoIsbn = scanner.nextLine();
        this.setIsbn(nuevoIsbn);

        System.out.println("Ingrese una nueva descripción del libro:");
        String nuevaDescripcion = scanner.nextLine();
        this.setDescripcion(nuevaDescripcion);

        System.out.println("El libro ha sido editado exitosamente.");
    }

    // Método para ocultar/mostrar el libro
    public void cambiarDisponibilidad() {
        if (this.disponible) {
            this.setDisponible(false);
            System.out.println("Libro ocultado exitosamente.");
        } else {
            this.setDisponible(true);
            System.out.println("Libro mostrado exitosamente.");
        }
    }
    
    @Override
    public String toString() {
        return "Libro [titulo=" + titulo + ", autor=" + autor + ", fechaPublicacion=" + fechaPublicacion + 
               ", numPaginas=" + numPaginas + ", disponible=" + disponible + ", isbn=" + isbn + 
               ", descripcion=" + descripcion + "]";
    }
}
