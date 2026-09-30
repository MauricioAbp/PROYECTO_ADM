package pe.laramadita.seguridad.api;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import pe.laramadita.seguridad.api.dto.*; import pe.laramadita.seguridad.service.AuthService;
@RestController @RequestMapping("/api/v1/auth")
public class AuthController{private final AuthService service;public AuthController(AuthService s){service=s;}@PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest r){return service.login(r);}}
