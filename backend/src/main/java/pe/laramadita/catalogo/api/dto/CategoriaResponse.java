package pe.laramadita.catalogo.api.dto;
import java.time.Instant;
public record CategoriaResponse(Long id,String nombre,String descripcion,boolean activo,Instant creadoEn,Instant actualizadoEn){}
