package ORIENTADOAOBJETOS;

import java.sql.*;

/**
 * La clase `ConexionBD` proporciona métodos para interactuar con una base de datos MySQL para realizar
 * operaciones relacionadas con la gestión de libros y préstamos.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

// El método `getConnection()` en la clase `ConexionBD` es un método estático privado que establece
    // una conexión con una base de datos MySQL utilizando la URL, el nombre de usuario y la contraseña
    // proporcionados. Lanza una excepción `SQLException` si hay un problema con la conexión.
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * El método `crearLibro` inserta un nuevo registro de libro en una tabla de base de datos con los
     * detalles del libro proporcionados.
     * 
     * @param libro El método `crearLibro` se utiliza para insertar un nuevo registro en una tabla de
     * base de datos llamada `libros` con la información proporcionada de un libro (`Libro` objeto).
     * Los parámetros requeridos para este método son los siguientes:
     */
    public static void crearLibro(Libro libro) throws SQLException {
        String query = "INSERT INTO libros (id, titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, libro.getId());
            pstmt.setString(2, libro.getTitulo());
            pstmt.setString(3, libro.getAutor());
            pstmt.setInt(4, libro.getfechaPublicacion());
            pstmt.setInt(5, libro.getNumPaginas());
            pstmt.setBoolean(6, libro.isDisponible());
            pstmt.setString(7, libro.getIsbn());
            pstmt.setString(8, libro.getDescripcion());
            pstmt.executeUpdate();
        }
    }

 /**
     * El método `leerLibro` lee un libro de una base de datos según el ID proporcionado.
     * 
     * @param id El método `leerLibro` toma un entero `id` como parámetro. Este `id` se utiliza para
     * consultar la base de datos para un libro específico con el ID coincidente. Si se encuentra un
     * libro con ese ID en la base de datos, el método crea un nuevo objeto `Libro` con los detalles
     * obtenidos de la base de datos.
     * @return El método `leerLibro` devuelve un objeto `Libro` con los detalles obtenidos de la base
     * de datos según el ID proporcionado. Si se encuentra un registro coincidente en la base de datos,
     * se crea un nuevo objeto `Libro` con los datos obtenidos y se devuelve. Si no se encuentra un
     * registro, el método devuelve `null`.
     */
    public static Libro leerLibro(int id) throws SQLException {
        String query = "SELECT * FROM libros WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("fechaPublicacion"),
                            rs.getInt("numPaginas"),
                            rs.getBoolean("disponible"),
                            rs.getString("isbn"),
                            rs.getString("descripcion"));
                }
            }
        }
        return null;
    }

 /**
     * El método `actualizarLibro` actualiza un registro de libro en una tabla de base de datos con la
     * información proporcionada según el ID del libro.
     * 
     * @param id El parámetro `id` en el método `actualizarLibro` es el identificador único del libro
     * que se desea actualizar en la base de datos. Se utiliza en la consulta SQL para especificar qué
     * libro debe actualizarse según su ID.
     * @param libro El parámetro `libro` en el método `actualizarLibro` representa un objeto de la
     * clase `Libro`, que contiene información sobre un libro como su título, autor, fecha de publicación,
     * número de páginas, estado de disponibilidad, ISBN y descripción.
     */
    public static void actualizarLibro(int id, Libro libro) throws SQLException {
        String query = "UPDATE libros SET titulo = ?, autor = ?, fechaPublicacion = ?, numPaginas = ?, disponible = ?, isbn = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getfechaPublicacion());
            pstmt.setInt(4, libro.getNumPaginas());
            pstmt.setBoolean(5, libro.isDisponible());
            pstmt.setString(6, libro.getIsbn());
            pstmt.setString(7, libro.getDescripcion());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        }
    }

 /**
     * El método `eliminarLibro` elimina un libro de una base de datos según su ID.
     * 
     * @param id El parámetro `id` en el método `eliminarLibro` es un valor entero que representa el
     * identificador único del libro que se debe eliminar de la base de datos. Este método es
     * responsable de eliminar un registro de libro de la tabla `libros` en la base de datos según el
     * ID proporcionado.
     */
    public static void eliminarLibro(int id) throws SQLException {
        String query = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

  /**
     * El método `obtenerSiguienteId` obtiene el próximo ID disponible para un libro consultando el ID
     * máximo de la tabla de base de datos e incrementándolo en 1.
     * 
     * @return El método `obtenerSiguienteId` devuelve un valor entero, que es el próximo ID disponible
     * para un nuevo registro de libro en la tabla de base de datos. Si hay registros de libros
     * existentes en la base de datos, recupera el ID máximo de la tabla `libros` y devuelve el próximo
     * ID secuencial incrementándolo en 1. Si no hay registros de libros existentes, devuelve 1 como
     * ID inicial.
     */
    public static int obtenerSiguienteId() throws SQLException {
        String query = "SELECT MAX(id) FROM libros";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1; // Si no hay libros, empezamos desde 1
    }

/**
 * El método `crearPrestamo` inserta un nuevo registro de préstamo en una tabla de base de datos y recupera
 * la clave generada para el registro insertado.
 * 
 * @param prestamo El método `crearPrestamo` se utiliza para insertar un nuevo registro de préstamo en una
 * tabla de base de datos llamada `prestamos`.
 */
    public static void crearPrestamo(Prestamo prestamo) throws SQLException {
        String query = "INSERT INTO prestamos (nombre_usuario, documento, id_libro, isbn_libro, titulo_libro, autor_libro, fecha_prestamo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, prestamo.getNombreUsuario());
            pstmt.setString(2, prestamo.getDocumento());
            pstmt.setInt(3, prestamo.getIdLibro());
            pstmt.setString(4, prestamo.getIsbnLibro());
            pstmt.setString(5, prestamo.getTituloLibro());
            pstmt.setString(6, prestamo.getAutorLibro());
            pstmt.setDate(7, prestamo.getFechaPrestamo());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    prestamo.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

// El método `actualizarDisponibilidadLibro` en la clase `ConexionBD` es responsable de actualizar el
// estado de disponibilidad de un libro en la base de datos según el ID del libro proporcionado (`idLibro`)
// y el nuevo estado de disponibilidad (`disponible`).
    public static void actualizarDisponibilidadLibro(int idLibro, boolean disponible) throws SQLException {
        String query = "UPDATE libros SET disponible = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, disponible);
            pstmt.setInt(2, idLibro);
            pstmt.executeUpdate();
        }
    }

}