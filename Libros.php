<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "biblioteca";

// Asegurarse de que el método de la solicitud sea POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    die(json_encode(["error" => "Método HTTP no soportado. Se esperaba POST."]));
}

// Generar la conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar la conexión
if ($conn->connect_error) {
    die(json_encode(["error" => "Conexión fallida: " . $conn->connect_error]));
}

// Depuración: Imprimir todos los datos POST recibidos
echo "Datos POST recibidos:\n";
print_r($_POST);

// Obtener datos desde la solicitud POST con validación
$titulo = isset($_POST['titulo']) ? $conn->real_escape_string($_POST['titulo']) : '';
$autor = isset($_POST['autor']) ? $conn->real_escape_string($_POST['autor']) : '';
$fechaPublicacion = isset($_POST['fechaPublicacion']) ? intval($_POST['fechaPublicacion']) : 0;
$numPaginas = isset($_POST['numPaginas']) ? intval($_POST['numPaginas']) : 0;
$disponible = isset($_POST['disponible']) ? intval($_POST['disponible']) : 0;
$isbn = isset($_POST['isbn']) ? $conn->real_escape_string($_POST['isbn']) : '';
$descripcion = isset($_POST['descripcion']) ? $conn->real_escape_string($_POST['descripcion']) : '';

// Depuración: Imprimir los datos procesados
echo "Datos procesados:\n";
echo "Título: $titulo\n";
echo "Autor: $autor\n";
echo "Fecha de publicación: $fechaPublicacion\n";
echo "Número de páginas: $numPaginas\n";
echo "Disponible: $disponible\n";
echo "ISBN: $isbn\n";
echo "Descripción: $descripcion\n";

// Preparar y ejecutar la consulta
$stmt = $conn->prepare("INSERT INTO libros (titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)");

if ($stmt === false) {
    die(json_encode(["error" => "Error en la preparación de la consulta: " . $conn->error]));
}

$stmt->bind_param("ssiiiis", $titulo, $autor, $fechaPublicacion, $numPaginas, $disponible, $isbn, $descripcion);

if ($stmt->execute()) {
    echo json_encode(["success" => "Nuevo libro agregado correctamente"]);
} else {
    echo json_encode(["error" => "Error al agregar libro: " . $stmt->error]);
}

$stmt->close();
$conn->close();
?>
