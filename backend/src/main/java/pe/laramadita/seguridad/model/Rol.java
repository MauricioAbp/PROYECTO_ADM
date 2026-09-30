package pe.laramadita.seguridad.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name="roles")
public class Rol {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=20) private String codigo;
 @Column(nullable=false,unique=true,length=50) private String nombre;
 @ManyToMany(fetch=FetchType.EAGER) @JoinTable(name="rol_permiso",joinColumns=@JoinColumn(name="rol_id"),inverseJoinColumns=@JoinColumn(name="permiso_id"))
 private Set<Permiso> permisos=new LinkedHashSet<>();
 public Long getId(){return id;} public String getCodigo(){return codigo;} public String getNombre(){return nombre;} public Set<Permiso> getPermisos(){return permisos;}
}
