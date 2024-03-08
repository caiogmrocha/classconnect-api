package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import com.classconnect.classconnectapi.validacao.ValidMimeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CadastrarSalaDTO(
  @NotBlank(message = "O campo \"nome\" é obrigatório")
  String nome,

  @NotBlank(message = "O campo \"descricao\" é obrigatório")
  String descricao,

  @NotEmpty(message = "O campo \"banner\" é obrigatório")
  @ValidMimeType(value = {
    "image/png",
    "image/jpeg",
    "image/jpg",
    "image/gif",
  }, message = "O banner deve ser um dos seguintes tipos: .png, .jpeg, .jpg, .gif")
  MultipartFile[] banner
) {}
