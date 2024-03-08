package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Professor extends Perfil {
  @OneToMany(mappedBy = "professor")
  private List<Sala> salas;

  @OneToMany(mappedBy = "professor")
  private List<Material> materiais;

  public List<Sala> getSalas() {
    return salas;
  }

  public void setSalas(List<Sala> salas) {
    this.salas = salas;
  }

  public List<Material> getMateriais() {
    return materiais;
  }

  public void setMateriais(List<Material> materiais) {
    this.materiais = materiais;
  }
}
