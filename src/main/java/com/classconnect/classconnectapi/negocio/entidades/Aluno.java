package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno extends Perfil {
    @OneToMany(mappedBy = "aluno")
    private List<RespostaAtividade> respostasAtividades;

    @OneToMany(mappedBy = "aluno")
    private List<Curtida> curtidas;

    @ManyToMany(mappedBy = "alunos")
    private List<Sala> salas;
}
