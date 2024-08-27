
**PLANEACIÓN**
- Conectar el proyecto a SpringBoot &cross;
- Conectar el proyecto a una base de datos &cross;
- Crear un diagrama de clases &cross;
-  Cambiar los arrays por listas &check;

________________________________________________________________________________


**DESCRIPCIÓN ACTUAL**
Este repositorio cuenta con un pequeño software para el manejo de una biblioteca, actualmente se cuentra bajo algunas limitaciones, como falta de memoria dinamica. No se encuentra conectado a una base de datos y aun puede contener errores.
Actualmente solo se puede ejecutar desde la terminal. Por lo tanto no es necesario instalar nada
Unicamente deberá copiar y pegar el código en su IDE, deberá generar 2 clases, una con el nombre
"Biblioteca.java" y el otro con el nombre "Libro.java"

_________________________________________________________________________________

**ERRORES ANTERIORES SOLUCIONADOS**
- Error con el Scanner (NoSuchElementException) &check;
- Error con arrays (InputMismatchException) &check;
- Error en el guardado de libros &check;
- Error cuando se muestra un libro en la terminal &check;
- Error con la carga de los archivos .txt  &check;


_________________________________________________________________________________

**ERRORES CONOCIDOS**
- Los libros editados se pueden observar en la terminal, pero no se realiza el cambio en el archivo .txt &cross;
- Los libros suelen cargar mal generando una excepción, se debe elimar el archivo .txt generado para que el prgrama se ejecute con normalidad. &check;
- Error identificado: cuando se edita la fecha de publicación de un libro, se cierra el programa y seguidamente se vuelve a ejecutar como la fecha se cambia en la terminal pero no el txt, generando el siguiente error NumberFormatException.forInputString(NumberFormatException) &check;
      - El método de guardado no funciona correctamente, ya que una vez se cierra el programa
      intenta cargar libros que no existen en la terminal &check;
- Error NoSuchElementException "No line Found" cuando se presiona "ctrl + c" para cancelar ejecución del programa. &cross;
__________________________________________________________________________________
**PROGRESO ACUTAL CON LOS ERRORES**
Actualmente el error con los archivos persiste aunque ya es menos grave.
Se logró que el programa actualice los archivos en el .txt, pero surgió otro error
los libros no se estan cargando de forma correcta lo que provoca una perdida de datos y un inicio en las posiciones de los libros que no es correcta.
(27/08/2024)
Se logró mitigar el error al cargar los libros.


__________________________________________________________________________________

 **CAMBIOS**
__________________________________________________________  
08/08/2024----3:00pm

Cambios en el programa: 
- Se realizaron cambios en la estructura de las funciones
- Se les dio nombres más claros y fueron enviadas a las clases correspondienes para cada función

____________________________________________________________ 
08/08/2024--10:50pm

Siguiente actualización planeada:
- se realizará el guardado de libros a un archivo .txt a modo de BD temporal.

_________________________________________________________________________________

09/08/2024 --- 6:00pm

- Se realizaron cambios importantes en la estructura de las funciones
- Se añadio el guardado de libros por medio de archivos .txt

_____________________________________________________________________________________

27/08/2024 ----> 1:33pm

- Se realizaron cambios importantes en la estructura de las funciones (cargarLibros, guardarLibros y aTexto(de la clase Libro))
- Cambio de arrays a listas ligadas para evitar tener límites de libros