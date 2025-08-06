package com.gestao_escolar.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReqAutenticacaoDTO (

        @NotNull(message = "Entrada com valor null não permitida")
        @NotEmpty(message = "Entrada sem valor não permitida")
        String login,

        @NotNull(message = "Entrada com valor null não permitida")
        @NotEmpty(message = "Entrada sem valor não permitida")
        String senha
){
}
