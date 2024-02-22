package com.classconnect.classconnectapi.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MimeTypeValidator.class)
public @interface ValidMimeType {
  String message() default "Invalid MIME type.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] value() default {};
}
