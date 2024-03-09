package com.classconnect.classconnectapi.negocio.entidades;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column
    private String caminhoBanner;

    @Column(nullable = false)
    private Date dataCadastro;

    @Column()
    private Date dataAtualizacao;

    @Column
    private Date dataDesativacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professor;

    @OneToMany(mappedBy = "sala")
    private List<Matricula> matriculas;

    @OneToMany(mappedBy = "sala")
    private List<Material> materiais;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean ativa;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = new Date(System.currentTimeMillis());
        this.ativa = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = new Date(System.currentTimeMillis());
    }

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getDescricao() {
      return descricao;
    }

    public void setDescricao(String descricao) {
      this.descricao = descricao;
    }

    public String getCaminhoBanner() {
      return caminhoBanner;
    }

    public void setCaminhoBanner(String caminhoBanner) {
      this.caminhoBanner = caminhoBanner;
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

    public Date getDataDesativacao() {
      return dataDesativacao;
    }

    public void setDataDesativacao(Date dataDesativacao) {
      this.dataDesativacao = dataDesativacao;
    }

    public Professor getProfessor() {
      return professor;
    }

    public void setProfessor(Professor professor) {
      this.professor = professor;
    }

    public List<Matricula> getMatriculas() {
      return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
      this.matriculas = matriculas;
    }

    public List<Material> getMateriais() {
      return materiais;
    }

    public void setMateriais(List<Material> materiais) {
      this.materiais = materiais;
    }

    public boolean isAtiva() {
      return ativa;
    }

    public void setAtiva(boolean ativa) {
      this.ativa = ativa;
    }
}
