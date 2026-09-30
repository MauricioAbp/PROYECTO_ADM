package pe.laramadita.catalogo.api;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import pe.laramadita.catalogo.api.dto.CatalogoResponse;
import pe.laramadita.catalogo.service.CatalogoService;
@RestController @RequestMapping("/api/v1/catalogos")
public class CatalogoController{
 private final CatalogoService service;public CatalogoController(CatalogoService s){service=s;}
 @GetMapping("/areas-preparacion") public List<CatalogoResponse> areas(){return service.areas();}
 @GetMapping("/unidades-negocio") public List<CatalogoResponse> unidades(){return service.unidades();}
}
