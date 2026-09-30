package pe.laramadita.seguridad.api.dto;
import java.time.Instant; import java.util.Set;
public record UsuarioResponse(Long id,String username,String nombres,String apellidos,String rolCodigo,String rolNombre,boolean activo,Set<String> permisos,Instant creadoEn,Instant actualizadoEn){}
