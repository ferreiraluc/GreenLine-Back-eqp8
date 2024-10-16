package app.auth;

import app.auditing.Audit;
import app.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import app.config.JwtServiceGenerator;

@Service
public class LoginService {

    private AuditRepository auditRepository;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtServiceGenerator jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String logar(Usuario login) {
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
        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }
}