package pe.laramadita.seguridad.api.dto;
import jakarta.validation.constraints.*;
public record UsuarioRequest(@NotBlank @Size(min=3,max=60) String username,@NotBlank @Size(min=8,max=72) String password,@NotBlank @Size(min=2,max=100) String nombres,@NotBlank @Size(min=2,max=100) String apellidos,@NotBlank String rolCodigo,Boolean activo){}
