package com.classconnect.classconnectapi.comunicacao;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.negocio.servicos.ArquivosService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/arquivos")
public class ArquivosController {
  @Autowired
  private ArquivosService arquivosService;

  @GetMapping("/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
    try {
      var resource = this.arquivosService.baixarArquivo(fileName);

      String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

      if (contentType == null) {
        contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
          .body(resource);
    } catch (MalformedURLException ex) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
