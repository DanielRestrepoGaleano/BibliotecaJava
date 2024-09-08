import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConectarPHP {

    public static void enviarLibro(Libro libro) throws Exception {
        URL url = new URL("http://localhost/ORIENTADOAOBJETOS/Libros.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");  // Asegurando que el método es POST
        conn.setDoOutput(true);         // Habilitar el envío de datos

        // Asegúrate de establecer el tipo de contenido
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Parámetros codificados
        String parametros = String.format("titulo=%s&autor=%s&fechaPublicacion=%d&numPaginas=%d&disponible=%d&isbn=%s&descripcion=%s",
                URLEncoder.encode(libro.getTitulo(), StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(libro.getAutor(), StandardCharsets.UTF_8.toString()),
                libro.getfechaPublicacion(),
                libro.getNumPaginas(),
                libro.isDisponible() ? 1 : 0,
                URLEncoder.encode(libro.getIsbn(), StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(libro.getDescripcion(), StandardCharsets.UTF_8.toString()));

        // Para depurar, imprime los parámetros
        System.out.println("Parámetros enviados: " + parametros);

        // Enviar los parámetros al servidor
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Leer la respuesta del servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Respuesta del servidor: " + response.toString());
        }

        // Desconectar la conexión
        conn.disconnect();
    }
}