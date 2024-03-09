package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class AlunoNaoExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idAluno;


  public AlunoNaoExisteException(Long idAluno) {
    super("Aluno com id " + idAluno + " não existe");
    this.idAluno = idAluno;
  }

  public Long getIdAluno() {
    return this.idAluno;
  }
}
