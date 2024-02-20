package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublicarPostDTO(
    @NotBlank(message = "O campo \"titulo\" é obrigatório") String titulo,
    @NotBlank(message = "O campo \"conteudo\" é obrigatório") String conteudo,
    @NotNull(message = "O campo \"arquivo\" é obrigatório") MultipartFile arquivo
) {}
