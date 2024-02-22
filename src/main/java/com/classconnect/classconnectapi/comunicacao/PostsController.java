package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.PostsService;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class PostsController {
  @Autowired
  private PostsService postsService;

  @PostMapping("/api/salas/{idSala}/posts")
  public ResponseEntity<Map<?,?>> publicarPost(
    @Valid @ModelAttribute PublicarPostDTO publicarPostDTO,
    @PathParam("idSala") Long idSala
  ) {
    try {
      var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      this.postsService.publicarPost(publicarPostDTO, idSala, perfil.getId());

      return ResponseEntity.ok(Map.of("mensagem", "Post publicado com sucesso"));
    } catch(ProfessorNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Professor não existe"));
    } catch (Exception e) {
      e.printStackTrace();

      return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
    }
  }
}
