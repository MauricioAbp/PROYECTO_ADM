package pe.laramadita.shared.error;
import java.net.URI;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
@RestControllerAdvice
public class ApiExceptionHandler{
 @ExceptionHandler(RecursoNoEncontradoException.class) ProblemDetail noEncontrado(RecursoNoEncontradoException e){return problema(HttpStatus.NOT_FOUND,"Recurso no encontrado",e.getMessage());}
 @ExceptionHandler(ConflictoException.class) ProblemDetail conflicto(ConflictoException e){return problema(HttpStatus.CONFLICT,"Conflicto",e.getMessage());}
 @ExceptionHandler(ReglaNegocioException.class) ProblemDetail negocio(ReglaNegocioException e){return problema(HttpStatus.UNPROCESSABLE_ENTITY,"Regla de negocio inválida",e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ProblemDetail validacion(MethodArgumentNotValidException e){var p=problema(HttpStatus.BAD_REQUEST,"Solicitud inválida","Uno o más campos no cumplen las validaciones");Map<String,String> errores=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->errores.putIfAbsent(x.getField(),x.getDefaultMessage()));p.setProperty("errores",errores);return p;}
 @ExceptionHandler(NoResourceFoundException.class) ProblemDetail ruta(NoResourceFoundException e){return problema(HttpStatus.NOT_FOUND,"Ruta no encontrada","El recurso solicitado no existe");}
 private ProblemDetail problema(HttpStatus status,String titulo,String detalle){var p=ProblemDetail.forStatusAndDetail(status,detalle);p.setTitle(titulo);p.setType(URI.create("about:blank"));return p;}
}
