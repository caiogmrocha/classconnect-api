package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class RespostaAtividadeJaExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idAtividade;
  private Long idAluno;

  public RespostaAtividadeJaExisteException(Long idAtividade, Long idAluno) {
    super("Resposta da atividade com id " + idAtividade + " já existe para o aluno com id " + idAluno);
    this.idAtividade = idAtividade;
    this.idAluno = idAluno;
  }

  public Long idAtividade() {
    return this.idAtividade;
  }

  public Long idAluno() {
    return this.idAluno;
  }
}
