package pe.laramadita.catalogo.model;

import jakarta.persistence.*;

@Entity @Table(name="unidades_negocio")
public class UnidadNegocio extends EntidadAuditable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=20,unique=true) private String codigo;
    @Column(nullable=false,length=80,unique=true) private String nombre;
    @Column(nullable=false) private boolean activo=true;
    protected UnidadNegocio(){}
    public Long getId(){return id;} public String getCodigo(){return codigo;} public String getNombre(){return nombre;} public boolean isActivo(){return activo;}
}
