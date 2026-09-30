package pe.laramadita.seguridad.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.laramadita.seguridad.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{Optional<Usuario> findByUsernameIgnoreCase(String username); boolean existsByUsernameIgnoreCase(String username);}
