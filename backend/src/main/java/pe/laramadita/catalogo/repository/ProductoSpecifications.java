package pe.laramadita.catalogo.repository;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import pe.laramadita.catalogo.model.Producto;

public final class ProductoSpecifications {
    private ProductoSpecifications(){}
    public static Specification<Producto> filtros(String buscar,Long categoriaId,String unidad,Boolean disponible,Boolean activo){
        return Specification.where(nombreContiene(buscar)).and(categoria(categoriaId)).and(unidad(unidad)).and(disponible(disponible)).and(activo(activo));
    }
    private static Specification<Producto> nombreContiene(String valor){return (r,q,c)->valor==null||valor.isBlank()?c.conjunction():c.like(c.lower(r.get("nombre")),"%"+valor.trim().toLowerCase()+"%");}
    private static Specification<Producto> categoria(Long id){return (r,q,c)->id==null?c.conjunction():c.equal(r.get("categoria").get("id"),id);}
    private static Specification<Producto> unidad(String codigo){return (r,q,c)->{if(codigo==null||codigo.isBlank())return c.conjunction();q.distinct(true);return c.equal(r.join("unidadesNegocio",JoinType.INNER).get("codigo"),codigo.toUpperCase());};}
    private static Specification<Producto> disponible(Boolean valor){return (r,q,c)->valor==null?c.conjunction():c.equal(r.get("disponible"),valor);}
    private static Specification<Producto> activo(Boolean valor){return (r,q,c)->valor==null?c.conjunction():c.equal(r.get("activo"),valor);}
}
