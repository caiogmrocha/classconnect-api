package com.classconnect.classconnectapi.negocio.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classconnect.classconnectapi.dados.AlunosRepository;
import com.classconnect.classconnectapi.dados.MatriculasRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Matricula;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoJaMatriculadoSalaException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoExisteException;

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

    if (this.salasRepository.countByAlunoIdAndId(idAluno, idSala) > 0) {
      throw new AlunoJaMatriculadoSalaException(idAluno, idSala);
    }

    var matricula = new Matricula();

    matricula.setAluno(aluno);
    matricula.setSala(sala);

    sala.getMatriculas().add(matricula);

    this.matriculasRepository.save(matricula);
  }
}
