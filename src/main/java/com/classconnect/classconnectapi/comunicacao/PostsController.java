package com.classconnect.classconnectapi.comunicacao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.PublicarPostDTO;
import com.classconnect.classconnectapi.configuracao.ArmazenamentoArquivosPropertiesConfiguration;
import com.classconnect.classconnectapi.dados.AnexosRepository;
import com.classconnect.classconnectapi.dados.MateriaisRepository;
import com.classconnect.classconnectapi.negocio.entidades.Anexo;
import com.classconnect.classconnectapi.negocio.entidades.Material;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class PostsController {
  private final Path fileStorageLocation;

  @Autowired
  private MateriaisRepository materiaisRepository;

  @Autowired
  private AnexosRepository anexosRepository;

  public PostsController(ArmazenamentoArquivosPropertiesConfiguration fileStorageProperties) {
    this.fileStorageLocation = Paths
        .get(fileStorageProperties.getUploadDir())
        .toAbsolutePath()
        .normalize();
  }

  @PostMapping("/api/salas/{idSala}/posts")
  public ResponseEntity<Map<?,?>> publicarPost(
    @Valid @ModelAttribute PublicarPostDTO publicarPostDTO,
    @PathParam("idSala") Long idSala
  ) {
    var material = new Material();

    material.setTitulo(publicarPostDTO.titulo());
    material.setConteudo(publicarPostDTO.conteudo());

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
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Erro ao salvar o arquivo"));
      }

      var anexo = new Anexo();

      anexo.setCaminho(uriArquivo);
      anexo.setExtensao(uriArquivo.substring(uriArquivo.lastIndexOf(".")));
      anexo.setMimetype(arquivo.getContentType());
      anexo.setMaterial(material);

      anexos.add(anexo);
    }

    this.anexosRepository.saveAll(anexos);

    return ResponseEntity.ok(Map.of("mensagem", "Post publicado com sucesso"));
  }
}
