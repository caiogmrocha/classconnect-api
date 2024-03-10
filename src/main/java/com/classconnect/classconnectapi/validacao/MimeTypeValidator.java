package com.classconnect.classconnectapi.validacao;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MimeTypeValidator implements ConstraintValidator<ValidMimeType, MultipartFile[]> {

  private String[] allowedMimeTypes;

  @Override
  public void initialize(ValidMimeType constraintAnnotation) {
    this.allowedMimeTypes = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(MultipartFile[] files, ConstraintValidatorContext context) {
    if (files == null) {
      return true;
    }

    for (MultipartFile file : files) {
      if (file != null && Arrays.asList(allowedMimeTypes).contains(file.getContentType())) {
        return true;
      }

      continue;
    }

    return false;
  }
}
