package com.classconnect.classconnectapi.negocio.enums;

public enum TipoPerfil {
    PROFESSOR("professor"),
    ALUNO("aluno");

    private String tipo;

    TipoPerfil(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
