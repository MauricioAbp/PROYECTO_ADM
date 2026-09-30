package pe.laramadita.catalogo.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.laramadita.catalogo.api.dto.*;
import pe.laramadita.catalogo.model.Categoria;
import pe.laramadita.catalogo.repository.CategoriaRepository;
import pe.laramadita.shared.error.*;

@Service @Transactional
public class CategoriaService {
    private final CategoriaRepository repository;
    public CategoriaService(CategoriaRepository repository){this.repository=repository;}
    public CategoriaResponse crear(CategoriaRequest request){String nombre=normalizar(request.nombre());if(repository.existsByNombreIgnoreCase(nombre))throw new ConflictoException("Ya existe una categoría con ese nombre");return map(repository.save(new Categoria(nombre,limpiar(request.descripcion()))));}
    @Transactional(readOnly=true) public List<CategoriaResponse> listar(Boolean activo){return repository.findAll(Sort.by("nombre")).stream().filter(c->activo==null||c.isActivo()==activo).map(this::map).toList();}
    @Transactional(readOnly=true) public CategoriaResponse obtener(Long id){return map(buscar(id));}
    public CategoriaResponse actualizar(Long id,CategoriaRequest request){Categoria c=buscar(id);String nombre=normalizar(request.nombre());if(repository.existsByNombreIgnoreCaseAndIdNot(nombre,id))throw new ConflictoException("Ya existe una categoría con ese nombre");c.actualizar(nombre,limpiar(request.descripcion()),request.activo()==null?c.isActivo():request.activo());return map(c);}
    public void desactivar(Long id){buscar(id).desactivar();}
    private Categoria buscar(Long id){return repository.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Categoría no encontrada: "+id));}
    private String normalizar(String v){return v.trim().replaceAll("\\s+"," ");} private String limpiar(String v){return v==null||v.isBlank()?null:v.trim();}
    private CategoriaResponse map(Categoria c){return new CategoriaResponse(c.getId(),c.getNombre(),c.getDescripcion(),c.isActivo(),c.getCreadoEn(),c.getActualizadoEn());}
}
