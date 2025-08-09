package com.gestao_escolar.model.dto;

import java.util.UUID;

public record ProfessorMateriaDTO(
        UUID id,
        String nome,
        String materia
)
{
    public ProfessorMateriaDTO(UUID id, String nome, String materia){
        this.id = id;
        this.nome = nome;
        this.materia = materia;
    }
}
