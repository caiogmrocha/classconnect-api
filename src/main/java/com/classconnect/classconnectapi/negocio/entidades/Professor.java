package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Professor extends Perfil {
  @Id
  private Long id;

  @OneToMany(mappedBy = "professor")
  private List<Sala> salas;

  @OneToMany(mappedBy = "professor")
  private List<Material> materiais;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
