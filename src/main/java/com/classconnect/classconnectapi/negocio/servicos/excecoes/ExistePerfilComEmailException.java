package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class ExistePerfilComEmailException extends Exception {
  private static final long serialVersionUID = 1L;
  public String email;

  public ExistePerfilComEmailException(String email) {
    super("Já existe um perfil cadastrado com o e-mail informado.");
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
