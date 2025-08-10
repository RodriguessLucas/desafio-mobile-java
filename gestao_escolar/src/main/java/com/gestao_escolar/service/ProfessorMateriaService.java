package com.gestao_escolar.service;

import com.gestao_escolar.model.dto.ProfessorMateriaDTO;
import com.gestao_escolar.model.dto.ResListMateriaDTO;
import com.gestao_escolar.model.dto.ResListProfessorDTO;
import com.gestao_escolar.model.enums.PapelEnum;
import com.gestao_escolar.repository.ProfessorMateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfessorMateriaService {

    @Autowired
    private ProfessorMateriaRepository professorMateriaRepository;

    public List<ResListProfessorDTO> listarProfessoresComMaterias() {

        List<ProfessorMateriaDTO> professoresFlat =
                professorMateriaRepository.findAllProfessoresAndMaterias(PapelEnum.PROFESSOR);

        Map<UUID, List<ProfessorMateriaDTO>> professoresAgrupadosPorId = professoresFlat.stream()
                .collect(Collectors.groupingBy(ProfessorMateriaDTO::id));

        return professoresAgrupadosPorId.values().stream()
                .map(listaDoProfessor -> {
                    ProfessorMateriaDTO primeiroItem = listaDoProfessor.get(0);
                    UUID idProfessor = primeiroItem.id();
                    String nomeProfessor = primeiroItem.nome();

                    List<String> materias = listaDoProfessor.stream()
                            .map(ProfessorMateriaDTO::materia)
                            .distinct()
                            .collect(Collectors.toList());

                    return new ResListProfessorDTO(idProfessor, nomeProfessor, materias);
                })
                .collect(Collectors.toList());
    }


    public List<ResListMateriaDTO> listarMateriasPorTurma(UUID turmaId) {
        var materiasTurma = professorMateriaRepository.findAllMateriasByTurmaOrder(turmaId);
        return  materiasTurma;
    }
}
