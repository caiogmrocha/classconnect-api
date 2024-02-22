package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Aluno;

public interface AlunosRepository extends JpaRepository<Aluno, Long> {}
