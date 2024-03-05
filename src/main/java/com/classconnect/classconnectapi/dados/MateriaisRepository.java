package com.classconnect.classconnectapi.dados;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.classconnect.classconnectapi.negocio.entidades.Material;

public interface MateriaisRepository extends JpaRepository<Material, Long>{
  public Optional<Material> findBySalaIdAndId(Long idSala, Long idMaterial);

  @Query("SELECT m FROM Material m JOIN m.sala s WHERE s.id = ?1 AND m.class <> RespostaAtividade")
  public List<Material> findBySalaId(Long idSala);
}
