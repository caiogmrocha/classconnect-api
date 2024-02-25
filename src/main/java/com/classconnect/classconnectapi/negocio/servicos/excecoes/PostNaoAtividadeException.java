package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class PostNaoAtividadeException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idPost;

  public PostNaoAtividadeException(Long idPost) {
    super("Post com id " + idPost + " não é uma atividade");
    this.idPost = idPost;
  }

  public Long idPost() {
    return this.idPost;
  }
}
