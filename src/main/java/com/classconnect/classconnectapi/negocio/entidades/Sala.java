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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sala_aluno", joinColumns = @JoinColumn(name = "sala_id"), inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    private List<Aluno> alunos;

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
}
