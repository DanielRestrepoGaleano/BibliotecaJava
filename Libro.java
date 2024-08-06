public class Libro{


    private String titulo;
    private String autor;
    private int fechaPublicacion;
    private int numPaginas;
    private boolean disponible;
    private String isbn;
    private String descripcion;
    
    //Constructor
    
    
    public Libro(String titulo, String autor, int fechaPublicacion, int numPaginas, boolean disponible, String isbn, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.numPaginas = numPaginas;
        this.disponible = disponible;
        this.isbn = isbn;
        this.descripcion = descripcion;
    
    
    } //Métodos getter y setter.
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
    @Override
    public String toString() {
        return "Libro [titulo=" + titulo + ", autor=" + autor + ", fechaPublicacion=" + fechaPublicacion + ", numPaginas="
                + numPaginas + ", disponible=" + disponible + ", isbn=" + isbn + " descripcion:"  +  descripcion  + "]";
    }
    
    }
    
    