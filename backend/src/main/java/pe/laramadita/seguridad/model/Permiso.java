package pe.laramadita.seguridad.model;

import jakarta.persistence.*;

@Entity @Table(name="permisos")
public class Permiso {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=50) private String codigo;
 @Column(nullable=false,length=200) private String descripcion;
 public Long getId(){return id;} public String getCodigo(){return codigo;} public String getDescripcion(){return descripcion;}
}
