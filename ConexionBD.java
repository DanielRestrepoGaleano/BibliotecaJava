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
                        rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }

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

    public static void eliminarLibro(int id) throws SQLException {
        String query = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

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
}