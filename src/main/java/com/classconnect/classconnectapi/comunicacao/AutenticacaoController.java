package com.classconnect.classconnectapi.comunicacao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.requests.AutenticacaoCadastrarDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.requests.AutenticacaoLoginDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.responses.AutenticacaoLoginRespostaDTO;
import com.classconnect.classconnectapi.dados.PerfilRepository;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;
import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.negocio.servicos.AutenticacaoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/perfis")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoLoginRespostaDTO> login(@RequestBody @Valid AutenticacaoLoginDTO body) {
        var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.senha());
        var auth = this.authenticationManager.authenticate(authToken);

        var token = this.autenticacaoService.gerarToken((Perfil) auth.getPrincipal());

        return ResponseEntity.ok().body(new AutenticacaoLoginRespostaDTO(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AutenticacaoCadastrarDTO body) {
        if (this.perfilRepository.findByEmail(body.email()) != null) {
            return ResponseEntity.badRequest().body(
                Map.of("message", "Já existe um perfil cadastrado com o e-mail informado.")
            );
        }

        var senhaEncriptada = new BCryptPasswordEncoder().encode(body.senha());
        var perfil = new Perfil();

        perfil.setNome(body.nome());
        perfil.setEmail(body.email());
        perfil.setSenha(senhaEncriptada);
        perfil.setTipoPerfil(TipoPerfil.valueOf(body.tipoPerfil()));

        this.perfilRepository.save(perfil);

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public String editar(@PathVariable String id, @RequestBody String entity) {
        System.out.println("Editando perfil " + id);

        return entity;
    }
}
