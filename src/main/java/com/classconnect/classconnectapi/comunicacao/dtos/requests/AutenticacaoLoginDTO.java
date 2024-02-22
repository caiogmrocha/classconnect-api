package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutenticacaoLoginDTO(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, message = "O campo \"senha\" deve ter no mínimo 8 caracteres") String senha
) {}
