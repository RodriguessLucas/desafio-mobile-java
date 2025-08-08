package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.enums.PapelEnum;

public record ResAutenticacaoDTO (
        String token,
        String email,
        String nome,
        PapelEnum papel
){
    public ResAutenticacaoDTO(String token, String email, String nome, PapelEnum papel){
        this.token = token;
        this.email = email;
        this.nome = extrairPrimeiroNome(nome);
        this.papel = papel;
    }

    private String extrairPrimeiroNome(String nome) {
        if(nome == null || nome.isEmpty()) {
            return "";
        }
        String[] partes = nome.split(" ");
        return partes[0];
    }


}
