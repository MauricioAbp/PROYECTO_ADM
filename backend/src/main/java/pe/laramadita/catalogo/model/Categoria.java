package pe.laramadita.catalogo.model;

import jakarta.persistence.*;

@Entity @Table(name="categorias")
public class Categoria extends EntidadAuditable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100,unique=true) private String nombre;
    @Column(length=300) private String descripcion;
    @Column(nullable=false) private boolean activo=true;
    protected Categoria(){}
    public Categoria(String nombre,String descripcion){this.nombre=nombre;this.descripcion=descripcion;}
    public void actualizar(String nombre,String descripcion,boolean activo){this.nombre=nombre;this.descripcion=descripcion;this.activo=activo;}
    public void desactivar(){activo=false;}
    public Long getId(){return id;} public String getNombre(){return nombre;} public String getDescripcion(){return descripcion;} public boolean isActivo(){return activo;}
}
