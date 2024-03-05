package com.classconnect.classconnectapi.negocio.entidades;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Curtida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno aluno;

    @Column(nullable = false)
    private Date dataCadastro;

    @Column()
    private Date dataAtualizacao;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = new Date(System.currentTimeMillis());
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Material getMaterial() {
      return material;
    }

    public void setMaterial(Material material) {
      this.material = material;
    }

    public Aluno getAluno() {
      return aluno;
    }

    public void setAluno(Aluno aluno) {
      this.aluno = aluno;
    }

    public Date getDataCadastro() {
      return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
      this.dataCadastro = dataCadastro;
    }

    public Date getDataAtualizacao() {
      return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
      this.dataAtualizacao = dataAtualizacao;
    }
}
