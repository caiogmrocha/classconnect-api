package com.classconnect.classconnectapi.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Matricula;

public interface MatriculasRepository extends JpaRepository<Matricula, Long>{
  public Optional<Matricula> findBySalaIdAndAlunoId(Long idSala, Long idAluno);
}
