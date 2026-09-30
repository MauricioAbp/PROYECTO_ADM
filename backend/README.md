# Backend de La Ramadita

## Inicio local inmediato (sin SQL Server)

Para probar la API con una base temporal en memoria:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

Los datos se reinician cada vez que se detiene la aplicación. La configuración normal descrita abajo continúa usando SQL Server.

El backend implementa categorías, productos, usuarios, inicio de sesión y permisos. No contiene todavía pedidos, pagos, inventario ni Business Intelligence.

## Modelo de datos

- `categorias`: agrupación comercial del producto. Se desactiva sin eliminar registros.
- `areas_preparacion`: catálogo extensible. Se inicializa con las cocinas del primer y segundo piso confirmadas por el stakeholder.
- `unidades_negocio`: contiene `RESTAURANTE` y `POLLERIA`.
- `productos`: nombre único, descripción, precio, categoría, área, disponibilidad y estado activo.
- `producto_unidad_negocio`: relación muchos a muchos. Un producto de ambas unidades tiene dos filas; no se guarda el texto `AMBOS`.

`disponible=false` significa agotado temporalmente y permite mostrar el producto deshabilitado. `activo=false` es una baja lógica permanente o administrativa. Los endpoints `DELETE` nunca borran físicamente.

## Requisitos

- Java 21 o superior.
- Maven 3.6.3 o superior, o el Maven Wrapper incluido.
- SQL Server 2022, local o en Docker.

## Crear la base de datos

La aplicación no crea la base contenedora porque la conexión necesita que esta exista. Ejecuta primero [00_create_database.sql](database/sqlserver/00_create_database.sql) con una cuenta autorizada. Al iniciar la API, Flyway aplicará automáticamente [V1__categorias_productos.sql](src/main/resources/db/migration/V1__categorias_productos.sql).

Con Docker puedes iniciar SQL Server así:

```powershell
$env:DB_PASSWORD="UnaClaveSegura_2026!"
docker compose up -d
docker exec la-ramadita-sqlserver /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P $env:DB_PASSWORD -C -Q "IF DB_ID(N'LaRamadita') IS NULL CREATE DATABASE LaRamadita"
```

## Configuración

La aplicación admite estas variables:

| Variable | Predeterminado | Uso |
|---|---:|---|
| `DB_HOST` | `localhost` | Servidor SQL Server |
| `DB_PORT` | `1433` | Puerto |
| `DB_NAME` | `LaRamadita` | Base de datos |
| `DB_USER` | `sa` | Usuario |
| `DB_PASSWORD` | vacío | Contraseña, obligatoria en la práctica |
| `DB_ENCRYPT` | `true` | Cifra la conexión JDBC |
| `DB_TRUST_SERVER_CERTIFICATE` | `true` | Útil para certificado local autofirmado; usar `false` con certificado válido |
| `SERVER_PORT` | `8080` | Puerto HTTP |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:4200` | Origen permitido del frontend |
| `JWT_SECRET` | clave local de desarrollo | Firma de los tokens; configure una clave larga en otros ambientes |
| `JWT_TOKEN_MINUTES` | `480` | Duración del token de acceso |
| `APP_ADMIN_USERNAME` | `dueno` | Usuario Dueño inicial, creado solo cuando aún no hay usuarios |
| `APP_ADMIN_PASSWORD` | `Ramadita2026!` | Contraseña inicial; debe cambiarse mediante variables de entorno |

Ejemplo en PowerShell:

```powershell
$env:DB_HOST="localhost"
$env:DB_NAME="LaRamadita"
$env:DB_USER="sa"
$env:DB_PASSWORD="UnaClaveSegura_2026!"
./mvnw.cmd spring-boot:run
```

## API

Todos los endpoints, salvo `POST /api/v1/auth/login`, requieren `Authorization: Bearer <token>`.

### Usuarios y acceso

- `POST /api/v1/auth/login`
- `POST /api/v1/usuarios`
- `GET /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `PUT /api/v1/usuarios/{id}`
- `DELETE /api/v1/usuarios/{id}` desactiva sin borrar

Dueño posee todos los permisos. Supervisor gestiona usuarios y catálogo. Mesero consulta el catálogo y tiene asignados los permisos futuros de pedidos y cobros, porque el cuestionario confirma que también cobra. Cocinero consulta el catálogo y tiene el permiso futuro de cocina. Caja consulta el catálogo y tiene los permisos futuros de cobros y caja. Los permisos futuros no crean endpoints de pedidos ni pagos.

Las contraseñas se almacenan con BCrypt. Un usuario desactivado no puede iniciar sesión y sus tokens emitidos dejan de aceptarse. Solo Dueño y Supervisor pueden crear, actualizar o desactivar categorías y productos.

Ejemplo de inicio de sesión:

```powershell
$login = Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/auth/login `
  -ContentType application/json -Body '{"username":"dueno","password":"Ramadita2026!"}'
$headers = @{ Authorization = "Bearer $($login.token)" }
Invoke-RestMethod http://localhost:8080/api/v1/productos -Headers $headers
```

### Categorías

- `POST /api/v1/categorias`
- `GET /api/v1/categorias?activo=true`
- `GET /api/v1/categorias/{id}`
- `PUT /api/v1/categorias/{id}`
- `DELETE /api/v1/categorias/{id}` desactiva la categoría

### Productos

- `POST /api/v1/productos`
- `GET /api/v1/productos`
- `GET /api/v1/productos/{id}`
- `PUT /api/v1/productos/{id}`
- `DELETE /api/v1/productos/{id}` desactiva y marca no disponible

El listado acepta `buscar`, `categoriaId`, `unidadNegocio`, `disponible`, `activo`, `page`, `size` y `sort`.

### Catálogos de apoyo

- `GET /api/v1/catalogos/areas-preparacion`
- `GET /api/v1/catalogos/unidades-negocio`

## Prueba rápida

Consulta primero los identificadores de categoría y área. Luego registra un producto:

```powershell
$body = @{
  nombre = "Pollo a la brasa"
  descripcion = "Cuarto de pollo con papas y ensalada"
  precio = 22.00
  categoriaId = 6
  areaPreparacionId = 2
  unidadesNegocio = @("POLLERIA")
  disponible = $true
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/productos -ContentType application/json -Body $body
Invoke-RestMethod http://localhost:8080/api/v1/productos?unidadNegocio=POLLERIA
Invoke-RestMethod -Method Delete http://localhost:8080/api/v1/productos/1
```

Para un producto disponible en ambos negocios usa `unidadesNegocio: ["RESTAURANTE", "POLLERIA"]`.

## Verificación

```powershell
./mvnw.cmd test
./mvnw.cmd clean package
```
