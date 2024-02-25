package com.classconnect.classconnectapi.negocio.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class RespostaAtividade extends Material {
  @Column
  private Double nota;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "atividade_id", referencedColumnName = "id")
  private Atividade atividade;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "aluno_id", referencedColumnName = "id")
  private Aluno aluno;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    RespostaAtividade other = (RespostaAtividade) obj;
    if (this.getId() == null) {
      if (other.getId() != null)
        return false;
    } else if (!this.getId().equals(other.getId()))
      return false;
    return true;
  }

  public Double getNota() {
    return nota;
  }

  public void setNota(Double nota) {
    this.nota = nota;
  }

  public Atividade getAtividade() {
    return atividade;
  }

  public void setAtividade(Atividade atividade) {
    this.atividade = atividade;
  }

  public Aluno getAluno() {
    return aluno;
  }

  public void setAluno(Aluno aluno) {
    this.aluno = aluno;
  }
}
