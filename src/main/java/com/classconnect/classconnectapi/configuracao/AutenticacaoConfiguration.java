package com.classconnect.classconnectapi.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.classconnect.classconnectapi.negocio.servicos.AutenticacaoService;

@Configuration
@EnableWebSecurity
public class AutenticacaoConfiguration {
  @Autowired
  private AutenticacaoService autenticacaoService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.POST, "/api/perfis/login", "/api/perfis/cadastrar")
        .permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/perfis/{idPerfil}")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/perfis/{idPerfil}")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/salas").authenticated()
        .requestMatchers(HttpMethod.POST, "/api/salas").hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}").authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/salas/{idSala}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}/alunos")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}/posts")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/salas/{idSala}/posts")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.PUT, "/api/salas/{idSala}/posts/{idPost}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/posts/{idPost}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.GET, "/api/arquivos/{fileName:.+}")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}/posts/{idAtividade}/respostas/{idAluno}")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/salas/{idSala}/posts/{idAtividade}/respostas")
        .hasRole("ALUNO")
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}/posts/{idPost}/comentarios")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/salas/{idSala}/posts/{idPost}/comentarios")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/posts/{idPost}/comentarios/{idComentario}")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/salas/{idSala}/posts/{idPost}/curtidas/quantidade")
        .authenticated()
        .requestMatchers(HttpMethod.PATCH, "/api/salas/{idSala}/posts/{idPost}/curtidas/curtir")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/salas/{iSala}/matriculas/solicitar/{idAluno}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.POST, "/api/salas/{iSala}/matriculas/solicitar")
        .hasRole("ALUNO")
        .requestMatchers(HttpMethod.PATCH, "/api/salas/{idSala}/matriculas/aceitar/{idAluno}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.PATCH, "/api/salas/{idSala}/matriculas/aceitar")
        .hasRole("ALUNO")

        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/matriculas/recusar")
        .hasRole("ALUNO")
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/matriculas/desfazer")
        .hasRole("ALUNO")
        .requestMatchers(HttpMethod.POST, "/api/salas/{iSala}/matriculas/solicitar/{idAluno}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/matriculas/recusar/{idAluno}")
        .hasRole("PROFESSOR")
        .requestMatchers(HttpMethod.DELETE, "/api/salas/{idSala}/matriculas/desfazer/{idAluno}")
        .hasRole("PROFESSOR")
        .anyRequest().authenticated()
      )
    .addFilterBefore(this.autenticacaoService, UsernamePasswordAuthenticationFilter.class)
    .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
