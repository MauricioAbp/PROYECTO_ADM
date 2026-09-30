package pe.laramadita.catalogo.service;

import java.util.LinkedHashSet;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.laramadita.catalogo.api.dto.*;
import pe.laramadita.catalogo.model.*;
import pe.laramadita.catalogo.repository.*;
import pe.laramadita.shared.error.*;

@Service @Transactional
public class ProductoService {
    private final ProductoRepository productos; private final CategoriaRepository categorias; private final AreaPreparacionRepository areas; private final UnidadNegocioRepository unidades;
    public ProductoService(ProductoRepository p,CategoriaRepository c,AreaPreparacionRepository a,UnidadNegocioRepository u){productos=p;categorias=c;areas=a;unidades=u;}
    public ProductoResponse crear(ProductoRequest r){String nombre=normalizar(r.nombre());if(productos.existsByNombreIgnoreCase(nombre))throw new ConflictoException("Ya existe un producto con ese nombre");var refs=referencias(r);return map(productos.save(new Producto(nombre,limpiar(r.descripcion()),r.precio(),refs.categoria(),refs.area(),refs.unidades(),r.disponible())));}
    @Transactional(readOnly=true) public Page<ProductoResponse> listar(String buscar,Long categoriaId,String unidad,Boolean disponible,Boolean activo,Pageable pageable){return productos.findAll(ProductoSpecifications.filtros(buscar,categoriaId,unidad,disponible,activo),pageable).map(this::map);}
    @Transactional(readOnly=true) public ProductoResponse obtener(Long id){return map(buscar(id));}
    public ProductoResponse actualizar(Long id,ProductoRequest r){Producto p=buscar(id);String nombre=normalizar(r.nombre());if(productos.existsByNombreIgnoreCaseAndIdNot(nombre,id))throw new ConflictoException("Ya existe un producto con ese nombre");var refs=referencias(r);p.actualizar(nombre,limpiar(r.descripcion()),r.precio(),refs.categoria(),refs.area(),refs.unidades(),r.disponible(),r.activo()==null?p.isActivo():r.activo());return map(p);}
    public void desactivar(Long id){buscar(id).desactivar();}
    private Referencias referencias(ProductoRequest r){Categoria c=categorias.findByIdAndActivoTrue(r.categoriaId()).orElseThrow(()->new ReglaNegocioException("La categoría no existe o está inactiva"));AreaPreparacion a=areas.findByIdAndActivoTrue(r.areaPreparacionId()).orElseThrow(()->new ReglaNegocioException("El área de preparación no existe o está inactiva"));var u=unidades.findAllByCodigoInAndActivoTrue(r.unidadesNegocio());if(u.size()!=r.unidadesNegocio().size())throw new ReglaNegocioException("Una o más unidades de negocio no existen o están inactivas");return new Referencias(c,a,new LinkedHashSet<>(u));}
    private Producto buscar(Long id){return productos.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Producto no encontrado: "+id));}
    private ProductoResponse map(Producto p){var categoria=new CatalogoResponse(p.getCategoria().getId(),null,p.getCategoria().getNombre());var area=new CatalogoResponse(p.getAreaPreparacion().getId(),null,p.getAreaPreparacion().getNombre());var unidades=p.getUnidadesNegocio().stream().sorted((a,b)->a.getCodigo().compareTo(b.getCodigo())).map(u->new CatalogoResponse(u.getId(),u.getCodigo(),u.getNombre())).collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));return new ProductoResponse(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),categoria,area,unidades,p.isDisponible(),p.isActivo(),p.getCreadoEn(),p.getActualizadoEn());}
    private String normalizar(String v){return v.trim().replaceAll("\\s+"," ");} private String limpiar(String v){return v==null||v.isBlank()?null:v.trim();}
    private record Referencias(Categoria categoria,AreaPreparacion area,LinkedHashSet<UnidadNegocio> unidades){}
}
