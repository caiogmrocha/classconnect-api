package com.classconnect.classconnectapi.negocio.entidades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Material {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false)
  private String conteudo;

  @Column(nullable = false)
  private Date dataCadastro;

  @Column()
  private Date dataAtualizacao;

  @Column
  private Date dataArquivacao;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean arquivado;

  @OneToMany(mappedBy = "material")
  private List<Anexo> anexos;

  @OneToMany(mappedBy = "material")
  private List<Comentario> comentarios;

  @OneToMany(mappedBy = "material")
  private List<Curtida> curtidas;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "professor_id", referencedColumnName = "id")
  private Professor professor;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "sala_id", referencedColumnName = "id")
  private Sala sala;

  public Material() {
    this.anexos = new ArrayList<Anexo>();
    this.comentarios = new ArrayList<Comentario>();
    this.curtidas = new ArrayList<Curtida>();
  }

  @PrePersist
  public void prePersist() {
    this.dataCadastro = new Date(System.currentTimeMillis());
    this.arquivado = false;
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

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getConteudo() {
    return conteudo;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
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

  public Date getDataArquivacao() {
    return dataArquivacao;
  }

  public void setDataArquivacao(Date dataArquivacao) {
    this.dataArquivacao = dataArquivacao;
  }

  public boolean isArquivado() {
    return arquivado;
  }

  public void setArquivado(boolean arquivado) {
    this.arquivado = arquivado;
  }

  public List<Anexo> getAnexos() {
    return anexos;
  }

  public void setAnexos(List<Anexo> anexos) {
    this.anexos = anexos;
  }

  public List<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public List<Curtida> getCurtidas() {
    return curtidas;
  }

  public void setCurtidas(List<Curtida> curtidas) {
    this.curtidas = curtidas;
  }

  public Professor getProfessor() {
    return professor;
  }

  public void setProfessor(Professor professor) {
    this.professor = professor;
  }

  public Sala getSala() {
    return sala;
  }

  public void setSala(Sala sala) {
    this.sala = sala;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
    Material other = (Material) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (titulo == null) {
      if (other.titulo != null)
        return false;
    } else if (!titulo.equals(other.titulo))
      return false;
    return true;
  }
}
