package com.classconnect.classconnectapi.negocio.servicos;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.classconnect.classconnectapi.dados.PerfilRepository;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AutenticacaoService extends OncePerRequestFilter implements UserDetailsService {
    @Value("${api.security.jwt.secret}")
    private String secret;

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.perfilRepository.findByEmail(username);
    }

    public String gerarToken(Perfil perfil) {
        try {
            var algoritmo = Algorithm.HMAC256(this.secret);
            var token = JWT
                    .create()
                    .withIssuer("classconnect")
                    .withSubject(perfil.getEmail())
                    .withExpiresAt(this.gerarTempoExpiracao())
                    .sign(algoritmo);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validarToken(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(this.secret);

            return JWT
                    .require(algoritmo)
                    .withIssuer("classconnect")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant gerarTempoExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recuperarToken(request);

        if (token != null && !token.isBlank()) {
            var email = this.validarToken(token);

            UserDetails perfil = this.perfilRepository.findByEmail(email);

            var authentication = new UsernamePasswordAuthenticationToken(perfil, null, perfil.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return "";
        }

        return authorization.replace("Bearer", "");
    }
}
