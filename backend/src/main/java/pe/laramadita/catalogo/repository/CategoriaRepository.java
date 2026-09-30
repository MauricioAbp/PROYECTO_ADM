package pe.laramadita.catalogo.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.laramadita.catalogo.model.Categoria;
public interface CategoriaRepository extends JpaRepository<Categoria,Long>{boolean existsByNombreIgnoreCase(String nombre);boolean existsByNombreIgnoreCaseAndIdNot(String nombre,Long id);Optional<Categoria> findByIdAndActivoTrue(Long id);}
