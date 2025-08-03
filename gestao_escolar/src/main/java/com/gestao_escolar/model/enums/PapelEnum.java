package com.gestao_escolar.model.enums;

public enum PapelEnum {
    ALUNO("Aluno"),
    PROFESSOR("Professor"),
    DIRETOR("Diretor");

    private String descricao;

    PapelEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}

