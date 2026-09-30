package pe.laramadita.catalogo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import pe.laramadita.catalogo.api.dto.CategoriaRequest;
import pe.laramadita.catalogo.model.Categoria;
import pe.laramadita.catalogo.repository.CategoriaRepository;
import pe.laramadita.shared.error.ConflictoException;

class CategoriaServiceTest {
    private final CategoriaRepository repository=mock(CategoriaRepository.class);
    private final CategoriaService service=new CategoriaService(repository);
    @Test void rechazaNombreDuplicado(){when(repository.existsByNombreIgnoreCase("Bebidas")).thenReturn(true);assertThatThrownBy(()->service.crear(new CategoriaRequest(" Bebidas ",null,null))).isInstanceOf(ConflictoException.class);}
    @Test void desactivaSinEliminar(){var categoria=new Categoria("Bebidas",null);when(repository.findById(1L)).thenReturn(Optional.of(categoria));service.desactivar(1L);assertThat(categoria.isActivo()).isFalse();verify(repository,never()).delete(any());}
}
