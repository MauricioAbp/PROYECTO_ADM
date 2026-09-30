package pe.laramadita.seguridad.service;
import org.springframework.security.authentication.*; import org.springframework.stereotype.Service;
import pe.laramadita.seguridad.api.dto.*;
@Service
public class AuthService{
 private final AuthenticationManager authenticationManager; private final UsuarioDetailsService details; private final JwtService jwt; private final UsuarioService usuarios;
 public AuthService(AuthenticationManager a,UsuarioDetailsService d,JwtService j,UsuarioService u){authenticationManager=a;details=d;jwt=j;usuarios=u;}
 public LoginResponse login(LoginRequest r){authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(r.username().trim(),r.password()));var usuario=details.buscar(r.username());var token=jwt.emitir(details.detalles(usuario));return new LoginResponse(token.valor(),"Bearer",token.expiraEn(),usuarios.respuesta(usuario),usuarios.permisos(usuario));}
}
