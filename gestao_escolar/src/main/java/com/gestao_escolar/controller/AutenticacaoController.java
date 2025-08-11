package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ReqAutenticacaoDTO;
import com.gestao_escolar.model.dto.ReqRegistroDTO;
import com.gestao_escolar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Endpoints para Login e Registro de Usuários")
public class AutenticacaoController {

    @Autowired
    private UsuarioService  usuarioService;

    @Operation(
            summary = "Realiza o login de um usuário",
            description = "Autentica um usuário com 'login' e 'senha' e retorna um token JWT em caso de sucesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido, retorna o token JWT"),
            @ApiResponse(responseCode = "403", description = "Acesso Negado / Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid ReqAutenticacaoDTO autenticacaoDTO){
        var resposta = usuarioService.logar(autenticacaoDTO);
        return ResponseEntity.ok(resposta);
    }

    @Operation(
            summary = "Registra um novo usuário no sistema",
            description = "Cria um novo usuário (Aluno, Professor, etc.). Esta operação requer permissão de ADMIN ou DIRETOR."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos (ex: login já existe ou campos vazios)"),
            @ApiResponse(responseCode = "403", description = "Acesso Negado. O usuário logado não tem permissão para esta ação.")
    })
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