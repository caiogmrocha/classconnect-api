package com.classconnect.classconnectapi.dados;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.classconnect.classconnectapi.negocio.entidades.Sala;

public interface SalasRepository extends JpaRepository<Sala, Long>{
  public Optional<Sala> findByProfessorIdAndId(Long idProfessor, Long idSala);

  public List<Sala> findByProfessorId(Long idProfessor);

  public List<Sala> findByAlunosId(Long idAluno);

  @Query("SELECT COUNT(s) FROM Sala s JOIN s.alunos a WHERE a.id = ?1 AND s.id = ?2")
  public Long countByAlunoIdAndId(Long idAluno, Long idSala);
}
