package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno extends Perfil {
  @OneToMany(mappedBy = "aluno")
  private List<RespostaAtividade> respostasAtividades;

  @OneToMany(mappedBy = "aluno")
  private List<Curtida> curtidas;

  @ManyToMany(mappedBy = "alunos")
  private List<Sala> salas;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<RespostaAtividade> getRespostasAtividades() {
    return respostasAtividades;
  }

  public void setRespostasAtividades(List<RespostaAtividade> respostasAtividades) {
    this.respostasAtividades = respostasAtividades;
  }

  public List<Curtida> getCurtidas() {
    return curtidas;
  }

  public void setCurtidas(List<Curtida> curtidas) {
    this.curtidas = curtidas;
  }

  public List<Sala> getSalas() {
    return salas;
  }

  public void setSalas(List<Sala> salas) {
    this.salas = salas;
  }
}
