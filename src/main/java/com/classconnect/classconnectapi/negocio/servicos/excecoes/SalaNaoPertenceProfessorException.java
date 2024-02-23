package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class SalaNaoPertenceProfessorException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idSala;
  private Long idProfessor;

  public SalaNaoPertenceProfessorException(Long idSala, Long idProfessor) {
    super(String.format("A sala com id %d não pertence ao professor com id %d", idSala, idProfessor));
    this.idSala = idSala;
    this.idProfessor = idProfessor;
  }

  public Long getIdSala() {
    return idSala;
  }

  public Long getIdProfessor() {
    return idProfessor;
  }
}
