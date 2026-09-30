package pe.laramadita.seguridad.api.dto;
import java.time.Instant; import java.util.Set;
public record LoginResponse(String token,String tipo,Instant expiraEn,UsuarioResponse usuario,Set<String> permisos){}
