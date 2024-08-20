

//PARA CAMBIOS EN EL CODIGO
//1. GIT ADD <REPOSITORIO>
//2. GIT COMMIT CON LOS CAMBIOS
//3. GIT PUSH origin master
//Secuencia total: git add <Biblioteca.java o Libro.java> ; git commit -m <especificar cambio> ; git push origin master.

import java.util.Scanner;
import java.io.*;

public class Biblioteca {
    private static final String ARCHIVO_LIBROS = "libros.txt"; // CONSTANTE PARA GENERAR EL ARCHIVO .TXT
// FUNCIÓN PARA MOSTRAR LOS LIBROS
    private static void mostrarLibros(Libro[] biblioteca, int contadorLibros) {
        System.out.println("--- LIBROS EN LA BIBLIOTECA ---");
        boolean hayLibrosDisponibles = false;
        for (int i = 0; i < contadorLibros; i++) {
            if (biblioteca[i] != null && biblioteca[i].isDisponible()) {
                hayLibrosDisponibles = true;
                break;
            }
        }

        if (!hayLibrosDisponibles) {
            System.out.println("No hay libros disponibles");
        } else {
            for (int i = 0; i < contadorLibros; i++) {
                if (biblioteca[i] != null && biblioteca[i].isDisponible()) {
                    System.out.println("");
                    System.out.println(biblioteca[i].toString());
                }
            }
        }
    }
//FUNCION PARA LA POSICIÓN DE CADA LIBRO
    private static void posicionLibro(Libro[] biblioteca, int contadorLibros) {
        if (contadorLibros <= 0) {
            System.out.println("La biblioteca no tiene ningún libro.");
        } else {
            for (int l = 0; l < contadorLibros; l++) {
                if (biblioteca[l] != null) {
                    int posicion = l + 1;
                    System.out.print("[" + posicion + "] ");
                    System.out.println(biblioteca[l].getTitulo());
                    if (!biblioteca[l].isDisponible()) {
                        System.out.println("(EL LIBRO " + biblioteca[l].getTitulo() + " SE ENCUENTRA OCULTO)");
                    }
                } else {
                    System.out.println("No hay libros disponibles");
                }
            }
        }
    }
//FUNCIÓN PARA BORRAR UN LIBRO SEGÚN SU POSICIÓN DE FORMA DEFINITIVA
    private static void borrarLibroDefinitivo(Libro[] biblioteca, int[] contadorLibros, Scanner scanner) {
        if (contadorLibros[0] <= 0) {
            System.out.println("No hay libros disponibles");
        } else {
            System.out.println("Seleccione la posición del libro a eliminar:");
            int posicionEliminar = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicionEliminar > 0 && posicionEliminar <= contadorLibros[0]) {
                for (int i = posicionEliminar - 1; i < contadorLibros[0] - 1; i++) {
                    biblioteca[i] = biblioteca[i + 1];
                }
                biblioteca[contadorLibros[0] - 1] = null;
                contadorLibros[0]--;
            } else {
                System.out.println("Posición inválida, intente nuevamente.");
            }
            guardarLbros(biblioteca, contadorLibros[0]);
        }
    }

    // FUNCION PARA GUARDAR LIBROS COMO .TXT
    private static void guardarLbros(Libro[] biblioteca, int contadorLibros) {
        try (PrintWriter escritor = new PrintWriter(ARCHIVO_LIBROS)) {
            for (int i = 0; i < contadorLibros; i++) {
                if (biblioteca[i] != null) {
                    escritor.println(biblioteca[i].aTexto());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al guardar los libros");
        }
    }

    // FUNCION PARA CARGAR LOS LIBROS CUANDO SE CIERRE EL IDE
    private static void cargarLibros(Libro[] biblioteca, int[] contadorLibros) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_LIBROS))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                biblioteca[contadorLibros[0]] = Libro.aLibro(linea);
                contadorLibros[0]++;
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los libros");
        }
    }

    // FUNCION PARA AGREGAR UN LIBRO NUEVO
    private static void agregarLibro(Libro[] biblioteca, int[] contadorLibros, Scanner teclado) {
        System.out.println("Ingresar un nuevo libro");

        System.out.println("Ingrese el título del libro:");
        String titulo = teclado.nextLine().toLowerCase();

        System.out.println("Ingrese el autor del libro:");
        String autor = teclado.nextLine().toLowerCase();

        System.out.println("Ingrese el año de publicación:");
        int fechaPublicacion = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        while (fechaPublicacion > 2024) {
            System.out.println("La fecha " + fechaPublicacion + " es superior al año actual, vuelva a intentarlo.");
            fechaPublicacion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
        }

        System.out.println("Ingrese el número de páginas:");
        int numPaginas = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea

        System.out.println("¿El libro está disponible? (true/false):");
        boolean disponible = teclado.nextBoolean();
        teclado.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese el ISBN del libro:");
        String isbn = teclado.nextLine();

        System.out.println("Agregue una descripción del libro:");
        String descripcion = teclado.nextLine();

        Libro nuevoLibro = new Libro(titulo, autor, fechaPublicacion, numPaginas, disponible, isbn, descripcion);
        biblioteca[contadorLibros[0]] = nuevoLibro;
        contadorLibros[0]++;

        guardarLbros(biblioteca, contadorLibros[0]);
    }

    // FUNCIÓN PARA EDITAR LIBROS EN FUNCIÓN DE SU POSICIÓN
    @SuppressWarnings("unused")
    private static void editarLibro(Libro[] biblioteca, int contadorLibros, Scanner teclado) {
        if (contadorLibros >= 1) {
            posicionLibro(biblioteca, contadorLibros);
            System.out.println("Ingrese la posición del libro para editar");
            int posicion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea
            if (posicion > 0 && posicion <= contadorLibros) {
                biblioteca[posicion - 1].editarLibro(teclado);
                guardarLbros(biblioteca, contadorLibros);    
            } else {
                System.out.println("Posición no encontrada");
            }
            
        }
    }
//FUNCIÓN PARA CAMBIAR LA DISPONIBILIDAD DEL LIBRO SEGÚN SU POSICIÓN
    private static void cambiarEstado(Libro[] biblioteca, int contadorLibros, Scanner teclado) {
        posicionLibro(biblioteca, contadorLibros);
        System.out.println("Seleccione el número del libro para cambiar su estado (ocultar/mostrar):");
        int numLibro = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea
        if (numLibro > 0 && numLibro <= contadorLibros) {
            biblioteca[numLibro - 1].cambiarDisponibilidad();
        } else {
            System.out.println("Número de libro inválido.");
        }

        guardarLbros(biblioteca, contadorLibros);  // Guardar después de cambiar el estado
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int[] contadorLibros = {0};
        Libro[] biblioteca = new Libro[100];

        cargarLibros(biblioteca, contadorLibros);

        int x = 1;

        do {
            System.out.println("Por favor presione 1 para ingresar un nuevo libro");
            System.out.println("Por favor presione 2 para ver la lista de libros");
            System.out.println("Por favor presione 3 para ver la posición de los libros");
            System.out.println("Por favor presione 4 para cambiar el estado del libro");
            System.out.println("Por favor presione 5 para editar un libro");
            System.out.println("Por favor presione 6 para borrar de forma definitiva un libro de la lista");
            System.out.println("Por favor presione 0 para salir");
            System.out.println("Ingrese su opción");

            String input = teclado.nextLine();

            try {
                x = Integer.parseInt(input);
                if (x != 0) {
                    switch (x) {
                        case 1:
                            agregarLibro(biblioteca, contadorLibros, teclado);
                            break;

                        case 2:
                            mostrarLibros(biblioteca, contadorLibros[0]);
                            break;

                        case 3:
                            posicionLibro(biblioteca, contadorLibros[0]);
                            break;

                        case 4:
                            cambiarEstado(biblioteca, contadorLibros[0], teclado);
                            break;

                        case 5:
                            if (contadorLibros[0] >= 1) {
                                posicionLibro(biblioteca, contadorLibros[0]);
                                System.out.println("Seleccione el número del libro para editar:");
                                int numEditar = teclado.nextInt();
                                teclado.nextLine(); // Consumir el salto de línea
                                if (numEditar > 0 && numEditar <= contadorLibros[0]) {
                                    biblioteca[numEditar - 1].editarLibro(teclado);
                                    guardarLbros(biblioteca, contadorLibros[0]);
                                } else {
                                    System.out.println("Número de libro inválido.");
                                }
                            } else {
                                System.out.println("No hay libros en la biblioteca.");
                            }
                            break;

                        case 6:
                            borrarLibroDefinitivo(biblioteca, contadorLibros, teclado);
                            break;

                        default:
                            System.out.println("Opción inválida, intente nuevamente.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        } while (x != 0);
    }
}