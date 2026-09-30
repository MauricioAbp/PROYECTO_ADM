package pe.laramadita.seguridad.config;
import jakarta.servlet.*; import jakarta.servlet.http.*; import java.io.IOException;
import org.springframework.http.MediaType; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.oauth2.jwt.*; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import pe.laramadita.seguridad.service.UsuarioDetailsService;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
 private final JwtDecoder decoder; private final UsuarioDetailsService usuarios;
 public JwtAuthenticationFilter(JwtDecoder d,UsuarioDetailsService u){decoder=d;usuarios=u;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer "))try{var jwt=decoder.decode(h.substring(7));var user=usuarios.buscar(jwt.getSubject());UserDetails details=usuarios.detalles(user);if(!details.isEnabled())throw new JwtException("Usuario inactivo");SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities()));}catch(Exception e){SecurityContextHolder.clearContext();res.setStatus(401);res.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);res.getWriter().write("{\"title\":\"No autenticado\",\"status\":401,\"detail\":\"El token no es válido o el usuario está inactivo\"}");return;}
  chain.doFilter(req,res);
 }
}
