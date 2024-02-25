package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class SalaNaoExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idSala;

  public SalaNaoExisteException(Long idSala) {
    super("Sala com id " + idSala + " não existe");
    this.idSala = idSala;
  }

  public Long idSala() {
    return this.idSala;
  }
}
