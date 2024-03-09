package com.classconnect.classconnectapi.negocio.servicos;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ArquivosService {
  private final Path fileStorageLocation;

  public ArquivosService() {
    this.fileStorageLocation = Paths
        .get("uploads")
        .toAbsolutePath()
        .normalize();
  }

  public Resource baixarArquivo(String fileName) throws IOException {
    Path filePath = fileStorageLocation.resolve(fileName).normalize();

    Resource resource = new UrlResource(filePath.toUri());

    return resource;
  }

  public String salvarArquivo(MultipartFile arquivo) throws IOException {
    var nomeArquivo = UUID
      .randomUUID()
      .toString()
      .concat(
        arquivo.getOriginalFilename().substring(
          arquivo.getOriginalFilename().lastIndexOf(".")
        )
      );
    var localizacaoArquivo = this.fileStorageLocation.resolve(nomeArquivo);
    String uriArquivo;

    try {
      arquivo.transferTo(localizacaoArquivo);

      uriArquivo = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("api/arquivos/")
        .path(nomeArquivo)
        .toUriString();
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar o arquivo", e);
    }

    return uriArquivo;
  }
}
