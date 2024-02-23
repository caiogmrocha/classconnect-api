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
import com.classconnect.classconnectapi.configuracao.ArmazenamentoArquivosPropertiesConfiguration;
import com.classconnect.classconnectapi.dados.AnexosRepository;
import com.classconnect.classconnectapi.dados.MateriaisRepository;
import com.classconnect.classconnectapi.dados.ProfessorRepository;
import com.classconnect.classconnectapi.dados.SalasRepository;
import com.classconnect.classconnectapi.negocio.entidades.Anexo;
import com.classconnect.classconnectapi.negocio.entidades.Atividade;
import com.classconnect.classconnectapi.negocio.entidades.Material;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.SalaNaoPertenceProfessorException;

@Service
public class PostsService {
  private final Path fileStorageLocation;

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private SalasRepository salasRepository;

  @Autowired
  private MateriaisRepository materiaisRepository;

  @Autowired
  private AnexosRepository anexosRepository;

  public PostsService(ArmazenamentoArquivosPropertiesConfiguration fileStorageProperties) {
    this.fileStorageLocation = Paths
        .get(fileStorageProperties.getUploadDir())
        .toAbsolutePath()
        .normalize();
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
          .path("api/salas/{idSala}/posts/arquivos/")
          .path(nomeArquivo)
          .build(idSala)
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
}
