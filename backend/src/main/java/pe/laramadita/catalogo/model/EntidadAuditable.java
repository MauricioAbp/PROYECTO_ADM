package pe.laramadita.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;

@MappedSuperclass
public abstract class EntidadAuditable {
    @Column(name="creado_en", nullable=false, updatable=false) private Instant creadoEn;
    @Column(name="actualizado_en", nullable=false) private Instant actualizadoEn;
    @PrePersist void alCrear(){var ahora=Instant.now();creadoEn=ahora;actualizadoEn=ahora;}
    @PreUpdate void alActualizar(){actualizadoEn=Instant.now();}
    public Instant getCreadoEn(){return creadoEn;}
    public Instant getActualizadoEn(){return actualizadoEn;}
}
