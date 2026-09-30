package pe.laramadita.catalogo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name="productos")
public class Producto extends EntidadAuditable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=150,unique=true) private String nombre;
    @Column(length=500) private String descripcion;
    @Column(nullable=false,precision=10,scale=2) private BigDecimal precio;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="categoria_id") private Categoria categoria;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="area_preparacion_id") private AreaPreparacion areaPreparacion;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="producto_unidad_negocio",joinColumns=@JoinColumn(name="producto_id"),inverseJoinColumns=@JoinColumn(name="unidad_negocio_id"))
    private Set<UnidadNegocio> unidadesNegocio=new LinkedHashSet<>();
    @Column(nullable=false) private boolean disponible=true;
    @Column(nullable=false) private boolean activo=true;
    protected Producto(){}
    public Producto(String nombre,String descripcion,BigDecimal precio,Categoria categoria,AreaPreparacion area,Set<UnidadNegocio> unidades,boolean disponible){actualizar(nombre,descripcion,precio,categoria,area,unidades,disponible,true);}
    public void actualizar(String nombre,String descripcion,BigDecimal precio,Categoria categoria,AreaPreparacion area,Set<UnidadNegocio> unidades,boolean disponible,boolean activo){this.nombre=nombre;this.descripcion=descripcion;this.precio=precio;this.categoria=categoria;this.areaPreparacion=area;this.unidadesNegocio.clear();this.unidadesNegocio.addAll(unidades);this.disponible=disponible;this.activo=activo;}
    public void desactivar(){activo=false;disponible=false;}
    public Long getId(){return id;} public String getNombre(){return nombre;} public String getDescripcion(){return descripcion;} public BigDecimal getPrecio(){return precio;} public Categoria getCategoria(){return categoria;} public AreaPreparacion getAreaPreparacion(){return areaPreparacion;} public Set<UnidadNegocio> getUnidadesNegocio(){return unidadesNegocio;} public boolean isDisponible(){return disponible;} public boolean isActivo(){return activo;}
}
