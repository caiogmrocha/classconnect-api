package com.classconnect.classconnectapi.dados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Sala;

public interface SalasRepository extends JpaRepository<Sala, Long>{
  public Optional<Sala> findByProfessorIdAndId(Long idProfessor, Long idSala);
}
