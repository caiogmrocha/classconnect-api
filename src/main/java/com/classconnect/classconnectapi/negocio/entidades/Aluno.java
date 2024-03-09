package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno extends Perfil {
  @OneToMany(mappedBy = "aluno")
  private List<RespostaAtividade> respostasAtividades;

  @OneToMany(mappedBy = "aluno")
  private List<Curtida> curtidas;

  @OneToMany(mappedBy = "aluno")
  private List<Matricula> matriculas;

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

  public List<Matricula> getMatriculas() {
    return matriculas;
  }

  public void setMatriculas(List<Matricula> matriculas) {
    this.matriculas = matriculas;
  }
}
