package ORIENTADOAOBJETOS;

/**
 * La clase "Prestamo" representa un préstamo con propiedades como id, usuario, documento, libro, fecha de préstamo,
 * junto con getters, setters y un método toString.
 */
public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String email;
    private boolean esAdministrador;
    
    // Este parte del código es el constructor de la clase `Prestamo` en Java. Es un método especial que
    // se llama cuando se crea una nueva instancia de la clase.
    public Usuario(int id, String nombreUsuario, String contrasena, String email, boolean esAdministrador) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.esAdministrador = esAdministrador;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean esAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

  /**
     * El método `toString` en Java sobrescribe la implementación predeterminada para devolver una representación de cadena
     * formateada de los atributos del objeto `Prestamo`.
     * 
     * @return El método `toString()` se sobrescribe para devolver una representación de cadena del objeto `Prestamo`,
     * que incluye los valores de sus atributos como id, usuario, documento, libro y fecha de préstamo.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", esAdministrador=" + esAdministrador +
                '}';
    }
}