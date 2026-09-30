package pe.laramadita.catalogo.model;

import jakarta.persistence.*;

@Entity @Table(name="areas_preparacion")
public class AreaPreparacion extends EntidadAuditable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100,unique=true) private String nombre;
    @Column(nullable=false) private boolean activo=true;
    protected AreaPreparacion(){}
    public Long getId(){return id;} public String getNombre(){return nombre;} public boolean isActivo(){return activo;}
}
