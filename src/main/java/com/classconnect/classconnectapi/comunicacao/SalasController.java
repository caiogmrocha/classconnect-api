package com.classconnect.classconnectapi.comunicacao;

import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.negocio.servicos.SalasService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/salas")
public class SalasController {
  @Autowired
  private SalasService salasService;

  @GetMapping()
  public ResponseEntity<Map<?, ?>[]> listarSalas() {
    var salas = this.salasService.listarSalas();

    var salasDTO = salas.stream().map(sala -> {
      var professor = sala.getProfessor();

      return Map.of(
        "id", sala.getId(),
        "nome", sala.getNome(),
        "professor", Map.of(
          "id", professor.getId(),
          "nome", professor.getNome(),
          "email", professor.getEmail()
        )
      );
    }).toArray(Map[]::new);

    return ResponseEntity.ok(salasDTO);
  }
}
