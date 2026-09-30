CREATE TABLE unidades_negocio (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_unidades_negocio PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL CONSTRAINT uq_unidades_negocio_codigo UNIQUE,
    nombre NVARCHAR(80) NOT NULL CONSTRAINT uq_unidades_negocio_nombre UNIQUE,
    activo BIT NOT NULL CONSTRAINT df_unidades_negocio_activo DEFAULT 1,
    creado_en DATETIME2(3) NOT NULL CONSTRAINT df_unidades_negocio_creado DEFAULT SYSUTCDATETIME(),
    actualizado_en DATETIME2(3) NOT NULL CONSTRAINT df_unidades_negocio_actualizado DEFAULT SYSUTCDATETIME(),
    CONSTRAINT ck_unidades_negocio_codigo CHECK (codigo IN ('RESTAURANTE', 'POLLERIA'))
);

CREATE TABLE categorias (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_categorias PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(300) NULL,
    activo BIT NOT NULL CONSTRAINT df_categorias_activo DEFAULT 1,
    creado_en DATETIME2(3) NOT NULL CONSTRAINT df_categorias_creado DEFAULT SYSUTCDATETIME(),
    actualizado_en DATETIME2(3) NOT NULL CONSTRAINT df_categorias_actualizado DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_categorias_nombre UNIQUE (nombre),
    CONSTRAINT ck_categorias_nombre CHECK (LEN(LTRIM(RTRIM(nombre))) BETWEEN 2 AND 100)
);

CREATE TABLE areas_preparacion (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_areas_preparacion PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL CONSTRAINT uq_areas_preparacion_nombre UNIQUE,
    activo BIT NOT NULL CONSTRAINT df_areas_preparacion_activo DEFAULT 1,
    creado_en DATETIME2(3) NOT NULL CONSTRAINT df_areas_preparacion_creado DEFAULT SYSUTCDATETIME(),
    actualizado_en DATETIME2(3) NOT NULL CONSTRAINT df_areas_preparacion_actualizado DEFAULT SYSUTCDATETIME(),
    CONSTRAINT ck_areas_preparacion_nombre CHECK (LEN(LTRIM(RTRIM(nombre))) BETWEEN 2 AND 100)
);

CREATE TABLE productos (
    id BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT pk_productos PRIMARY KEY,
    nombre NVARCHAR(150) NOT NULL,
    descripcion NVARCHAR(500) NULL,
    precio DECIMAL(10,2) NOT NULL,
    categoria_id BIGINT NOT NULL,
    area_preparacion_id BIGINT NOT NULL,
    disponible BIT NOT NULL CONSTRAINT df_productos_disponible DEFAULT 1,
    activo BIT NOT NULL CONSTRAINT df_productos_activo DEFAULT 1,
    creado_en DATETIME2(3) NOT NULL CONSTRAINT df_productos_creado DEFAULT SYSUTCDATETIME(),
    actualizado_en DATETIME2(3) NOT NULL CONSTRAINT df_productos_actualizado DEFAULT SYSUTCDATETIME(),
    CONSTRAINT uq_productos_nombre UNIQUE (nombre),
    CONSTRAINT ck_productos_nombre CHECK (LEN(LTRIM(RTRIM(nombre))) BETWEEN 2 AND 150),
    CONSTRAINT ck_productos_precio CHECK (precio > 0),
    CONSTRAINT fk_productos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_productos_area FOREIGN KEY (area_preparacion_id) REFERENCES areas_preparacion(id)
);

CREATE TABLE producto_unidad_negocio (
    producto_id BIGINT NOT NULL,
    unidad_negocio_id BIGINT NOT NULL,
    CONSTRAINT pk_producto_unidad_negocio PRIMARY KEY (producto_id, unidad_negocio_id),
    CONSTRAINT fk_pun_producto FOREIGN KEY (producto_id) REFERENCES productos(id),
    CONSTRAINT fk_pun_unidad FOREIGN KEY (unidad_negocio_id) REFERENCES unidades_negocio(id)
);

CREATE INDEX ix_productos_categoria ON productos(categoria_id);
CREATE INDEX ix_productos_area ON productos(area_preparacion_id);
CREATE INDEX ix_productos_activo_disponible ON productos(activo, disponible);
CREATE INDEX ix_pun_unidad ON producto_unidad_negocio(unidad_negocio_id, producto_id);

INSERT INTO unidades_negocio (codigo, nombre) VALUES ('RESTAURANTE', N'Restaurante'), ('POLLERIA', N'Pollería');
INSERT INTO areas_preparacion (nombre) VALUES (N'Cocina del primer piso'), (N'Cocina principal del segundo piso');
INSERT INTO categorias (nombre, descripcion) VALUES
    (N'Menús', N'Menús y platos del día'), (N'Entradas', N'Entradas y piqueos'),
    (N'Ceviches', N'Platos preparados en el área de ceviches'), (N'Arroces', N'Platos a base de arroz'),
    (N'Platos', N'Platos a la carta'), (N'Pollos', N'Pollos, parrillas y complementos'),
    (N'Bebidas', N'Bebidas frías y calientes'), (N'Postres', N'Postres y dulces');
