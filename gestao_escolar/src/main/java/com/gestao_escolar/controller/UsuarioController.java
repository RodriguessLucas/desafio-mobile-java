package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListProfessorDTO;
import com.gestao_escolar.service.ProfessorMateriaService;
import com.gestao_escolar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "Usuários", description = "Endpoints para consulta de usuários por perfil (Professores e Alunos).")
public class UsuarioController {

    @Autowired
    private ProfessorMateriaService professorMateriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Lista todos os professores e suas matérias",
            description = "Retorna uma lista agrupada de professores com as matérias que cada um leciona. Acesso restrito a Diretores."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer perfil de DIRETOR.")
    })
    @GetMapping("/diretor/professores")
    @PreAuthorize("hasAuthority('ROLE_DIRETOR')")
    public ResponseEntity<List<ResListProfessorDTO>> listarProfessores() {
        var listaProfessores = professorMateriaService.listarProfessoresComMaterias();
        return ResponseEntity.ok(listaProfessores);
    }

    @Operation(
            summary = "Lista todos os alunos cadastrados",
            description = "Retorna uma lista de todos os alunos do sistema. Acesso restrito a Diretores."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            @ApiResponse(responseCode = "204", description = "Nenhum aluno encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer perfil de DIRETOR.")
    })
    @GetMapping("/diretor/alunos")
    @PreAuthorize("hasAuthority('ROLE_DIRETOR')")
    public ResponseEntity<List<ResListAlunoDTO>> listarAlunos() {
        var listarAlunos = usuarioService.listarAlunos();
        if (listarAlunos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listarAlunos);
    }
}