package com.gestao_escolar.controller;


import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
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

    @GetMapping("/turmas")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmas() {
        var listaTurmas = turmaService.listarTurmas();
        return ResponseEntity.ok(listaTurmas);
    }

    @GetMapping("/turmas/{idTurma}/alunos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DIRETOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<ResListAlunoDTO>> listarTurmasAlunos(@PathVariable UUID idTurma) {
        var alunosTurma = turmaService.listarAlunosByTurma(idTurma);
        return  ResponseEntity.ok(alunosTurma);
    }


}
