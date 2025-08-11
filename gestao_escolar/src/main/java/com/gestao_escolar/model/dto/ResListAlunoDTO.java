package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.entity.UsuarioEntity;

import java.util.List;
import java.util.UUID;

public record ResListAlunoDTO(
    UUID id,
    String nome,
    List<MateriaNotaDTO> notas
) {
    public ResListAlunoDTO(UsuarioEntity usuario, List<MateriaNotaDTO> notas) {
        this(usuario.getId(), usuario.getNome(), notas);
    }
}
