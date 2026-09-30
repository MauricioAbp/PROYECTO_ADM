package pe.laramadita.catalogo.api;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.laramadita.catalogo.api.dto.*;
import pe.laramadita.catalogo.service.ProductoService;
@RestController @RequestMapping("/api/v1/productos")
public class ProductoController{
 private final ProductoService service;public ProductoController(ProductoService s){service=s;}
 @PostMapping @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest r){var x=service.crear(r);return ResponseEntity.created(URI.create("/api/v1/productos/"+x.id())).body(x);}
 @GetMapping public Page<ProductoResponse> listar(@RequestParam(required=false) String buscar,@RequestParam(required=false) Long categoriaId,@RequestParam(required=false) String unidadNegocio,@RequestParam(required=false) Boolean disponible,@RequestParam(defaultValue="true") Boolean activo,@PageableDefault(size=20,sort="nombre") Pageable pageable){return service.listar(buscar,categoriaId,unidadNegocio,disponible,activo,pageable);}
 @GetMapping("/{id}") public ProductoResponse obtener(@PathVariable Long id){return service.obtener(id);}
 @PutMapping("/{id}") @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") public ProductoResponse actualizar(@PathVariable Long id,@Valid @RequestBody ProductoRequest r){return service.actualizar(id,r);}
 @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('CATALOGO_GESTIONAR')") @ResponseStatus(HttpStatus.NO_CONTENT) public void desactivar(@PathVariable Long id){service.desactivar(id);}
}
