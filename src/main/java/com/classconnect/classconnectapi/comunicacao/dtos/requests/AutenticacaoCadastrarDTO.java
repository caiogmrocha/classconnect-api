package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import com.classconnect.classconnectapi.negocio.enums.TipoPerfil;
import com.classconnect.classconnectapi.validacao.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutenticacaoCadastrarDTO(
        @NotBlank(message = "O campo \"nome\" é obrigatório") String nome,
        @NotBlank(message = "O campo \"e-mail\" é obrigatório") @Email(message = "O campo \"e-mail\" deve estar em um formato válido") String email,
        @NotBlank(message = "O campo \"senha\" é obrigatório") @Size(min = 8, message = "O campo \"senha\" deve ter no mínimo 8 caracteres") String senha,
        @NotBlank(message = "O campo \"tipo de perfil\" é obrigatório") @ValidEnum(targetClassType = TipoPerfil.class, message = "O campo \"tipo de perfil\" deve ser um dos valores: 'ALUNO', 'PROFESSOR'") String tipoPerfil
) {}
