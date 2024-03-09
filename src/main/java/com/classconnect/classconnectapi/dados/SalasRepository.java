package com.classconnect.classconnectapi.dados;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.classconnect.classconnectapi.negocio.entidades.Sala;

public interface SalasRepository extends JpaRepository<Sala, Long>{
  public Optional<Sala> findByProfessorIdAndId(Long idProfessor, Long idSala);

  public List<Sala> findByProfessorId(Long idProfessor);

  @Query("SELECT s FROM Sala s JOIN s.matriculas m WHERE m.aluno.id = ?1")
  public List<Sala> findByAlunoId(Long idAluno);
}
