package pe.laramadita.seguridad.config;
import org.springframework.beans.factory.annotation.Value; import org.springframework.boot.ApplicationArguments; import org.springframework.boot.ApplicationRunner; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional;
import pe.laramadita.seguridad.model.Usuario; import pe.laramadita.seguridad.repository.*;
@Component
public class BootstrapOwner implements ApplicationRunner{
 private final UsuarioRepository usuarios; private final RolRepository roles; private final PasswordEncoder encoder; private final String username,password;
 public BootstrapOwner(UsuarioRepository u,RolRepository r,PasswordEncoder e,@Value("${app.security.bootstrap-owner.username}") String name,@Value("${app.security.bootstrap-owner.password}") String pass){usuarios=u;roles=r;encoder=e;username=name;password=pass;}
 @Override @Transactional public void run(ApplicationArguments args){if(usuarios.count()==0){var rol=roles.findByCodigo("DUENO").orElseThrow();usuarios.save(new Usuario(username.trim().toLowerCase(),encoder.encode(password),"Dueño","La Ramadita",rol));}}
}
