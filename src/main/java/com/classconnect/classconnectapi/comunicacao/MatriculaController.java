package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.MatriculasService;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoJaMatriculadoSalaException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SolicitacaoMatriculaNaoExiste;

import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/salas/{idSala}/matriculas")
public class MatriculaController {
    @Autowired
    private MatriculasService matriculaService;

    @PostMapping("/solicitar")
    public ResponseEntity<Map<?,?>> alunoSolicitarMatricula(@PathVariable Long idSala) {
      var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      try {
        this.matriculaService.alunoSolicitarMatricula(idSala, perfil.getId());

        return ResponseEntity.ok(Map.of("mensagem", "Solicitação de matrícula enviada"));
      } catch (AlunoJaMatriculadoSalaException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Aluno já matriculado na sala"));
      } catch (SalaNaoExisteException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Sala não existe"));
      } catch (AlunoNaoExisteException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Aluno não existe"));
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
      }
    }

    @PatchMapping("/aceitar/{idAluno}")
    public ResponseEntity<Map<?,?>> professorAceitarSolicitacao(@PathVariable Long idSala, @PathVariable Long idAluno) {
      var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      try {
        this.matriculaService.professorAceitarSolicitacao(idSala, idAluno, perfil.getId());

        return ResponseEntity.ok(Map.of("mensagem", "Solicitação de matrícula aceita"));
      } catch (AlunoJaMatriculadoSalaException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Aluno já matriculado na sala"));
      } catch (SalaNaoExisteException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Sala não existe"));
      } catch (AlunoNaoExisteException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Aluno não existe"));
      } catch (SalaNaoPertenceProfessorException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Sala não pertence ao professor"));
      } catch (SolicitacaoMatriculaNaoExiste e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Solicitação de matrícula não existe"));
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
      }
    }
}
