package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;

public record AutenticacaoCadastrarDTO(String nome, String email, String senha, TipoPerfil tipoPerfil) {
}