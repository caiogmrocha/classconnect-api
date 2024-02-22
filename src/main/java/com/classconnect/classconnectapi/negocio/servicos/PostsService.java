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
import com.classconnect.classconnectapi.negocio.entidades.Anexo;
import com.classconnect.classconnectapi.negocio.entidades.Material;
import com.classconnect.classconnectapi.negocio.servicos.excecoes.ProfessorNaoExisteException;

@Service
public class PostsService {
  private final Path fileStorageLocation;

  @Autowired
  private ProfessorRepository professorRepository;

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

  public void publicarPost(PublicarPostDTO publicarPostDTO, Long idSala, Long idPerfil) throws ProfessorNaoExisteException {
    var professor = this.professorRepository.findById(idPerfil);

    if (professor.isEmpty()) {
      throw new ProfessorNaoExisteException(idPerfil);
    }

    var material = new Material();

    material.setTitulo(publicarPostDTO.titulo());
    material.setConteudo(publicarPostDTO.conteudo());
    material.setProfessor(professor.get());

    this.materiaisRepository.save(material);

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
      anexo.setMaterial(material);

      anexos.add(anexo);
    }

    this.anexosRepository.saveAll(anexos);
  }
}
