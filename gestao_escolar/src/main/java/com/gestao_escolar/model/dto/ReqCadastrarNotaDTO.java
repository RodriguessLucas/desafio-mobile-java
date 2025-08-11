package com.gestao_escolar.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public record ReqCadastrarNotaDTO(
        @NotNull(message = "ID do usuário com valor null não permitida")
        @NotEmpty(message = "ID do usuário sem valor não permitida")
        UUID idAluno,

        @NotNull(message = "ID da matéria com valor null não permitida")
        @NotEmpty(message = "ID da matéria sem valor não permitida")
        UUID idMateria,

        @NotNull(message = "ID da turma com valor null não permitida")
        @NotEmpty(message = "ID da turma sem valor não permitida")
        UUID idTurma,

        @NotNull(message = "Nota do aluno com valor null não permitida")
        @NotEmpty(message = "Nota do aluno sem valor não permitida")
        Double nota
) {
}
