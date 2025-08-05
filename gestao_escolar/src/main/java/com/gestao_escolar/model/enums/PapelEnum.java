package com.gestao_escolar.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PapelEnum  implements GrantedAuthority {
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

    @Override
    public String getAuthority() {
        return "ROLE_"+descricao;
    }
}

