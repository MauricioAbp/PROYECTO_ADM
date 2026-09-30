package pe.laramadita.catalogo.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.laramadita.catalogo.model.AreaPreparacion;
public interface AreaPreparacionRepository extends JpaRepository<AreaPreparacion,Long>{List<AreaPreparacion> findAllByActivoTrueOrderByNombre();Optional<AreaPreparacion> findByIdAndActivoTrue(Long id);}
