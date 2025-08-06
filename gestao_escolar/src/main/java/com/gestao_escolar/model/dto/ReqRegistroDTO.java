package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.enums.PapelEnum;

public record ReqRegistroDTO(
        String login,
        String senha,
        String nome,
        PapelEnum papel
) {
}
