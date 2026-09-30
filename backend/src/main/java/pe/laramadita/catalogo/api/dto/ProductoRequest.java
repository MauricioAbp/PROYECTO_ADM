package pe.laramadita.catalogo.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public record ProductoRequest(
    @NotBlank @Size(min=2,max=150) String nombre,
    @Size(max=500) String descripcion,
    @NotNull @DecimalMin(value="0.01") @Digits(integer=8,fraction=2) BigDecimal precio,
    @NotNull @Positive Long categoriaId,
    @NotNull @Positive Long areaPreparacionId,
    @NotEmpty Set<@Pattern(regexp="RESTAURANTE|POLLERIA",message="debe ser RESTAURANTE o POLLERIA") String> unidadesNegocio,
    @NotNull Boolean disponible,
    Boolean activo
){}
