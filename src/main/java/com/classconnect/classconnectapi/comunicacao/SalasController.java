package com.classconnect.classconnectapi.comunicacao;

import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.CadastrarSalaDTO;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.servicos.SalasService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/salas")
public class SalasController {
  @Autowired
  private SalasService salasService;

  @GetMapping()
  public ResponseEntity<Map<?, ?>[]> listarSalas() {
    try {
      var salas = this.salasService.listarSalas();

      var salasDTO = salas.stream().map(sala -> {
        var professor = sala.getProfessor();

        return Map.of(
            "id", sala.getId(),
            "nome", sala.getNome(),
            "professor", Map.of(
                "id", professor.getId(),
                "nome", professor.getNome(),
                "email", professor.getEmail()));
      }).toArray(Map[]::new);

      return ResponseEntity.ok(salasDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new Map[] {Map.of("mensagem", "Internal server error")});
    }
  }

  @PostMapping()
  public ResponseEntity<Map<?,?>> cadastrarSala(@Valid @ModelAttribute CadastrarSalaDTO cadastrarSalaDTO) {
    var perfil = (Perfil) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    try {
      this.salasService.cadastrarSala(cadastrarSalaDTO, perfil.getId());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(Map.of("mensagem", "Internal server error"));
    }

    return ResponseEntity.ok(Map.of("mensagem", "Sala cadastrada com sucesso"));
  }
}
