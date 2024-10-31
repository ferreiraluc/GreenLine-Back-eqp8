package app.auth;

import app.repository.AuditRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private static final String SECRET_KEY = "GUSTAVOPASSOUAQUIGUSTAVOPASSOUAQUIGUSTAVOPASSOUAQUI"; // Chave secreta para assinar o JWT
    private static final int TOKEN_EXPIRATION_HOURS = 48; // Expiração do token em horas

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String logar(Usuario login) {
        // Autentica o usuário
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        // Busca o usuário no repositório
        Usuario user = repository.findByEmailUsuario(login.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera o token JWT
        return generateToken(user);
    }

    private String generateToken(Usuario user) {
        // Define as claims (informações adicionais) no token
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("emailUsuario", user.getUsername());
        claims.put("idUsuario", user.getIdUsuario());
        claims.put("role", user.getRole());

        // Cria o token JWT
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * TOKEN_EXPIRATION_HOURS)) // Define a expiração
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Assina o token com a chave secreta e o algoritmo HS256
                .compact();
    }
}
