package pe.laramadita.catalogo.api;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.laramadita.catalogo.api.dto.*;
import pe.laramadita.catalogo.service.CategoriaService;
@RestController @RequestMapping("/api/v1/categorias")
public class CategoriaController{
 private final CategoriaService service;public CategoriaController(CategoriaService s){service=s;}
 @PostMapping @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest r){var x=service.crear(r);return ResponseEntity.created(URI.create("/api/v1/categorias/"+x.id())).body(x);}
 @GetMapping public List<CategoriaResponse> listar(@RequestParam(required=false) Boolean activo){return service.listar(activo);}
 @GetMapping("/{id}") public CategoriaResponse obtener(@PathVariable Long id){return service.obtener(id);}
 @PutMapping("/{id}") @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") public CategoriaResponse actualizar(@PathVariable Long id,@Valid @RequestBody CategoriaRequest r){return service.actualizar(id,r);}
 @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") @ResponseStatus(HttpStatus.NO_CONTENT) public void desactivar(@PathVariable Long id){service.desactivar(id);}
}
