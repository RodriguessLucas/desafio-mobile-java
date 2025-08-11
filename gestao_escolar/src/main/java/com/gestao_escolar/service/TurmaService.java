package com.gestao_escolar.service;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.repository.TurmaRepository;
import com.gestao_escolar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ResListTurmaDTO> listarTurmas() {
        var listTurmas = turmaRepository.findAllByOrderBySerieAsc();
        return listTurmas.stream().map(ResListTurmaDTO::new).toList();
    }


    public List<ResListAlunoDTO> listarAlunosByTurma(UUID idTurma) {
        var alunosTurma = turmaRepository.findByTurmaIdOrderByUsuarioNomeAsc(idTurma);
        return alunosTurma;
    }

    public List<ResListTurmaDTO> listarTurmasByProfessor(UUID idProfessor) {
        var existeProfessor = usuarioRepository.existsById(idProfessor);
        var listTurmas = turmaRepository.findAllTurmasByProfessorId(idProfessor);

        if (!existeProfessor) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O professor de Id: " + idProfessor + " não existe"
            );
        }

        if (listTurmas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O professor de Id: " + idProfessor + " não possui turmas."
            );
        }

        return listTurmas;

    };

}
