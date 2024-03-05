package com.classconnect.classconnectapi.negocio.servicos;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.requests.ResponderAtividadeDTO;
import com.classconnect.classconnectapi.dados.AlunosRepository;
import com.classconnect.classconnectapi.dados.AnexosRepository;
import com.classconnect.classconnectapi.dados.MateriaisRepository;
import com.classconnect.classconnectapi.dados.ProfessoresRepository;
import com.classconnect.classconnectapi.dados.RespostaAtividadeRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Anexo;
import com.classconnect.classconnectapi.negocio.entidades.Atividade;
import com.classconnect.classconnectapi.negocio.entidades.Material;
import com.classconnect.classconnectapi.negocio.entidades.RespostaAtividade;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.AlunoNaoPertenceSalaException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.PostNaoAtividadeException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.PostNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.RespostaAtividadeJaExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;

/**
 * @author Caio Guilherme Moreira da Rocha
 *
 * Este serviço é responsável por lidar com as operações de posts,
 * i.e., materiais e atividades.
 */
@Service
public class PostsService {
  private final Path fileStorageLocation;

  @Autowired
  private ProfessoresRepository professorRepository;

  @Autowired
  private AlunosRepository alunosRepository;

  @Autowired
  private SalasRepository salasRepository;

  @Autowired
  private MateriaisRepository materiaisRepository;

  @Autowired
  private AnexosRepository anexosRepository;

  @Autowired
  private RespostaAtividadeRepository respostaAtividadeRepository;

  public PostsService() {
    this.fileStorageLocation = Paths
        .get("uploads")
        .toAbsolutePath()
        .normalize();
  }

  public List<Material> listarPosts(Long idSala) throws SalaNaoExisteException {
    var sala = this.salasRepository.findById(idSala);

    if (sala.isEmpty()) {
      throw new SalaNaoExisteException(idSala);
    }

    var posts = this.materiaisRepository.findBySalaId(idSala);

    if (posts.isEmpty()) {
      return new ArrayList<Material>();
    }

    return posts;
  }

  public Material detalharPost(Long idSala, Long idPost) throws SalaNaoExisteException, PostNaoExisteException {
    var sala = this.salasRepository.findById(idSala);

    if (sala.isEmpty()) {
      throw new SalaNaoExisteException(idSala);
    }

    var post = this.materiaisRepository.findBySalaIdAndId(idSala, idPost);

    if (post.isEmpty()) {
      throw new PostNaoExisteException(idPost);
    }

    return post.get();
  }

  public void publicarPost(PublicarPostDTO publicarPostDTO, Long idSala, Long idPerfil) throws ProfessorNaoExisteException, SalaNaoPertenceProfessorException {
    var professor = this.professorRepository.findById(idPerfil);

    if (professor.isEmpty()) {
      throw new ProfessorNaoExisteException(idPerfil);
    }

    var sala = this.salasRepository.findByProfessorIdAndId(idPerfil, idSala);

    if (sala.isEmpty()) {
      throw new SalaNaoPertenceProfessorException(idSala, idPerfil);
    }

    Material post = publicarPostDTO.dataEntrega() != null
      ? new Atividade()
      : new Material();

    post.setTitulo(publicarPostDTO.titulo());
    post.setConteudo(publicarPostDTO.conteudo());
    post.setProfessor(professor.get());
    post.setSala(sala.get());

    if (publicarPostDTO.dataEntrega() != null) {
      ((Atividade) post).setDataEntrega(publicarPostDTO.dataEntrega());

      this.materiaisRepository.save((Atividade) post);
    } else {
      this.materiaisRepository.save(post);
    }

    List<Anexo> anexos = new ArrayList<Anexo>();

    for (MultipartFile arquivo : publicarPostDTO.arquivos()) {
      var nomeArquivo = UUID.randomUUID().toString().concat(arquivo.getOriginalFilename().substring(arquivo.getOriginalFilename().lastIndexOf(".")));
      var localizacaoArquivo = this.fileStorageLocation.resolve(nomeArquivo);
      String uriArquivo;

      try {
        arquivo.transferTo(localizacaoArquivo);

        uriArquivo = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("api/arquivos")
          .path(nomeArquivo)
          .toString();
      } catch (IOException e) {
        throw new RuntimeException("Erro ao salvar o arquivo", e);
      }

      var anexo = new Anexo();

      anexo.setCaminho(uriArquivo);
      anexo.setExtensao(uriArquivo.substring(uriArquivo.lastIndexOf(".")));
      anexo.setMimetype(arquivo.getContentType());
      anexo.setMaterial(post);

      anexos.add(anexo);
    }

    this.anexosRepository.saveAll(anexos);
  }

  public void responderAtividade(ResponderAtividadeDTO responderAtividadeDTO, Long idSala, Long idAtividade, Long idAluno) throws SalaNaoExisteException, AlunoNaoPertenceSalaException, PostNaoExisteException, PostNaoAtividadeException, RespostaAtividadeJaExisteException {
    // Verificar se a sala existe
    var sala = this.salasRepository.findById(idSala);

    if (sala.isEmpty()) {
      throw new SalaNaoExisteException(idSala);
    }

    // Verificar se o usuário logado pertece a sala
    var alunoPertenceSala = this.salasRepository.countByAlunoIdAndId(idAluno, idSala);

    if (alunoPertenceSala == 0) {
      throw new AlunoNaoPertenceSalaException(idSala, idAluno);
    }

    // Verificar se o post existe na sala
    var atividade = this.materiaisRepository.findBySalaIdAndId(idSala, idAtividade);

    if (atividade.isEmpty()) {
      throw new PostNaoExisteException(idAluno);
    }

    // Verificar se o post é uma atividade
    if (!(atividade.get() instanceof Atividade)) {
      throw new PostNaoAtividadeException(idAluno);
    }

    // Verificar se o aluno já respondeu a atividade
    var respostaAtividadeAnterior = this.respostaAtividadeRepository.findByAtividadeIdAndAlunoId(idAtividade, idAluno);

    if (respostaAtividadeAnterior.isPresent()) {
      throw new RespostaAtividadeJaExisteException(idAtividade, idAluno);
    }

    var respostaAtividade = new RespostaAtividade();
    var aluno = this.alunosRepository.findById(idAluno);

    respostaAtividade.setAluno(aluno.get());
    respostaAtividade.setAtividade((Atividade) atividade.get());
    respostaAtividade.setSala(sala.get());
    respostaAtividade.setTitulo(responderAtividadeDTO.titulo());
    respostaAtividade.setConteudo(responderAtividadeDTO.conteudo());

    this.materiaisRepository.save(respostaAtividade);

    List<Anexo> anexos = new ArrayList<Anexo>();

    for (MultipartFile arquivo : responderAtividadeDTO.arquivos()) {
      var nomeArquivo = UUID.randomUUID().toString().concat(arquivo.getOriginalFilename().substring(arquivo.getOriginalFilename().lastIndexOf(".")));
      var localizacaoArquivo = this.fileStorageLocation.resolve(nomeArquivo);
      String uriArquivo;

      try {
        arquivo.transferTo(localizacaoArquivo);

        uriArquivo = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("api/arquivos")
          .path(nomeArquivo)
          .toString();
      } catch (IOException e) {
        throw new RuntimeException("Erro ao salvar o arquivo", e);
      }

      var anexo = new Anexo();

      anexo.setCaminho(uriArquivo);
      anexo.setExtensao(uriArquivo.substring(uriArquivo.lastIndexOf(".")));
      anexo.setMimetype(arquivo.getContentType());
      anexo.setMaterial(atividade.get());

      anexos.add(anexo);
    }

    this.anexosRepository.saveAll(anexos);
  }
}
