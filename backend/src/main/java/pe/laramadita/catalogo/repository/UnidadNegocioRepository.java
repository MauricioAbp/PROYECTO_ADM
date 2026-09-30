package pe.laramadita.catalogo.repository;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.laramadita.catalogo.model.UnidadNegocio;
public interface UnidadNegocioRepository extends JpaRepository<UnidadNegocio,Long>{List<UnidadNegocio> findAllByActivoTrueOrderByNombre();Set<UnidadNegocio> findAllByCodigoInAndActivoTrue(Set<String> codigos);}
