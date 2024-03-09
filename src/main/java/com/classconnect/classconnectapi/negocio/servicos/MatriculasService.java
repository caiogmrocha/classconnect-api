package com.classconnect.classconnectapi.negocio.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classconnect.classconnectapi.dados.AlunosRepository;
import com.classconnect.classconnectapi.dados.MatriculasRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Matricula;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoJaMatriculadoSalaException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SolicitacaoMatriculaJaExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SolicitacaoMatriculaNaoExiste;

@Service
public class MatriculasService {
  @Autowired
  private MatriculasRepository matriculasRepository;

  @Autowired
  private SalasRepository salasRepository;

  @Autowired
  private AlunosRepository alunosRepository;

  public void alunoSolicitarMatricula(Long idSala, Long idAluno) throws SalaNaoExisteException, AlunoNaoExisteException, AlunoJaMatriculadoSalaException {
    var sala = this.salasRepository.findById(idSala).orElseThrow(() -> new SalaNaoExisteException(idSala));

    var aluno = this.alunosRepository.findById(idAluno).orElseThrow(() -> new AlunoNaoExisteException(idAluno));

    var matriculaAnterior = this.matriculasRepository.findBySalaIdAndAlunoId(idSala, idAluno);

    if (matriculaAnterior.isPresent()) {
      throw new AlunoJaMatriculadoSalaException(idAluno, idSala);
    }

    var matricula = new Matricula();

    matricula.setSolicitante(TipoPerfil.ALUNO);
    matricula.setAluno(aluno);
    matricula.setSala(sala);

    sala.getMatriculas().add(matricula);

    this.matriculasRepository.save(matricula);
  }

  public void professorAceitarSolicitacao(Long idSala, Long idAluno, Long idProfessor) throws SalaNaoExisteException, AlunoNaoExisteException, SalaNaoPertenceProfessorException, SolicitacaoMatriculaNaoExiste, AlunoJaMatriculadoSalaException {
    var sala = this.salasRepository.findById(idSala).orElseThrow(() -> new SalaNaoExisteException(idSala));

    this.alunosRepository.findById(idAluno).orElseThrow(() -> new AlunoNaoExisteException(idAluno));

    var professor = sala.getProfessor();

    if (professor.getId() != idProfessor) {
      throw new SalaNaoPertenceProfessorException(idSala, idProfessor);
    }

    var matricula = this.matriculasRepository.findBySalaIdAndAlunoIdAndSolicitante(idSala, idAluno, Optional.empty()).orElseThrow(() -> new SolicitacaoMatriculaNaoExiste(idAluno, idSala));

    if (matricula.getDataConfirmacao() != null) {
      throw new AlunoJaMatriculadoSalaException(idAluno, idSala);
    }

    matricula.setDataConfirmacao(java.time.LocalDateTime.now());

    sala.getMatriculas().add(matricula);

    this.salasRepository.save(sala);
  }

  public void professorSolicitarMatricula(Long idSala, Long idAluno, Long idProfessor) throws SalaNaoExisteException, AlunoNaoExisteException, AlunoJaMatriculadoSalaException, SalaNaoPertenceProfessorException, SolicitacaoMatriculaJaExisteException, SolicitacaoMatriculaNaoExiste {
    var sala = this.salasRepository.findById(idSala).orElseThrow(() -> new SalaNaoExisteException(idSala));

    var professor = sala.getProfessor();

    if (professor.getId() != idProfessor) {
      throw new SalaNaoPertenceProfessorException(idSala, idProfessor);
    }

    var aluno = this.alunosRepository.findById(idAluno).orElseThrow(() -> new AlunoNaoExisteException(idAluno));

    var matriculaAnterior = this.matriculasRepository.findBySalaIdAndAlunoId(idSala, idAluno);

    if (matriculaAnterior.isPresent() && matriculaAnterior.get().getDataConfirmacao() != null) {
      throw new AlunoJaMatriculadoSalaException(idAluno, idSala);
    }

    var solicitacaoMatriculaAnterior = this.matriculasRepository.findBySalaIdAndAlunoIdAndSolicitante(idSala, idAluno, Optional.of(TipoPerfil.ALUNO));

    if (solicitacaoMatriculaAnterior.isPresent()) {
      this.professorAceitarSolicitacao(idSala, idAluno, idProfessor);

      return;
    }

    solicitacaoMatriculaAnterior = this.matriculasRepository.findBySalaIdAndAlunoIdAndSolicitante(idSala, idAluno, Optional.of(TipoPerfil.PROFESSOR));

    if (solicitacaoMatriculaAnterior.isPresent()) {
      throw new SolicitacaoMatriculaJaExisteException(idAluno, idSala);
    }

    var matricula = new Matricula();

    matricula.setSolicitante(TipoPerfil.PROFESSOR);
    matricula.setAluno(aluno);
    matricula.setSala(sala);

    sala.getMatriculas().add(matricula);

    this.matriculasRepository.save(matricula);
  }
}
