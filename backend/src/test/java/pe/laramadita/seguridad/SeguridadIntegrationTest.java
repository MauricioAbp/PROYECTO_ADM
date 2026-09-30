package pe.laramadita.seguridad;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.test.context.ActiveProfiles; import org.springframework.test.web.servlet.MockMvc;
import pe.laramadita.seguridad.model.Usuario; import pe.laramadita.seguridad.repository.*;

@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("local")
class SeguridadIntegrationTest{
 @Autowired MockMvc mvc; @Autowired UsuarioRepository usuarios; @Autowired RolRepository roles; @Autowired PasswordEncoder encoder; @Autowired ObjectMapper json;
 @BeforeEach void preparar(){crearSiNoExiste("mesero.test",true);crearSiNoExiste("inactivo.test",false);}
 void crearSiNoExiste(String username,boolean activo){if(usuarios.findByUsernameIgnoreCase(username).isEmpty()){var u=new Usuario(username,encoder.encode("Clave2026!"),"Usuario","Prueba",roles.findByCodigo("MESERO").orElseThrow());if(!activo)u.desactivar();usuarios.save(u);}}
 @Test void iniciaSesionYNoExponeHash()throws Exception{mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"mesero.test\",\"password\":\"Clave2026!\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.token").isNotEmpty()).andExpect(jsonPath("$.usuario.passwordHash").doesNotExist()).andExpect(jsonPath("$.permisos").isArray());}
 @Test void rechazaUsuarioDesactivado()throws Exception{mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"inactivo.test\",\"password\":\"Clave2026!\"}")).andExpect(status().isUnauthorized());}
 @Test void meseroPuedeConsultarPeroNoGestionarCatalogo()throws Exception{var result=mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"mesero.test\",\"password\":\"Clave2026!\"}")).andReturn();String token=json.readTree(result.getResponse().getContentAsString()).get("token").asText();mvc.perform(get("/api/v1/productos").header("Authorization","Bearer "+token)).andExpect(status().isOk());mvc.perform(post("/api/v1/categorias").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"nombre\":\"Categoría bloqueada\"}")).andExpect(status().isForbidden());}
}
