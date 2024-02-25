package com.classconnect.classconnectapi.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.classconnect.classconnectapi.negocio.entidades.Sala;

public interface SalasRepository extends JpaRepository<Sala, Long>{
  public Optional<Sala> findByProfessorIdAndId(Long idProfessor, Long idSala);

  @Query("SELECT COUNT(s) FROM Sala s JOIN s.alunos a WHERE a.id = ?1 AND s.id = ?2")
  public Long countByAlunoIdAndId(Long idAluno, Long idSala);
}
