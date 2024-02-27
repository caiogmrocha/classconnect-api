package com.classconnect.classconnectapi.integracao.negocio.servicos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.dados.AlunosRepository;
import com.classconnect.classconnectapi.dados.AnexosRepository;
import com.classconnect.classconnectapi.dados.MateriaisRepository;
import com.classconnect.classconnectapi.dados.ProfessorRepository;
import com.classconnect.classconnectapi.dados.RespostaAtividadeRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Professor;
import com.classconnect.classconnectapi.negocio.entidades.Sala;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.negocio.servicos.PostsService;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;

public class PostsServiceTest {
  @Mock
  private ProfessorRepository professorRepository;

  @Mock
  private AlunosRepository alunosRepository;

  @Mock
  private SalasRepository salasRepository;

  @Mock
  private MateriaisRepository materiaisRepository;

  @Mock
  private AnexosRepository anexosRepository;

  @Mock
  private RespostaAtividadeRepository respostaAtividadeRepository;

  @Autowired
  @InjectMocks
  private PostsService postsService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("não deve retornar nada ao publicar um post com sucesso")
  public void publicarPostCaso1() throws ProfessorNaoExisteException, SalaNaoPertenceProfessorException {
    var professor = new Professor();

    professor.setId(1L);
    professor.setNome("Professor");
    professor.setEmail("professor@email.com");
    professor.setSenha("senha");
    professor.setTipoPerfil(TipoPerfil.PROFESSOR);

    when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

    var sala = new Sala();

    sala.setId(1L);
    sala.setNome("Sala 1");

    when(salasRepository.findByProfessorIdAndId(1L, 1L)).thenReturn(Optional.of(sala));

    var publicarPostDTO = new PublicarPostDTO(
      "Título",
      "Descrição",
      null,
      new MultipartFile[]{}
    );

    postsService.publicarPost(publicarPostDTO, 1L, 1L);

    verify(materiaisRepository, times(1)).save(any());
  }
}
