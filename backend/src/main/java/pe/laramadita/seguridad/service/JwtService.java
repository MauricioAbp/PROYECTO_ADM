package pe.laramadita.seguridad.service;
import java.time.*; import java.util.*;
import org.springframework.beans.factory.annotation.Value; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.security.oauth2.jose.jws.MacAlgorithm; import org.springframework.security.oauth2.jwt.*; import org.springframework.stereotype.Service;
@Service
public class JwtService{
 private final JwtEncoder encoder; private final long minutos;
 public JwtService(JwtEncoder e,@Value("${app.security.token-minutes}") long m){encoder=e;minutos=m;}
 public Token emitir(UserDetails u){var ahora=Instant.now();var expira=ahora.plus(Duration.ofMinutes(minutos));var permisos=u.getAuthorities().stream().map(a->a.getAuthority()).toList();var claims=JwtClaimsSet.builder().issuer("la-ramadita").issuedAt(ahora).expiresAt(expira).subject(u.getUsername()).claim("authorities",permisos).build();var header=JwsHeader.with(MacAlgorithm.HS256).build();return new Token(encoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue(),expira);}
 public record Token(String valor,Instant expiraEn){}
}
