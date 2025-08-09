package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.entity.TurmaEntity;

import java.util.UUID;

public record ResListTurmaDTO(
        UUID id,
        String turma,
        String turno
) {
    public ResListTurmaDTO(TurmaEntity turma){
        this(turma.getId(), turma.getSerie()+" ano "+turma.getLetraTurma(), turma.getTurno());
    }
}
