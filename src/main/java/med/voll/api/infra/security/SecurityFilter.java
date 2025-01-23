package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);
//        System.out.println("Token recebido no filtro: " + tokenJWT);

        if (tokenJWT != null) {
            try {
                var subject = tokenService.getSubject(tokenJWT); // Valida o token e retorna o "subject" (login do usuário)
//                System.out.println("Usuário autenticado no filtro: " + subject);

                var usuario = repository.findByLogin(subject);  // Busca o usuário pelo login
                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities() // Adiciona as autoridades do usuário
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication); // Configura o contexto de autenticação
                }
            } catch (Exception e) {
                System.err.println("Erro ao validar token: " + e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Caso o token seja inválido
                return;
            }
        }

        filterChain.doFilter(request, response); // Continua o fluxo
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}