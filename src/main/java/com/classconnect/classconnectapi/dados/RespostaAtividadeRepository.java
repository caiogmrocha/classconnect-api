package com.classconnect.classconnectapi.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.RespostaAtividade;

public interface RespostaAtividadeRepository extends JpaRepository<RespostaAtividade, Long>{
  public Optional<RespostaAtividade> findByAtividadeIdAndAlunoId(Long idAtividade, Long idAluno);
}
