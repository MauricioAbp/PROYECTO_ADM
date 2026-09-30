package pe.laramadita.catalogo.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.laramadita.catalogo.api.dto.CatalogoResponse;
import pe.laramadita.catalogo.repository.*;
@Service @Transactional(readOnly=true)
public class CatalogoService{
 private final AreaPreparacionRepository areas;private final UnidadNegocioRepository unidades;
 public CatalogoService(AreaPreparacionRepository a,UnidadNegocioRepository u){areas=a;unidades=u;}
 public List<CatalogoResponse> areas(){return areas.findAllByActivoTrueOrderByNombre().stream().map(a->new CatalogoResponse(a.getId(),null,a.getNombre())).toList();}
 public List<CatalogoResponse> unidades(){return unidades.findAllByActivoTrueOrderByNombre().stream().map(u->new CatalogoResponse(u.getId(),u.getCodigo(),u.getNombre())).toList();}
}
