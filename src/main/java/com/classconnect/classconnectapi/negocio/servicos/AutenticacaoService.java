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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.classconnect.classconnectapi.comunicacao.dtos.requests.AutenticacaoCadastrarDTO;
import com.classconnect.classconnectapi.dados.AlunosRepository;
import com.classconnect.classconnectapi.dados.PerfilRepository;
import com.classconnect.classconnectapi.dados.ProfessoresRepository;
import com.classconnect.classconnectapi.negocio.entidades.Aluno;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.entidades.Professor;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ExistePerfilComEmailException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Caio Guilherme Moreira da Rocha
 *
 * Este serviço é responsável por lidar com as operações de autenticação.
 */
@Service
public class AutenticacaoService extends OncePerRequestFilter implements UserDetailsService {
  @Value("${api.security.jwt.secret}")
  private String secret;

  @Autowired
  private PerfilRepository perfilRepository;

  @Autowired
  private ProfessoresRepository professorRepository;

  @Autowired
  private AlunosRepository alunosRepository;

  public void cadastrar(AutenticacaoCadastrarDTO autenticacaoCadastrarDTO) throws ExistePerfilComEmailException {
    if (this.perfilRepository.findByEmail(autenticacaoCadastrarDTO.email()) != null) {
      throw new ExistePerfilComEmailException(autenticacaoCadastrarDTO.email());
    }

    Perfil perfil = autenticacaoCadastrarDTO.tipoPerfil().equals("PROFESSOR") ? new Professor() : new Aluno();

    perfil.setNome(autenticacaoCadastrarDTO.nome());
    perfil.setEmail(autenticacaoCadastrarDTO.email());

    var senhaEncriptada = new BCryptPasswordEncoder().encode(autenticacaoCadastrarDTO.senha());
    perfil.setSenha(senhaEncriptada);

    perfil.setTipoPerfil(TipoPerfil.valueOf(autenticacaoCadastrarDTO.tipoPerfil()));

    perfil = this.perfilRepository.save(perfil);

    if (perfil.getTipoPerfil() == TipoPerfil.PROFESSOR) {
      this.professorRepository.save((Professor) perfil);
    } else {
      this.alunosRepository.save((Aluno) perfil);
    }
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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recuperarToken(request);

    if (token != null && !token.isBlank()) {
      var email = this.validarToken(token);

      UserDetails perfil = this.perfilRepository.findByEmail(email);

      var authentication = new UsernamePasswordAuthenticationToken(perfil, null, perfil.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.perfilRepository.findByEmail(username);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authorization = request.getHeader("Authorization");

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      return "";
    }

    return authorization.replace("Bearer ", "");
  }

  private Instant gerarTempoExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
