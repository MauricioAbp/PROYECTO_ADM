package pe.laramadita.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.laramadita.catalogo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>,JpaSpecificationExecutor<Producto>{
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre,Long id);
}
