package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.PostsService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    try {
      this.postsService.publicarPost(publicarPostDTO, idSala, perfil.getId());
    } catch (Exception e) {
      e.printStackTrace();

      return ResponseEntity.badRequest().body(Map.of("mensagem", "Erro ao salvar o arquivo"));
    }

    return ResponseEntity.ok(Map.of("mensagem", "Post publicado com sucesso"));
  }
}
