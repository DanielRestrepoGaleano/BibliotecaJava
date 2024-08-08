

//PARA CAMBIOS EN EL CODIGO
//1. GIT ADD <REPOSITORIO>
//2. GIT COMMIT CON LOS CAMBIOS
//3. GIT PUSH origin master
//Secuencia total: git add <Biblioteca.java o Libro.java> ; git commit -m <especificar cambio> ; git push origin master.

import java.util.Scanner;

public class Biblioteca {
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

    private static int borrarLibroDefinitivo(Libro[] biblioteca, int contadorLibros, Scanner scanner) {
        if (contadorLibros <= 0) {
            System.out.println("No hay libros disponibles");
            return contadorLibros;
        } else {
            System.out.println("Seleccione la posición del libro a eliminar:");
            int posicionEliminar = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicionEliminar > 0 && posicionEliminar <= contadorLibros) {
                for (int i = posicionEliminar - 1; i < contadorLibros - 1; i++) {
                    biblioteca[i] = biblioteca[i + 1];
                }
                biblioteca[contadorLibros - 1] = null;
                contadorLibros--;
            } else {
                System.out.println("Posición inválida, intente nuevamente.");
            }
            return contadorLibros;
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int contadorLibros = 0;
        Libro[] biblioteca = new Libro[100];

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
                            System.out.println("Ingresar un nuevo libro");

                            System.out.println("Ingrese el título del libro:");
                            String titulo = teclado.nextLine().toLowerCase();

                            System.out.println("Ingrese el autor del libro:");
                            String autor = teclado.nextLine().toLowerCase();

                            System.out.println("Ingrese el año de publicación:");
                            int fechaPublicacion = teclado.nextInt();
                            teclado.nextLine(); // Consumir el salto de línea

                            if (fechaPublicacion > 2024) {
                                while (fechaPublicacion > 2024) {
                                    System.out.println("La fecha " + fechaPublicacion + " es superior al año actual, vuelva a intentarlo.");
                                    fechaPublicacion = teclado.nextInt();
                                    teclado.nextLine(); // Consumir el salto de línea
                                }
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
                            biblioteca[contadorLibros] = nuevoLibro;
                            contadorLibros++;
                            break;

                        case 2:
                            mostrarLibros(biblioteca, contadorLibros);
                            break;

                        case 3:
                            posicionLibro(biblioteca, contadorLibros);
                            break;

                        case 4:
                            posicionLibro(biblioteca, contadorLibros);
                            System.out.println("Seleccione el número del libro para cambiar su estado (ocultar/mostrar):");
                            int numLibro = teclado.nextInt();
                            teclado.nextLine(); // Consumir el salto de línea
                            if (numLibro > 0 && numLibro <= contadorLibros) {
                                biblioteca[numLibro - 1].cambiarDisponibilidad();
                            } else {
                                System.out.println("Número de libro inválido.");
                            }
                            break;

                        case 5:
                            posicionLibro(biblioteca, contadorLibros);
                            System.out.println("Seleccione el número del libro para editar:");
                            int numEditar = teclado.nextInt();
                            teclado.nextLine(); // Consumir el salto de línea
                            if (numEditar > 0 && numEditar <= contadorLibros) {
                                biblioteca[numEditar - 1].editarLibro(teclado);
                            } else {
                                System.out.println("Número de libro inválido.");
                            }
                            break;

                        case 6:
                            contadorLibros = borrarLibroDefinitivo(biblioteca, contadorLibros, teclado);
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
