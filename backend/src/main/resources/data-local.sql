INSERT INTO unidades_negocio (codigo, nombre, activo, creado_en, actualizado_en) VALUES
('RESTAURANTE', 'Restaurante', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('POLLERIA', 'Pollería', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO areas_preparacion (nombre, activo, creado_en, actualizado_en) VALUES
('Cocina del primer piso', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cocina principal del segundo piso', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categorias (nombre, descripcion, activo, creado_en, actualizado_en) VALUES
('Menús', 'Menús y platos del día', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Entradas', 'Entradas y piqueos', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ceviches', 'Platos preparados en el área de ceviches', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Arroces', 'Platos a base de arroz', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Platos', 'Platos a la carta', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pollos', 'Pollos, parrillas y complementos', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bebidas', 'Bebidas frías y calientes', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Postres', 'Postres y dulces', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO permisos (codigo,descripcion) VALUES ('USUARIOS_GESTIONAR','Usuarios'),('CATALOGO_LEER','Consultar catálogo'),('CATALOGO_GESTIONAR','Gestionar catálogo'),('PEDIDOS_GESTIONAR','Pedidos futuros'),('COCINA_GESTIONAR','Cocina futura'),('COBROS_REGISTRAR','Cobros futuros'),('CAJA_GESTIONAR','Caja futura'),('REPORTES_VER','Reportes futuros');
INSERT INTO roles (codigo,nombre) VALUES ('DUENO','Dueño'),('SUPERVISOR','Supervisor'),('MESERO','Mesero'),('COCINERO','Cocinero'),('CAJA','Caja');
INSERT INTO rol_permiso (rol_id,permiso_id) SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='DUENO';
INSERT INTO rol_permiso (rol_id,permiso_id) SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='SUPERVISOR' AND p.codigo IN ('USUARIOS_GESTIONAR','CATALOGO_LEER','CATALOGO_GESTIONAR','REPORTES_VER');
INSERT INTO rol_permiso (rol_id,permiso_id) SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='MESERO' AND p.codigo IN ('CATALOGO_LEER','PEDIDOS_GESTIONAR','COBROS_REGISTRAR');
INSERT INTO rol_permiso (rol_id,permiso_id) SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='COCINERO' AND p.codigo IN ('CATALOGO_LEER','COCINA_GESTIONAR');
INSERT INTO rol_permiso (rol_id,permiso_id) SELECT r.id,p.id FROM roles r CROSS JOIN permisos p WHERE r.codigo='CAJA' AND p.codigo IN ('CATALOGO_LEER','COBROS_REGISTRAR','CAJA_GESTIONAR');
