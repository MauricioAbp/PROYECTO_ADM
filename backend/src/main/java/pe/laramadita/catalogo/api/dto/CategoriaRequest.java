package pe.laramadita.catalogo.api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CategoriaRequest(@NotBlank @Size(min=2,max=100) String nombre,@Size(max=300) String descripcion,Boolean activo){}
