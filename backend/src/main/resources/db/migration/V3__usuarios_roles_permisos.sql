CREATE TABLE permisos (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_permisos PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL CONSTRAINT uq_permisos_codigo UNIQUE,
    descripcion NVARCHAR(200) NOT NULL
);

CREATE TABLE roles (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_roles PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL CONSTRAINT uq_roles_codigo UNIQUE,
    nombre NVARCHAR(50) NOT NULL CONSTRAINT uq_roles_nombre UNIQUE,
    CONSTRAINT ck_roles_codigo CHECK (codigo IN ('DUENO','SUPERVISOR','MESERO','COCINERO','CAJA'))
);

CREATE TABLE rol_permiso (
    rol_id BIGINT NOT NULL,
    permiso_id BIGINT NOT NULL,
    CONSTRAINT pk_rol_permiso PRIMARY KEY (rol_id, permiso_id),
    CONSTRAINT fk_rol_permiso_rol FOREIGN KEY (rol_id) REFERENCES roles(id),
    CONSTRAINT fk_rol_permiso_permiso FOREIGN KEY (permiso_id) REFERENCES permisos(id)
);

CREATE TABLE usuarios (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_usuarios PRIMARY KEY,
    username VARCHAR(60) NOT NULL CONSTRAINT uq_usuarios_username UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    nombres NVARCHAR(100) NOT NULL,
    apellidos NVARCHAR(100) NOT NULL,
    activo BIT NOT NULL CONSTRAINT df_usuarios_activo DEFAULT 1,
    rol_id BIGINT NOT NULL,
    creado_en DATETIMEOFFSET(6) NOT NULL CONSTRAINT df_usuarios_creado DEFAULT SYSDATETIMEOFFSET(),
    actualizado_en DATETIMEOFFSET(6) NOT NULL CONSTRAINT df_usuarios_actualizado DEFAULT SYSDATETIMEOFFSET(),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (rol_id) REFERENCES roles(id),
    CONSTRAINT ck_usuarios_username CHECK (LEN(LTRIM(RTRIM(username))) BETWEEN 3 AND 60),
    CONSTRAINT ck_usuarios_nombres CHECK (LEN(LTRIM(RTRIM(nombres))) BETWEEN 2 AND 100),
    CONSTRAINT ck_usuarios_apellidos CHECK (LEN(LTRIM(RTRIM(apellidos))) BETWEEN 2 AND 100)
);
CREATE INDEX ix_usuarios_rol_activo ON usuarios(rol_id, activo);

INSERT INTO permisos (codigo, descripcion) VALUES
 ('USUARIOS_GESTIONAR',N'Crear, consultar y actualizar usuarios'),
 ('CATALOGO_LEER',N'Consultar categorías, productos y catálogos'),
 ('CATALOGO_GESTIONAR',N'Crear, actualizar y desactivar categorías y productos'),
 ('PEDIDOS_GESTIONAR',N'Permiso reservado para la futura gestión de pedidos'),
 ('COCINA_GESTIONAR',N'Permiso reservado para la futura operación de cocina'),
 ('COBROS_REGISTRAR',N'Permiso reservado para el futuro registro de cobros'),
 ('CAJA_GESTIONAR',N'Permiso reservado para la futura operación de caja'),
 ('REPORTES_VER',N'Permiso reservado para futuros reportes');

INSERT INTO roles (codigo,nombre) VALUES
 ('DUENO',N'Dueño'),('SUPERVISOR',N'Supervisor'),('MESERO',N'Mesero'),('COCINERO',N'Cocinero'),('CAJA',N'Caja');

INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='DUENO';
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='SUPERVISOR' AND p.codigo IN ('USUARIOS_GESTIONAR','CATALOGO_LEER','CATALOGO_GESTIONAR','REPORTES_VER');
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='MESERO' AND p.codigo IN ('CATALOGO_LEER','PEDIDOS_GESTIONAR','COBROS_REGISTRAR');
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='COCINERO' AND p.codigo IN ('CATALOGO_LEER','COCINA_GESTIONAR');
INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='CAJA' AND p.codigo IN ('CATALOGO_LEER','COBROS_REGISTRAR','CAJA_GESTIONAR');
