package com.classconnect.classconnectapi.negocio.enums;

public enum TipoPerfil {
    PROFESSOR("professor"),
    ALUNO("aluno");

    private String value;

    TipoPerfil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
