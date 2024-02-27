package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class ProfessorNaoExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long id;

  public ProfessorNaoExisteException(Long id) {
    super("Não existe um professor cadastrado com o ID informado.");
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
