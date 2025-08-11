package com.gestao_escolar.model.dto;

import java.util.UUID;

public record MateriaNotaDTO(
        UUID idMateria,
        String nomeMateria,
        UUID idProfessor,
        String nomeProfessor,
        Double nota
) {
    public MateriaNotaDTO(UUID idMateria, String nomeMateria, UUID idProfessor, String nomeProfessor, Double nota ) {
        this.idMateria = idMateria;
        this.nomeMateria = nomeMateria;
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
        this.nota = nota;
    }
}
