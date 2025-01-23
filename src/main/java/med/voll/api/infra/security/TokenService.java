package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;


@Service
public class TokenService {

//    private static final String ISSUER = "API Voll.med";

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {

//            System.out.println("Chave secreta usada para gerar o token: " + secret);

            var algoritmo = Algorithm.HMAC256(secret.trim());
            var expiracao = dataExpiracao(); // Obtendo a data de expiração

//            System.out.println("Data de expiração do token gerado: " + expiracao);

            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerrar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
//            System.out.println("Token recebido para validação: " + tokenJWT);
//            System.out.println("Chave secreta usada para validar o token: " + secret);

            var algoritmo = Algorithm.HMAC256(secret.trim());
            var verifier = JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build();

            // Decodifica o token para verificar informações
            var decodedJWT = verifier.verify(tokenJWT);

//            System.out.println("Token válido. Subject: " + decodedJWT.getSubject());
//            System.out.println("Data de expiração do token: " + decodedJWT.getExpiresAt());
//            System.out.println("Data atual do servidor (UTC): " + Instant.now());

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            System.err.println("Erro ao validar token: " + exception.getMessage());
            throw new RuntimeException("Token JWT inválido ou expirado: " + tokenJWT, exception);
        }
    }

    private Instant dataExpiracao() {
        var agora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return agora.plusHours(24).toInstant(ZoneOffset.UTC);

        }
    }
