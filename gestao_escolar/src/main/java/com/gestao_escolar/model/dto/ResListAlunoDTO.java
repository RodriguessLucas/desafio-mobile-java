package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.entity.UsuarioEntity;

import java.util.UUID;

public record ResListAlunoDTO(
    UUID id,
    String nome
) {
    public ResListAlunoDTO(UsuarioEntity usuario){
        this(usuario.getId(), usuario.getNome());
    }
}
