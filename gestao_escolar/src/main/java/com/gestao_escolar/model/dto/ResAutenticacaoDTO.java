package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.enums.PapelEnum;

import java.util.UUID;

public record ResAutenticacaoDTO (
        String token,
        String email,
        String nome,
        PapelEnum papel,
        UUID id
){
    public ResAutenticacaoDTO(String token, String email, String nome, PapelEnum papel, UUID id) {
        this.token = token;
        this.email = email;
        this.nome = extrairPrimeiroNome(nome);
        this.papel = papel;
        this.id = id;
    }

    private String extrairPrimeiroNome(String nome) {
        if(nome == null || nome.isEmpty()) {
            return "";
        }
        String[] partes = nome.split(" ");
        return partes[0];
    }


}
