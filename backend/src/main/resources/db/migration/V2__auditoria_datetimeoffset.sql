ALTER TABLE unidades_negocio DROP CONSTRAINT df_unidades_negocio_creado;
ALTER TABLE unidades_negocio DROP CONSTRAINT df_unidades_negocio_actualizado;
ALTER TABLE unidades_negocio ALTER COLUMN creado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE unidades_negocio ALTER COLUMN actualizado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE unidades_negocio ADD CONSTRAINT df_unidades_negocio_creado DEFAULT SYSDATETIMEOFFSET() FOR creado_en;
ALTER TABLE unidades_negocio ADD CONSTRAINT df_unidades_negocio_actualizado DEFAULT SYSDATETIMEOFFSET() FOR actualizado_en;

ALTER TABLE categorias DROP CONSTRAINT df_categorias_creado;
ALTER TABLE categorias DROP CONSTRAINT df_categorias_actualizado;
ALTER TABLE categorias ALTER COLUMN creado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE categorias ALTER COLUMN actualizado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE categorias ADD CONSTRAINT df_categorias_creado DEFAULT SYSDATETIMEOFFSET() FOR creado_en;
ALTER TABLE categorias ADD CONSTRAINT df_categorias_actualizado DEFAULT SYSDATETIMEOFFSET() FOR actualizado_en;

ALTER TABLE areas_preparacion DROP CONSTRAINT df_areas_preparacion_creado;
ALTER TABLE areas_preparacion DROP CONSTRAINT df_areas_preparacion_actualizado;
ALTER TABLE areas_preparacion ALTER COLUMN creado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE areas_preparacion ALTER COLUMN actualizado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE areas_preparacion ADD CONSTRAINT df_areas_preparacion_creado DEFAULT SYSDATETIMEOFFSET() FOR creado_en;
ALTER TABLE areas_preparacion ADD CONSTRAINT df_areas_preparacion_actualizado DEFAULT SYSDATETIMEOFFSET() FOR actualizado_en;

ALTER TABLE productos DROP CONSTRAINT df_productos_creado;
ALTER TABLE productos DROP CONSTRAINT df_productos_actualizado;
ALTER TABLE productos ALTER COLUMN creado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE productos ALTER COLUMN actualizado_en DATETIMEOFFSET(6) NOT NULL;
ALTER TABLE productos ADD CONSTRAINT df_productos_creado DEFAULT SYSDATETIMEOFFSET() FOR creado_en;
ALTER TABLE productos ADD CONSTRAINT df_productos_actualizado DEFAULT SYSDATETIMEOFFSET() FOR actualizado_en;
