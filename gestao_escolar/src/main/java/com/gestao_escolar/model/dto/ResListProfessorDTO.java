package com.gestao_escolar.model.dto;

import java.util.List;
import java.util.UUID;

public record ResListProfessorDTO(
        UUID id,
        String nome,
        List<String> materias
) {
    public ResListProfessorDTO(UUID id, String nome, List<String> materias) {
        this.id = id;
        this.nome = nome;
        this.materias = materias;
    }
}