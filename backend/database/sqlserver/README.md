# Scripts de SQL Server

1. Ejecuta `00_create_database.sql` con una cuenta que pueda crear bases de datos.
2. Configura las variables de entorno de la API.
3. Inicia Spring Boot. Flyway ejecutará automáticamente el esquema ubicado en
   `src/main/resources/db/migration/V1__categorias_productos.sql`.

El archivo de migración es también el script SQL canónico del módulo. Incluye tablas,
claves foráneas, restricciones, índices y datos iniciales.
