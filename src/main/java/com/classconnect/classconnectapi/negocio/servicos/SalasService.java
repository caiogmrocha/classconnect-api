package com.classconnect.classconnectapi.negocio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.entidades.Sala;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;

@Service
public class SalasService {
  @Autowired
  private SalasRepository salasRepository;

  public List<Sala> listarSalas() {
    var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    List<Sala> salas;

    if (perfil.getTipoPerfil().equals(TipoPerfil.ALUNO)) {
      salas = salasRepository.findByAlunosId(perfil.getId());
    } else {
      salas = salasRepository.findByProfessorId(perfil.getId());
    }

    return salas;
  }
}
