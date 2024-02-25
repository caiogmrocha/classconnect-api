package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class AlunoNaoPertenceSalaException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idSala;
  private Long idAluno;

  public AlunoNaoPertenceSalaException(Long idSala, Long idAluno) {
    super("Aluno com id " + idAluno + " não pertence à sala com id " + idSala);
    this.idSala = idSala;
    this.idAluno = idAluno;
  }

  public Long idSala() {
    return this.idSala;
  }

  public Long idAluno() {
    return this.idAluno;
  }
}
