package com.gestao_escolar.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReqAutenticacaoDTO (

        @NotNull(message = "Entrada de login com valor null não permitida")
        @NotEmpty(message = "Entrada de login sem valor não permitida")
        String login,

        @NotNull(message = "Entrada de senha com valor null não permitida")
        @NotEmpty(message = "Entrada de senha sem valor não permitida")
        String senha
){
}
