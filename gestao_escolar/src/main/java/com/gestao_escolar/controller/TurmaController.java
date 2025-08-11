package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ReqCadastrarNotaDTO;
import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListMateriaDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.service.NotaService;
import com.gestao_escolar.service.ProfessorMateriaService;
import com.gestao_escolar.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Turmas e Alocações", description = "Endpoints para consultar informações sobre turmas, alunos e matérias.")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;
    @Autowired
    private ProfessorMateriaService professorMateriaService;
    @Autowired
    private NotaService notaService;

    @Operation(summary = "Lista todas as turmas cadastradas", description = "Retorna uma lista de todas as turmas. Acesso restrito a Diretores e Admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turmas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/turmas")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR')")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmas() {
        var listaTurmas = turmaService.listarTurmas();
        return ResponseEntity.ok(listaTurmas);
    }

    @Operation(summary = "Lista as turmas de um professor específico", description = "Dado o ID de um professor, retorna a lista de turmas em que ele está alocado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turmas do professor retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/turmas/{idProfessor}/professor")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmasPorProfessor(
            @Parameter(description = "ID do professor (usuário) para buscar as turmas") @PathVariable UUID idProfessor) {
        var listaTurmas = turmaService.listarTurmasByProfessor(idProfessor);
        return ResponseEntity.ok(listaTurmas);
    }

    @Operation(summary = "Lista os alunos de uma turma específica", description = "Dado o ID de uma turma, retorna a lista de todos os alunos matriculados nela.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "A turma existe, mas não possui alunos matriculados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @GetMapping("/turmas/{idTurma}/alunos")
    public ResponseEntity<List<ResListAlunoDTO>> listarAlunosPorTurma(
            @Parameter(description = "ID da turma para buscar os alunos") @PathVariable UUID idTurma) {
        var alunosTurma = turmaService.listarAlunosByTurma(idTurma);
        if (alunosTurma.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(alunosTurma);
    }

    @Operation(summary = "Lista as matérias de uma turma específica", description = "Dado o ID de uma turma, retorna a lista de todas as matérias lecionadas nela.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "A turma existe, mas não possui matérias alocadas"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @GetMapping("/turmas/{idTurma}/materias")
    public ResponseEntity<List<ResListMateriaDTO>> listarMateriasPorTurma(
            @Parameter(description = "ID da turma para buscar as matérias") @PathVariable UUID idTurma) {
        var materiaTurma = professorMateriaService.listarMateriasPorTurma(idTurma);
        if(materiaTurma.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(materiaTurma);
    }


    @Operation(summary = "Cadastra uma ou mais notas para alunos", description = "Recebe uma lista de notas para cadastrar. Acesso restrito a Professores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notas cadastradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição (ex: aluno não pertence à turma)"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping("/turmas/cadastrarnotas")
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<String> cadatrarNotas(@RequestBody List<ReqCadastrarNotaDTO> notasAlunos){
        notaService.cadastrarNota(notasAlunos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notas cadastradas com sucesso!");
    }
}