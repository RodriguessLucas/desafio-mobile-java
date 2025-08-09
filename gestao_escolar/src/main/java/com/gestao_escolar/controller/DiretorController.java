package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ResListProfessorDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.service.ProfessorMateriaService;
import com.gestao_escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diretor")
@PreAuthorize("hasAuthority('ROLE_DIRETOR')")
public class DiretorController {

    @Autowired
    private ProfessorMateriaService professorMateriaService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/professores")
    public ResponseEntity<List<ResListProfessorDTO>> listarProfessores() {
        var listaProfessores = professorMateriaService.listarProfessoresComMaterias();

        return ResponseEntity.ok(listaProfessores);
    }


    @GetMapping("/turmas")
    public ResponseEntity<List<ResListTurmaDTO>> listarTurmas() {
        var listaTurmas = turmaService.listarTurmas();
        return ResponseEntity.ok(listaTurmas);
    }
}