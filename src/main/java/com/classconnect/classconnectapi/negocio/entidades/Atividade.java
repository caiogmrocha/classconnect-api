package com.classconnect.classconnectapi.negocio.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Atividade extends Material {
  @Column
  private LocalDateTime dataEntrega;

  @OneToOne(mappedBy = "atividade")
  private RespostaAtividade resposta;

  public LocalDateTime getDataEntrega() {
    return dataEntrega;
  }

  public void setDataEntrega(LocalDateTime dataEntrega) {
    this.dataEntrega = dataEntrega;
  }
}
