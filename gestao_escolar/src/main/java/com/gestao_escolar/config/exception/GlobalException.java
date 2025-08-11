package com.gestao_escolar.config.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosInvalidos>> tratarErro(MethodArgumentNotValidException e){
        var erro = e.getFieldErrors();

        List<DadosInvalidos> listaErros = erro.stream()
                .map(DadosInvalidos::new)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(listaErros);
    }

    private record DadosInvalidos(String campo, String mensagem) {
        public DadosInvalidos(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


}