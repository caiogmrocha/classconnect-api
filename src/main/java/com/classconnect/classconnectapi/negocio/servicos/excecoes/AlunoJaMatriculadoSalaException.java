package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class AlunoJaMatriculadoSalaException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idAluno;
  private Long idSala;

  public AlunoJaMatriculadoSalaException(Long idAluno, Long idSala) {
    super("Aluno com id " + idAluno + " já matriculado na sala com id " + idSala);
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
