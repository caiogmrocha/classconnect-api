package com.classconnect.classconnectapi.negocio.servicos;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.CadastrarSalaDTO;
import com.classconnect.classconnectapi.dados.ProfessoresRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.entidades.Sala;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;

@Service
public class SalasService {
  @Autowired
  private ArquivosService arquivosService;

  @Autowired
  private SalasRepository salasRepository;

  @Autowired
  private ProfessoresRepository professorRepository;

  public List<Sala> listarSalas() {
    var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    List<Sala> salas;

    if (perfil.getTipoPerfil().equals(TipoPerfil.ALUNO)) {
      salas = salasRepository.findByAlunoId(perfil.getId());
    } else {
      salas = salasRepository.findByProfessorId(perfil.getId());
    }

    return salas;
  }

  public void cadastrarSala(CadastrarSalaDTO cadastrarSalaDTO, Long idPerfil) throws IOException, ProfessorNaoExisteException {
    var professor = this.professorRepository.findById(idPerfil);

    if (professor.isEmpty()) {
      throw new ProfessorNaoExisteException(idPerfil);
    }

    var sala = new Sala();

    sala.setNome(cadastrarSalaDTO.nome());
    sala.setDescricao(cadastrarSalaDTO.descricao());

    var uriImagem = this.arquivosService.salvarArquivo(cadastrarSalaDTO.banner()[0]);

    sala.setCaminhoBanner(uriImagem);
    sala.setProfessor(professor.get());

    this.salasRepository.save(sala);
  }
}
