import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

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

    public static void crearLibro(Libro libro) throws SQLException {
        String query = "INSERT INTO libros (titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getfechaPublicacion());
            pstmt.setInt(4, libro.getNumPaginas());
            pstmt.setBoolean(5, libro.isDisponible());
            pstmt.setString(6, libro.getIsbn());
            pstmt.setString(7, libro.getDescripcion());
            pstmt.executeUpdate();
        }
    }

    public static Libro leerLibro(String isbn) throws SQLException {
        String query = "SELECT * FROM libros WHERE isbn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("fechaPublicacion"),
                        rs.getInt("numPaginas"),
                        rs.getBoolean("disponible"),
                        rs.getString("isbn"),
                        rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }

    public static void actualizarLibro(String isbn, Libro libro) throws SQLException {
        String query = "UPDATE libros SET titulo = ?, autor = ?, fechaPublicacion = ?, numPaginas = ?, disponible = ?, descripcion = ? WHERE isbn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getfechaPublicacion());
            pstmt.setInt(4, libro.getNumPaginas());
            pstmt.setBoolean(5, libro.isDisponible());
            pstmt.setString(6, libro.getDescripcion());
            pstmt.setString(7, isbn);
            pstmt.executeUpdate();
        }
    }

    public static void eliminarLibro(String isbn) throws SQLException {
        String query = "DELETE FROM libros WHERE isbn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        }
    }
}