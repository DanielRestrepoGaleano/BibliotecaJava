
**PLANEACIÓN**
- Conectar el proyecto a SpringBoot &cross;
- Conectar el proyecto a una base de datos &cross; 
- crear interfaz gráfica &cross; :warning:
- Crear un diagrama de clases &cross;
- Cambiar los arrays por listas &check;
- Crear base de datos simulada con archivo .txt &check;


________________________________________________________________________________


**DESCRIPCIÓN ACTUAL**
 Este repositorio contiene un pequeño software para el manejo de una biblioteca. Actualmente, presenta algunas limitaciones, como la falta de memoria dinámica. No está conectado a una base de datos y aún puede contener errores.
 Por ahora, solo se puede ejecutar desde la terminal, por lo que no es necesario instalar nada. Solo deberá copiar y pegar el código en su IDE, generar dos clases:

 `Biblioteca.java`
 `Libro.java`
## NOTA 
- TODO LO QUE CONTENGA :warning: se le dará prioridad y será implementado cuanto antes.

_________________________________________________________________________________

**ERRORES ANTERIORES SOLUCIONADOS**
- Error con el `Scanner` `(NoSuchElementException)` &check;
- Error con arrays `(InputMismatchException)` &check;
- Error en el guardado de libros &check;
- Error cuando se muestra un libro en la terminal &check;
- Error con la carga de los archivos .txt  &check;


_________________________________________________________________________________

**ERRORES CONOCIDOS**
- Los libros editados se pueden observar en la terminal, pero no se realiza el cambio en el archivo .txt &cross;
- Los libros suelen cargar mal generando una excepción, se debe elimar el archivo .txt generado para que el prgrama se ejecute con normalidad. &check;
- Error identificado: cuando se edita la fecha de publicación de un libro, se cierra el programa y seguidamente se vuelve a ejecutar como la fecha se cambia en la terminal pero no el txt, generando el siguiente error `NumberFormatException.forInputString(NumberFormatException)` &check;
- El método de guardado no funciona correctamente, ya que una vez se cierra el programa
      intenta cargar libros que no existen en la terminal &check;

__________________________________________________________________________________
**PROGRESO ACUTAL CON LOS ERRORES**
Actualmente el error con los archivos persiste aunque ya es menos grave.
Se logró que el programa actualice los archivos en el .txt, pero surgió otro error
los libros no se estan cargando de forma correcta lo que provoca una perdida de datos y un inicio en las posiciones de los libros que no es correcta.
(27/08/2024)
Se logró mitigar el error al cargar los libros.
(4/09/2024)
No hay errores con el guardado y cargado de libros actualmente


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

- Se realizaron cambios importantes en la estructura de las funciones (`cargarLibros`, `guardarLibros` y `aTexto`(de la clase `Libro`))
- Cambio de arrays a listas  para evitar tener límites de libros

_____________________________________________________________________________________

4/09/2024 ----> 5:30pm
- se realizó un cambio en todos los `System.out`, fueron cambiados por `LOGGERS` para manter una mayor organización del código

_____________________________________________________________________________________

diagrama de clase


![Descripción de la imagen](../Imágenes/diagramaCBiblioteca.JPG)


