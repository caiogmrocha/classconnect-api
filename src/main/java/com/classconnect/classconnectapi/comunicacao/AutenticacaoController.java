package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.AutenticacaoCadastrarDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.requests.AutenticacaoLoginDTO;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.AutenticacaoService;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ExistePerfilComEmailException;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/perfis")
public class AutenticacaoController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AutenticacaoService autenticacaoService;

  @PostMapping("/login")
  public ResponseEntity<Map<?,?>> login(@RequestBody @Valid AutenticacaoLoginDTO body) {
    var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.senha());
    var auth = this.authenticationManager.authenticate(authToken);

    var token = this.autenticacaoService.gerarToken((Perfil) auth.getPrincipal());

    return ResponseEntity.ok().body(Map.of("token", token));
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody @Valid AutenticacaoCadastrarDTO autenticacaoCadastrarDTO) {
    try {
      this.autenticacaoService.cadastrar(autenticacaoCadastrarDTO);

      return ResponseEntity.ok(Map.of("mensagem", "Perfil cadastrado com sucesso"));
    } catch(ExistePerfilComEmailException e) {
      return ResponseEntity.badRequest().body(
        Map.of("mensagem", "Já existe um perfil cadastrado com o e-mail informado.")
      );
    } catch (Exception e) {
      e.printStackTrace();

      return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
    }
  }

  @PutMapping("/{id}")
  public String editar(@PathVariable String id, @RequestBody String entity) {
    System.out.println("Editando perfil " + id);

    return entity;
  }
}
