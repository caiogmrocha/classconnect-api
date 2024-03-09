package com.classconnect.classconnectapi.negocio.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Matricula {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "aluno_id", referencedColumnName = "id")
  private Aluno aluno;

  @ManyToOne
  @JoinColumn(name = "sala_id", referencedColumnName = "id")
  private Sala sala;

  @Column(nullable = true)
  private LocalDateTime dataConfirmacao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Aluno getAluno() {
    return aluno;
  }

  public void setAluno(Aluno aluno) {
    this.aluno = aluno;
  }

  public Sala getSala() {
    return sala;
  }

  public void setSala(Sala sala) {
    this.sala = sala;
  }

  public LocalDateTime getDataConfirmacao() {
    return dataConfirmacao;
  }

  public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
    this.dataConfirmacao = dataConfirmacao;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
    result = prime * result + ((sala == null) ? 0 : sala.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Matricula other = (Matricula) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (aluno == null) {
      if (other.aluno != null)
        return false;
    } else if (!aluno.equals(other.aluno))
      return false;
    if (sala == null) {
      if (other.sala != null)
        return false;
    } else if (!sala.equals(other.sala))
      return false;
    return true;
  }
}
