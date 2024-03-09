package com.classconnect.classconnectapi.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.classconnect.classconnectapi.negocio.entidades.Matricula;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;

public interface MatriculasRepository extends JpaRepository<Matricula, Long>{
  public Optional<Matricula> findBySalaIdAndAlunoId(Long idSala, Long idAluno);

  @Query("SELECT m FROM Matricula m WHERE m.sala.id = :salaId AND m.aluno.id = :alunoId AND (:solicitante IS NULL OR m.solicitante = :solicitante)")
  Optional<Matricula> findBySalaIdAndAlunoIdAndSolicitante(@Param("salaId") Long salaId, @Param("alunoId") Long alunoId, @Param("solicitante") Optional<TipoPerfil> solicitante);
}
