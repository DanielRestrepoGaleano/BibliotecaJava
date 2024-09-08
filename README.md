
**PLANEACIÓN**
- Conectar el proyecto a SpringBoot &cross;
- Conectar el proyecto a una base de datos &check; :warning: 
- crear interfaz gráfica &cross;
- Crear un diagrama de clases &check;
- Cambiar los arrays por listas &check;
- Crear base de datos simulada con archivo .txt &check;


________________________________________________________________________________


**DESCRIPCIÓN ACTUAL**
En este repositorio va a encontrar un software para el manejo de libros, cuenta con memoria dinamica y una conexión "prematura" a una base de datos, tambien le puede hacer seguimiento desde la termina ya que cuenta con una BD simulada en un archivo .txt
Es un proyecto personal para aprender a usar estas tecnologias, sientase libre de modificarlo y usarlo como crea conveniente.
- Daniel Restrepo Galeano

________________________________________________________________________________

**MODO DE USO**

En su IDE deberá generar 3 clases `Biblioteca.java`, `ConectarPHP` y `Libro.java` además de un archivo (API) en `PHP` llamado `Libros.php`.
También hará uso de *XAMPP* deberá activar *Apache* y *MySql*.
Una vez hecho esto (por ahora) deberá crear una base de datos con el nombre biblioteca y su tabla libros.
Si no funciona el programa asegurse de tener la carpeta guardada en la ruta:
*C:// --> xampp/ --> htdocs*, ahí deberá subir la carpeta completa con todos archivos adentro
Una vez realiazado esto no deberá tener problemas con la ejecución de la biblioteca.
El programa aun realiza el archivo .txt con los libros 
- Aun cuenta con limitaciones
- Ante cualquier duda puede notificar por cualquier medio
- se dejarán imagenes guía por si las necesita en la carpeta "GUIA DE IMAGENES"
- Si necesita más imagenes de igual forma puede hacerme llegar la notificación y añadiré pasos más detallados

### NOTA 
- TODO LO QUE CONTENGA :warning: se le dará prioridad y será implementado cuanto antes.

_________________________________________________________________________________

**ERRORES ANTERIORES SOLUCIONADOS**
- Error con el `Scanner` `(NoSuchElementException)` &check;
- Error con arrays `(InputMismatchException)` &check;
- Error en el guardado de libros &check;
- Error cuando se muestra un libro en la terminal &check;
- Error con la carga de los archivos .txt  &check;
- Los libros editados se pueden observar en la terminal, pero no se realiza el cambio en el archivo .txt &check;
- Los libros suelen cargar mal generando una excepción, se debe elimar el archivo .txt generado para que el prgrama se ejecute con normalidad. &check;
- Error identificado: cuando se edita la fecha de publicación de un libro, se cierra el programa y seguidamente se vuelve a ejecutar como la fecha se cambia en la terminal pero no el txt, generando el siguiente error `NumberFormatException.forInputString(NumberFormatException)` &check;
- El método de guardado no funciona correctamente, ya que una vez se cierra el programa
      intenta cargar libros que no existen en la terminal &check;



_________________________________________________________________________________

**ERRORES CONOCIDOS**
Hasta el momento no se han identificado más errores
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

7/09/2024 ----> 10:30pm
- se han añadido 1 clase nueva y una API en php para la conexión a la base de datos local en phpMyAdmin, con los nombres `ConectarPHP` y `Libros`
- Actualmente solo funciona el metodo de agregar en la base de datos


