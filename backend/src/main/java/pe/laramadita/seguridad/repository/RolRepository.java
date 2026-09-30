package pe.laramadita.seguridad.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.laramadita.seguridad.model.Rol;
public interface RolRepository extends JpaRepository<Rol,Long>{Optional<Rol> findByCodigo(String codigo);}
