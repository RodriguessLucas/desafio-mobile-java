package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ReqAutenticacaoDTO;
import com.gestao_escolar.model.dto.ReqRegistroDTO;
import com.gestao_escolar.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService  usuarioService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid ReqAutenticacaoDTO autenticacaoDTO){
        var resposta = usuarioService.logar(autenticacaoDTO);
        return ResponseEntity.ok(resposta);
    }


    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR')")
    public ResponseEntity<String> registrar(@RequestBody @Valid ReqRegistroDTO reqRegistroDTO) {
        try {
            usuarioService.registrar(reqRegistroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
