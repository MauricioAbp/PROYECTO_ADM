package pe.laramadita.catalogo.api.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
public record ProductoResponse(Long id,String nombre,String descripcion,BigDecimal precio,CatalogoResponse categoria,CatalogoResponse areaPreparacion,Set<CatalogoResponse> unidadesNegocio,boolean disponible,boolean activo,Instant creadoEn,Instant actualizadoEn){}
