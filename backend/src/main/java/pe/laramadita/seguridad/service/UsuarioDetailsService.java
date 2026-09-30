package pe.laramadita.seguridad.service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import pe.laramadita.seguridad.model.Usuario;
import pe.laramadita.seguridad.repository.UsuarioRepository;
import java.util.stream.Stream;
@Service
public class UsuarioDetailsService implements UserDetailsService{
 private final UsuarioRepository repository; public UsuarioDetailsService(UsuarioRepository r){repository=r;}
 @Override public UserDetails loadUserByUsername(String username){return detalles(buscar(username));}
 public Usuario buscar(String username){return repository.findByUsernameIgnoreCase(username.trim()).orElseThrow(()->new UsernameNotFoundException("Credenciales inválidas"));}
 public UserDetails detalles(Usuario u){var authorities=Stream.concat(Stream.of(new SimpleGrantedAuthority("ROLE_"+u.getRol().getCodigo())),u.getRol().getPermisos().stream().map(p->new SimpleGrantedAuthority(p.getCodigo()))).toList();return User.withUsername(u.getUsername()).password(u.getPasswordHash()).authorities(authorities).disabled(!u.isActivo()).build();}
}
