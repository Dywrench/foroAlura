# foroAlura
foro para usuarios, preguntas o peticiones
Este proyecto es una API para gestionar tópicos y usuarios, desarrollada con Spring Boot y JPA (Hibernate). La API permite realizar operaciones CRUD sobre tópicos y gestionar autenticación de usuarios con roles de administrador y usuario normal.

Tecnologías utilizadas
Spring Boot: Framework para desarrollar aplicaciones Java.
Spring Security: Para la gestión de seguridad y autenticación.
JPA (Hibernate): Para la persistencia de datos en la base de datos.
H2 Database: Base de datos en memoria utilizada para el desarrollo y pruebas.
BCrypt: Para la encriptación de contraseñas.
Postman: Para realizar pruebas de la API.
Características principales
CRUD de Tópicos: Puedes crear, leer, actualizar y eliminar tópicos.
Seguridad: Implementación básica de autenticación de usuarios con roles:
Admin: Acceso completo a todas las rutas.
User: Acceso limitado a ciertas rutas.
Autenticación con JWT: Utilización de un filtro para autenticar peticiones con JWT (JSON Web Tokens).
POSTGRE Database: Utilización de una base de datos en memoria para fines de desarrollo.
