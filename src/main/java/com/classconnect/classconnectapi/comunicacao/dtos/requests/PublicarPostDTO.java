package com.classconnect.classconnectapi.comunicacao.dtos.requests;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.classconnect.classconnectapi.validacao.ValidMimeType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PublicarPostDTO(
    @NotBlank(message = "O campo \"titulo\" é obrigatório")
    @Size(max = 100, message = "O campo \"titulo\" deve ter no máximo 100 caracteres")
    String titulo,

    @NotBlank(message = "O campo \"conteudo\" é obrigatório")
    String conteudo,

    @Future(message = "A data de entrega deve ser no futuro")
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    LocalDateTime dataEntrega,

    @NotEmpty(message = "O campo \"arquivos\" é obrigatório")
    @ValidMimeType(value = {
      "text/plain",
      "text/html",
      "image/png",
      "image/jpeg",
      "image/jpg",
      "application/pdf",

      "application/msword",
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",

      "application/vnd.ms-excel",
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",

      "application/vnd.ms-powerpoint",
      "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    }, message = "O arquivo deve ser um dos seguintes tipos: .txt, .html, .png, .jpeg, .jpg, .pdf, .doc, .docx, .xls, .xlsx, .ppt, .pptx")
    MultipartFile[] arquivos
) {}
