package com.classconnect.classconnectapi.negocio.servicos;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

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
}
