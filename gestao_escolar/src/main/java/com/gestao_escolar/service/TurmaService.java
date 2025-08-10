package com.gestao_escolar.service;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public List<ResListTurmaDTO> listarTurmas() {
        var listTurmas = turmaRepository.findAllByOrderBySerieAsc();
        return listTurmas.stream().map(ResListTurmaDTO::new).toList();
    }


    public List<ResListAlunoDTO> listarAlunosByTurma(UUID idTurma) {
        var alunosTurma = turmaRepository.findByTurmaIdOrderByUsuarioNomeAsc(idTurma);
        return alunosTurma;
    }

}
