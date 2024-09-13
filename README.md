
# **PLANEACIÓN**
- Conectar el proyecto a SpringBoot &cross;
- Conectar el proyecto a una base de datos &check; 
- crear interfaz gráfica &cross;
- Crear un diagrama de clases &check;
- Cambiar los arrays por listas &check;
- Crear base de datos simulada con archivo .txt &check;
- Crear una clase para el prestamo de un libro a un usuario &cross; :warning:

# NOTA TEMPORAL IMPORTANTE
_**Actualmente se están realizando cambios significativos en el proyecto, incluyendo la implementación de la funcionalidad de préstamos, la corrección de errores menores en la persistencia de datos y conexiones (Estos cambios no afectan la funcionalidad actual del proyecto). Se espera que estos cambios estén finalizados en un tiempo estimado de 5. Durante este período, es posible que se encuentren algunas inconsistencias o funcionalidades incompletas. Si encuentras algún problema, por favor repórtalo en el siguiente enlace: [REPORTAR_ERROR](https://github.com/DanielRestrepoGaleano/BibliotecaJava/issues). Por tanto espere una actualización a más tardar el martes 17/09/2024**_

________________________________________________________________________________


# **DESCRIPCIÓN ACTUAL**
Este repositorio contiene un software de gestión de biblioteca que se conecta a una base de datos MySQL a través de JDBC y gestiona las operaciones de CRUD (Crear, Leer, Actualizar, Eliminar) para los libros. También cuenta con una persistencia en archivos .txt como respaldo.

El proyecto utiliza **XAMPP** para gestionar la base de datos MySQL con **phpMyAdmin**, y el conector JDBC para interactuar con la base de datos desde Java.



________________________________________________________________________________

# **MODO DE USO**

## Requisitos

Para ejecutar este proyecto, es necesario:

1. **Conector JDBC**: 
   Descargue e instale el conector JDBC desde la página oficial de MySQL:  
   [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

2. **Base de datos MySQL**:
   - Deberá crear una base de datos con el nombre `biblioteca`.
   - Dentro de esta base de datos, cree una tabla `libros`.

3. **Entorno de desarrollo**:
   - Un IDE que soporte Java (Eclipse, IntelliJ IDEA, NetBeans, etc.).
   - Asegúrese de tener el archivo JAR del conector JDBC incluido en su proyecto para que las conexiones a la base de datos funcionen correctamente.

### Modo de uso

En su IDE, deberá crear las siguientes clases:

- `Biblioteca.java`: Clase principal del proyecto que gestiona las operaciones de la biblioteca.
- `ConexionDB.java`: Clase responsable de la conexión con la base de datos MySQL y la ejecución de las consultas.
- `Libro.java`: Clase que representa los objetos de tipo libro en el sistema.

### ConexionDB.java

Esta clase maneja toda la lógica de conexión con la base de datos. Sus métodos son llamados desde `Biblioteca.java` para realizar las operaciones necesarias en la base de datos, como agregar, editar y eliminar libros.

### Biblioteca.java

Es el punto de entrada del programa y coordina las interacciones entre la interfaz de usuario y la base de datos. Utiliza los métodos de `ConexionDB.java` para realizar las distintas operaciones sobre la tabla `libros`.

## Instalación

1. **Instalar MySQL y XAMPP**:
   - Si aún no lo tiene, instale XAMPP y asegúrese de activar **MySQL** y **Apache**.
   
2. **Configurar la base de datos**:
   - Cree la base de datos con el nombre `biblioteca` y la tabla `libros` que contiene la información de los libros gestionados por el sistema.

3. **Configuración del proyecto**:
   - Asegúrese de que el conector JDBC esté correctamente configurado en su IDE.
   - La clase `ConexionDB.java` será la encargada de manejar la conexión a la base de datos.

## Funcionalidades del programa

- **Gestión de libros**: Agregar, editar y eliminar libros en la base de datos.
- **Persistencia**: Aunque el proyecto ya no utiliza PHP ni archivos externos para el manejo de libros, el programa sigue generando un archivo `.txt` con los libros registrados.
- **Conexión a base de datos**: Se realiza mediante el uso de JDBC, reemplazando la versión anterior que utilizaba un API en PHP.

## Limitaciones

- El programa aún presenta algunas limitaciones en cuanto a la funcionalidad completa del sistema.
- Se pueden agregar más funcionalidades en futuras versiones.

## Soporte

- Ante cualquier duda o problema, puede ponerse en contacto por cualquier medio disponible.
- En la carpeta "GUIA DE IMAGENES" se incluyen imágenes que pueden servir de ayuda para la instalación y configuración del sistema.

Si necesita imágenes adicionales o pasos más detallados, no dude en hacer llegar una notificación y se actualizará la documentación con más información.

### ANEXO CODIGO PARA CREAR LA TABLA EN LA BASE DE DATOS BIBLIOTECA EN PHPMYADMIN
CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    autor VARCHAR(50),
    fechaPublicacion INT,
    numPaginas INT,
    disponible TINYINT(1),
    isbn CHAR(13), -- Ajusta la longitud según el formato ISBN que uses
    descripcion TEXT
);

#### NOTAS 
- TODO LO QUE CONTENGA :warning: se le dará prioridad y será implementado cuanto antes.
- web utilizado para el diagrama de clases [mermaid](https://mermaid.live/)
-   No se usa (por el momento) SRP  [Single Responsibility Principle](https://www.baeldung.com/java-single-responsibility-principle) (Principio de responsabilidad unica)[https://trbl-services.eu/blog-solid-single-responsability/#:~:text=Este%20principio%20establece%20que%20cada,la%20finalidad%20de%20la%20clase.]


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

# **ERRORES CONOCIDOS**
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

 # **CAMBIOS**
__________________________________________________________  
08/08/2024----3:00pm

- Se realizaron cambios en la estructura de las funciones
- Se les dio nombres más claros y fueron enviadas a las clases correspondienes para cada función

____________________________________________________________ 
08/08/2024--10:50pm

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

_____________________________________________________________________________________

10/09/2024 ---> 10:37am

- Se han implementado los métodos básicos del CRUD a la base de datos
- Se ha eliminado `PHP` debido a dificultades con su conexión a la base de datos
- La clase `ConexionDB.java` ahora realiza toda la conexión
- se han instalado dependencias `Referenced Libraries` de *https://dev.mysql.com/downloads/connector/j/

_____________________________________________________________________________________

11/09/2024 --> 10:45am

- Se ha arreglado el método para editar los libros en la BD

---> 5:47pm

- Se ha añadido el loggin de usuarios junto con su respectiva conexion a la BD

