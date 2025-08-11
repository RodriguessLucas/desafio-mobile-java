package com.gestao_escolar.controller;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListProfessorDTO;
import com.gestao_escolar.service.ProfessorMateriaService;
import com.gestao_escolar.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private ProfessorMateriaService professorMateriaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/diretor/professores")
    @PreAuthorize("hasAuthority('ROLE_DIRETOR')")
    public ResponseEntity<List<ResListProfessorDTO>> listarProfessores() {
        var listaProfessores = professorMateriaService.listarProfessoresComMaterias();
        return ResponseEntity.ok(listaProfessores);
    }

    @GetMapping("/diretor/alunos")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRETOR')")
    public ResponseEntity<List<ResListAlunoDTO>> listarAlunos() {
        var listarAlunos = usuarioService.listarAlunos();
        return ResponseEntity.ok(listarAlunos);
    }
}
