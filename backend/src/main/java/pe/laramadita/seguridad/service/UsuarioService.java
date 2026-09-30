package pe.laramadita.seguridad.service;
import java.util.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import pe.laramadita.seguridad.api.dto.*; import pe.laramadita.seguridad.model.*; import pe.laramadita.seguridad.repository.*; import pe.laramadita.shared.error.*;
@Service @Transactional
public class UsuarioService{
 private final UsuarioRepository usuarios; private final RolRepository roles; private final PasswordEncoder encoder;
 public UsuarioService(UsuarioRepository u,RolRepository r,PasswordEncoder e){usuarios=u;roles=r;encoder=e;}
 public UsuarioResponse crear(UsuarioRequest r){String username=normalizar(r.username());if(usuarios.existsByUsernameIgnoreCase(username))throw new ConflictoException("El nombre de usuario ya existe");var u=new Usuario(username,encoder.encode(r.password()),r.nombres().trim(),r.apellidos().trim(),rol(r.rolCodigo()));if(Boolean.FALSE.equals(r.activo()))u.desactivar();return respuesta(usuarios.save(u));}
 @Transactional(readOnly=true) public List<UsuarioResponse> listar(){return usuarios.findAll().stream().map(this::respuesta).toList();}
 @Transactional(readOnly=true) public UsuarioResponse obtener(Long id){return respuesta(buscar(id));}
 public UsuarioResponse actualizar(Long id,UsuarioActualizarRequest r){var u=buscar(id);String username=normalizar(r.username());usuarios.findByUsernameIgnoreCase(username).filter(x->!x.getId().equals(id)).ifPresent(x->{throw new ConflictoException("El nombre de usuario ya existe");});u.actualizar(username,r.nombres().trim(),r.apellidos().trim(),rol(r.rolCodigo()),r.activo());if(r.password()!=null&&!r.password().isBlank())u.cambiarPassword(encoder.encode(r.password()));return respuesta(u);}
 public void desactivar(Long id){buscar(id).desactivar();}
 Usuario buscar(Long id){return usuarios.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Usuario no encontrado"));}
 Rol rol(String codigo){try{return roles.findByCodigo(codigo.trim().toUpperCase(Locale.ROOT)).orElseThrow();}catch(Exception e){throw new ReglaNegocioException("El rol indicado no existe");}}
 String normalizar(String s){return s.trim().toLowerCase(Locale.ROOT);}
 public Set<String> permisos(Usuario u){var r=new TreeSet<String>();u.getRol().getPermisos().forEach(p->r.add(p.getCodigo()));return r;}
 public UsuarioResponse respuesta(Usuario u){return new UsuarioResponse(u.getId(),u.getUsername(),u.getNombres(),u.getApellidos(),u.getRol().getCodigo(),u.getRol().getNombre(),u.isActivo(),permisos(u),u.getCreadoEn(),u.getActualizadoEn());}
}
