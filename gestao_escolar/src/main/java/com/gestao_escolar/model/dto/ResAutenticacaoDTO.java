package com.gestao_escolar.model.dto;

import com.gestao_escolar.model.enums.PapelEnum;

public record ResAutenticacaoDTO (
        String token,
        String email,
        PapelEnum papel
){
}
