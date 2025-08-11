package com.gestao_escolar.model.dto;

import java.util.UUID;

public record ResListMateriaDTO (
        UUID id,
        String nome
){
    public ResListMateriaDTO (UUID id, String nome){
        this.id = id;
        this.nome = nome;
    }
}
