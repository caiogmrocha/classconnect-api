package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.requests.ResponderAtividadeDTO;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.PostsService;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoNaoPertenceSalaException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.PostNaoAtividadeException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.PostNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.RespostaAtividadeJaExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/salas/{idSala}/posts")
public class PostsController {
  @Autowired
  private PostsService postsService;

  @PostMapping()
  public ResponseEntity<Map<?, ?>> publicarPost(
      @Valid @ModelAttribute PublicarPostDTO publicarPostDTO,
      @PathVariable("idSala") Long idSala) {
    try {
      var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      this.postsService.publicarPost(publicarPostDTO, idSala, perfil.getId());

      return ResponseEntity.ok(Map.of("mensagem", "Post publicado com sucesso"));
    } catch (ProfessorNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Professor não existe"));
    } catch (SalaNaoPertenceProfessorException e) {
      return ResponseEntity.badRequest()
          .body(Map.of("mensagem", "Sala não encontrada ou não pertence ao professor logado"));
    } catch (Exception e) {
      e.printStackTrace();

      return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
    }
  }

  @PostMapping("/{idAtividade}/respostas")
  public ResponseEntity<Map<?, ?>> responderAtividade(
      @Valid @ModelAttribute ResponderAtividadeDTO responderAtividadeDTO,
      @PathVariable("idSala") Long idSala,
      @PathVariable("idAtividade") Long idAtividade) {
    try {
      var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      this.postsService.responderAtividade(responderAtividadeDTO, idSala, idAtividade, perfil.getId());

      return ResponseEntity.ok(Map.of("mensagem", "Resposta publicada com sucesso"));
    } catch (SalaNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Sala não existe"));
    } catch (PostNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Post não existe"));
    } catch (AlunoNaoPertenceSalaException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Aluno não pertence à sala"));
    } catch (PostNaoAtividadeException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Post não é uma atividade"));
    } catch (RespostaAtividadeJaExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Resposta para a atividade já existe"));
    }
  }

  @GetMapping()
  public ResponseEntity<Map<?, ?>[]> listarPosts(@PathVariable Long idSala) throws SalaNaoExisteException {
    try {
      var posts = this.postsService.listarPosts(idSala);

      if (posts.isEmpty()) {
        return ResponseEntity.ok(new Map[] {});
      }

      var postsDTO = posts.stream().map(post -> {
        return Map.of(
          "id", post.getId(),
          "titulo", post.getTitulo(),
            "conteudo", post.getConteudo(),
          "professor", Map.of(
            "id", post.getProfessor().getId(),
            "nome", post.getProfessor().getNome()
          ),
          "dataCadastro", post.getDataCadastro(),
          "dataAtualizacao", post.getDataAtualizacao(),
          "comentarios", post.getComentarios().stream().map(comentario -> Map.of(
            "id", comentario.getId(),
            "conteudo", comentario.getConteudo()
          )).toArray(Map[]::new),
          "curtidas", post.getCurtidas().size()
        );
      }).toArray(Map[]::new);

      return ResponseEntity.ok(postsDTO);
    } catch (SalaNaoExisteException e) {
      return ResponseEntity.badRequest().body(new Map[] {Map.of("mensagem", "Sala não existe")});
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(new Map[] {Map.of("mensagem", "Internal server error")});
    }
  }

  @GetMapping("/{idPost}")
  public ResponseEntity<Map<?, ?>> detalharPost(@PathVariable Long idSala, @PathVariable Long idPost) {
    try {
      var post = this.postsService.detalharPost(idSala, idPost);

      var postDTO = Map.of(
        "id", post.getId(),
        "titulo", post.getTitulo(),
        "conteudo", post.getConteudo(),
        "dataCadastro", post.getDataCadastro(),
        "comentarios", post.getComentarios().stream().map(comentario -> Map.of(
          "id", comentario.getId(),
          "conteudo", comentario.getConteudo()
        )).toArray(Map[]::new),
        "curtidas", post.getCurtidas().size(),
        "anexos", post.getAnexos().stream().map(anexo -> Map.of(
          "caminho", anexo.getCaminho(),
          "mimetype", anexo.getMimetype()
        )).toArray(Map[]::new)
      );

      return ResponseEntity.ok(postDTO);
    } catch (SalaNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Sala não existe"));
    } catch (PostNaoExisteException e) {
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Post não existe"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
    }
  }
}
