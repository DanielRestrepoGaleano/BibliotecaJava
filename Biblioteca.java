

//PARA CAMBIOS EN EL CODIGO
//1. GIT ADD <REPOSITORIO>
//2. GIT COMMIT CON LOS CAMBIOS
//3. GIT PUSH origin master
//Secuencia total: git add <Biblioteca.java o Libro.java> ; git commit -m <especificar cambio> ; git push origin master.

import java.util.Scanner; // importar Scanner para ingreso de datos

public class Biblioteca {
//FUNCIÓN PARA MOSTRAR LIBROS DISPONIBLES EN LA BIBLIOTECA
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
//CONTAR LA POSICIÓN DE CADA LIBRO EN LA BIBLIOTECA (8_08_2024 INCLUSO LIBROS OCULTADOS)
    private static void posicionLibro(Libro[] biblioteca, int contadorLibros) {
        if (contadorLibros <= 0) {
            System.out.println("La biblioteca no tiene ningún libro.");
        } else {
            for (int l = 0; l < contadorLibros; l++) {
                if (biblioteca[l] != null) {
                    int posicion = l + 1;
                    System.out.print("[" + posicion + "] ");
                    System.out.println(biblioteca[l].getTitulo());
                if(biblioteca[l].isDisponible() == false ){
                    System.out.println("(EL LIBRO " + biblioteca[l].getTitulo() + " SE ENCUENTRA OCULTO)");
                }
                } else {
                    System.out.println("No hay libros disponibles");
                }
            }
        }
    }
//FUNCIÓN PARA BORRAR UN LIBRO DE FORMA DEFINITIVA DE LA BIBLIOTECA
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
//FUNCIÓN PARA OCULTAR UN LIBRO DE LA BIBLIOTECA (SIN ELIMINAR)
    private static int eliminarLibro(Libro[] biblioteca, int contadorLibros, Scanner scanner) {
        if (contadorLibros <= 0) {
            System.out.println("No hay libros disponibles");
        } else {
            System.out.println("Seleccione la posición del libro:");
            int posicion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicion > 0 && posicion <= contadorLibros) {
                if (biblioteca[posicion - 1] != null) {
                    if (biblioteca[posicion - 1].isDisponible()) {
                        biblioteca[posicion - 1].setDisponible(false);
                        System.out.println("Libro ocultado exitosamente.");
                    } else {
                        biblioteca[posicion - 1].setDisponible(true);
                        System.out.println("Libro mostrado exitosamente.");
                    }
                }
            } else {
                System.out.println("Posición inválida, intente nuevamente.");
            }
        }
        return contadorLibros;
    }
//FUNCIÓN PARA EDITAR UN LIBRO SEGÚN SU POSICIÓN
    private static int editarLibro(Libro[] biblioteca, int contadorLibros, Scanner scanner) {
        if (contadorLibros <= 0) {
            System.out.println("No hay libros disponibles");
            return contadorLibros;
        } else {
            System.out.println("Ingrese la posición en la que se encuentra el libro:");
            int posicionEditar = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (posicionEditar > 0 && posicionEditar <= contadorLibros) {
                Libro libroAEditar = biblioteca[posicionEditar - 1];

                System.out.println("Información actual del libro:");
                System.out.println(libroAEditar.toString());

                System.out.println("Ingrese el nuevo título del libro:");
                String nuevoTitulo = scanner.nextLine();
                libroAEditar.setTitulo(nuevoTitulo);

                System.out.println("Ingrese el nuevo autor del libro:");
                String nuevoAutor = scanner.nextLine();
                libroAEditar.setAutor(nuevoAutor);

                System.out.println("Ingrese el nuevo año de publicación:");
                int fechaPublicacionNueva = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (fechaPublicacionNueva <= 2025) {
                    libroAEditar.setfechaPublicacion(fechaPublicacionNueva);
                } else {
                    System.out.println("Error! Año incorrecto.");
                }

                System.out.println("Ingrese el nuevo número de páginas:");
                int paginasNuevas = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                libroAEditar.setNumPaginas(paginasNuevas);

                System.out.println("Actualizar estado del libro:");
                boolean libroDisponible = scanner.nextBoolean();
                scanner.nextLine(); // Consumir el salto de línea
                libroAEditar.setDisponible(libroDisponible);

                System.out.println("Ingrese el nuevo ISBN del libro:");
                String isbnNuevo = scanner.nextLine();
                libroAEditar.setIsbn(isbnNuevo);

                System.out.println("Ingrese una nueva descripción del libro:");
                String descripcionNueva = scanner.nextLine();
                libroAEditar.setDescripcion(descripcionNueva);

                System.out.println("El libro ha sido editado exitosamente.");
            } else {
                System.out.println("Posición inválida, intentelo nuevamente.");
            }
        }
        return contadorLibros;
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int contadorLibros = 0;
        Libro[] biblioteca = new Libro[100]; //HASTA 100 POSICIONES PARA LIBROS (SE PLANEA CAMBIAR A UNA LISTA PARA HACERLO DINAMICO)

        int x = 1;
//MENÚ PRINCIPAL DEL PROGRAMA PARA LAS OPCIONES
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

                            System.out.println("");

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

                            System.out.println("");

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
                            eliminarLibro(biblioteca, contadorLibros, teclado);
                            break;
                        case 5:
                            editarLibro(biblioteca, contadorLibros, teclado);
                            break;
                        case 6:
                            contadorLibros = borrarLibroDefinitivo(biblioteca, contadorLibros, teclado);
                            break;
                        default:
                            System.out.println("Número no válido");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Ingrese un número válido.");
            }
        } while (x != 0);
        
       //EN ESTA ZONA SE PLANEA IMPLEMENTAR UN THREAD PARA QUE SE VEA MÁS ORGANIZADO
        teclado.close(); // Cerrar el scanner para evitar ingreso accidental de datos cuando se cierra el programa.
    }
}
