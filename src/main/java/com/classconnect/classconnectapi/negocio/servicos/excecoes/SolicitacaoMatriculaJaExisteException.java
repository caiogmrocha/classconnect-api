package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class SolicitacaoMatriculaJaExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idAluno;
  private Long idSala;

  public SolicitacaoMatriculaJaExisteException(Long idAluno, Long idSala) {
    super("Solicitação de matrícula do aluno com id " + idAluno + " na sala com id " + idSala + " já existe");
    this.idAluno = idAluno;
    this.idSala = idSala;
  }

  public Long getIdAluno() {
    return this.idAluno;
  }

  public Long getIdSala() {
    return this.idSala;
  }
}
