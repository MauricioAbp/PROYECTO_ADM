package pe.laramadita.seguridad.model;

import jakarta.persistence.*;
import pe.laramadita.catalogo.model.EntidadAuditable;

@Entity @Table(name="usuarios")
public class Usuario extends EntidadAuditable {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=60) private String username;
 @Column(name="password_hash",nullable=false,length=100) private String passwordHash;
 @Column(nullable=false,length=100) private String nombres;
 @Column(nullable=false,length=100) private String apellidos;
 @Column(nullable=false) private boolean activo=true;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="rol_id") private Rol rol;
 protected Usuario(){}
 public Usuario(String username,String passwordHash,String nombres,String apellidos,Rol rol){this.username=username;this.passwordHash=passwordHash;this.nombres=nombres;this.apellidos=apellidos;this.rol=rol;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getPasswordHash(){return passwordHash;} public String getNombres(){return nombres;} public String getApellidos(){return apellidos;} public boolean isActivo(){return activo;} public Rol getRol(){return rol;}
 public void actualizar(String username,String nombres,String apellidos,Rol rol,boolean activo){this.username=username;this.nombres=nombres;this.apellidos=apellidos;this.rol=rol;this.activo=activo;}
 public void cambiarPassword(String hash){passwordHash=hash;} public void desactivar(){activo=false;}
}
