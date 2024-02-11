package com.classconnect.classconnectapi.comunicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classconnect.classconnectapi.comunicacao.dtos.AutenticacaoCadastrarDTO;
import com.classconnect.classconnectapi.comunicacao.dtos.AutenticacaoLoginDTO;
import com.classconnect.classconnectapi.dados.PerfilRepository;
import com.classconnect.classconnectapi.negocio.entidades.Perfil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfis")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoLoginDTO> login(@RequestBody @Valid AutenticacaoLoginDTO body) {
        var authToken = new UsernamePasswordAuthenticationToken(body.email(), body.senha());
        var auth = this.authenticationManager.authenticate(authToken);

        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AutenticacaoCadastrarDTO body) {
        if (this.perfilRepository.findByEmail(body.email()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        var senhaEncriptada = new BCryptPasswordEncoder().encode(body.senha());
        var perfil = new Perfil();

        perfil.setNome(body.nome());
        perfil.setEmail(body.email());
        perfil.setSenha(senhaEncriptada);
        perfil.setTipoPerfil(body.tipoPerfil());

        this.perfilRepository.save(perfil);

        return ResponseEntity.ok(null);
    }

}
