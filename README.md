
**PLANEACIÓN**
- Conectar el proyecto a SpringBoot
- Conectar el proyecto a una base de datos
- Crear un diagrama de clases

________________________________________________________________________________


**DESCRIPCIÓN ACTUAL**
Este repositorio cuenta con un pequeño software para el manejo de una biblioteca, actualmente se cuentra bajo algunas limitaciones, como falta de memoria dinamica. No se encuentra conectado a una base de datos y aun puede contener errores.
Actualmente solo se puede ejecutar desde la terminal.

_________________________________________________________________________________

**ERRORES ANTERIORES SOLUCIONADOS**
- Error con el Scanner (NoSuchElementException) ----> ERROR ACTIVO 12/08/2024
- Error con arrays (InputMismatchException)
- Error en el guardado de libros
- Error cuando se muestra un libro en la terminal

_________________________________________________________________________________

**ERRORES CONOCIDOS**
- Los libros editados se pueden observar en la terminal, pero no se realiza el cambio en el archivo .txt
- Los libros suelen cargar mal generando una excepción, se debe elimar el archivo .txt generado para que el prgrama se ejecute con normalidad. (El error posiblemente se deba a editar la fecha del libro (FALTA VERIFICAR ESTO))
- Error NoSuchElementException "No line Found" cuando se presiona "ctrl + c" para cancelar ejecución del programa.
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
