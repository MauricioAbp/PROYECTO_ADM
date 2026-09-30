package pe.laramadita.seguridad.api;
import jakarta.validation.Valid; import java.net.URI; import java.util.List; import org.springframework.http.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import pe.laramadita.seguridad.api.dto.*; import pe.laramadita.seguridad.service.UsuarioService;
@RestController @RequestMapping("/api/v1/usuarios") @PreAuthorize("hasAnyRole('DUENO','SUPERVISOR')")
public class UsuarioController{
 private final UsuarioService service; public UsuarioController(UsuarioService s){service=s;}
 @PostMapping public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest r){var x=service.crear(r);return ResponseEntity.created(URI.create("/api/v1/usuarios/"+x.id())).body(x);}
 @GetMapping public List<UsuarioResponse> listar(){return service.listar();}
 @GetMapping("/{id}") public UsuarioResponse obtener(@PathVariable Long id){return service.obtener(id);}
 @PutMapping("/{id}") public UsuarioResponse actualizar(@PathVariable Long id,@Valid @RequestBody UsuarioActualizarRequest r){return service.actualizar(id,r);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void desactivar(@PathVariable Long id){service.desactivar(id);}
}
