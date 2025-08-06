package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.enums.PapelEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ReqRegistroDTO(

        @NotNull(message = "Entrada de login com valor null não permitida")
        @NotEmpty(message = "Entrada de login sem valor não permitida")
        String login,

        @NotNull(message = "Entrada de senha com valor null não permitida")
        @NotEmpty(message = "Entrada de senha sem valor não permitida")
        @Length(min = 6, message = "Tamanho minimo da senha é 6 caracteres")
        String senha,

        @NotNull(message = "Entrada de nome com valor null não permitida")
        @NotEmpty(message = "Entrada de nome sem valor não permitida")
        String nome,

        @NotNull(message = "Entrada de permissão com valor null não permitida")
        PapelEnum papel
) {
}
