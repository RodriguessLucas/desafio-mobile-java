package com.gestao_escolar.controller;


import com.gestao_escolar.model.dto.ReqCadastrarNotaDTO;
import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListMateriaDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.service.NotaService;
import com.gestao_escolar.service.ProfessorMateriaService;
import com.gestao_escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private ProfessorMateriaService professorMateriaService;
    @Autowired
    private NotaService notaService;

    @GetMapping("/turmas")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR')")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmas() {
        var listaTurmas = turmaService.listarTurmas();
        return ResponseEntity.ok(listaTurmas);
    }

    @GetMapping("/turmas/{idProfessor}/professor")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmasPorProfessor(@PathVariable UUID idProfessor) {
        var listaTurmas = turmaService.listarTurmasByProfessor(idProfessor);
        return ResponseEntity.ok(listaTurmas);
    }

    @GetMapping("/turmas/{idTurma}/alunos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<ResListAlunoDTO>> listarAlunosPorTurma(@PathVariable UUID idTurma) {
        var alunosTurma = turmaService.listarAlunosByTurma(idTurma);
        return  ResponseEntity.ok(alunosTurma);
    }

    @GetMapping("/turmas/{idTurma}/materias")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<ResListMateriaDTO>> listarMateriasPorTurma(@PathVariable UUID idTurma) {
        var materiaTurma = professorMateriaService.listarMateriasPorTurma(idTurma);
        if(materiaTurma.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(materiaTurma);
    }

    @PostMapping("/turmas/cadastrarnotas")
    @PreAuthorize("hasAnyAuthority('ROLE_PROFESSOR')")
    public ResponseEntity cadatrarNotas(@RequestBody List<ReqCadastrarNotaDTO> notasAlunos){
        notaService.cadastrarNota(notasAlunos);
        return ResponseEntity.ok("Sucesso!");
    }




}
