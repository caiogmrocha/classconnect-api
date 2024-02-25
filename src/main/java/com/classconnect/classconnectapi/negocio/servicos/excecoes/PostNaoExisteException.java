package com.classconnect.classconnectapi.negocio.servicos.excecoes;

public class PostNaoExisteException extends Exception {
  private static final long serialVersionUID = 1L;
  private Long idPost;

  public PostNaoExisteException(Long idPost) {
    super("Post com id " + idPost + " não existe");
    this.idPost = idPost;
  }

  public Long idPost() {
    return this.idPost;
  }
}
