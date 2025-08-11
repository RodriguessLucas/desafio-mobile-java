package com.gestao_escolar.service;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
import com.gestao_escolar.model.entity.UsuarioEntity;
import com.gestao_escolar.repository.NotaRepository;
import com.gestao_escolar.repository.TurmaRepository;
import com.gestao_escolar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotaRepository notaRepository;

    public List<ResListTurmaDTO> listarTurmas() {
        var listTurmas = turmaRepository.findAllByOrderBySerieAsc();
        return listTurmas.stream().map(ResListTurmaDTO::new).toList();
    }


    public List<ResListAlunoDTO> listarAlunosByTurma(UUID idTurma) {
        var existeTurma = turmaRepository.existsById(idTurma);
        var listaAlunos = turmaRepository.findByTurmaIdOrderByUsuarioNomeAsc(idTurma);

        if (!existeTurma) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Turma de id: " + idTurma + " não existe"

            );
        }

        if(listaAlunos.isEmpty()) {
            return null;
        }

        var resposta =  new ArrayList<ResListAlunoDTO>();
        for (UsuarioEntity usuarioEntity : listaAlunos) {
            var notasAluno = notaRepository.findAllNotasAluno(usuarioEntity.getId());

            ResListAlunoDTO dadosAluno = new ResListAlunoDTO(usuarioEntity, notasAluno);
            resposta.add(dadosAluno);
        }

        return resposta;
    }

    public List<ResListTurmaDTO> listarTurmasByProfessor(UUID idProfessor) {
        var existeProfessor = usuarioRepository.existsById(idProfessor);
        var listTurmas = turmaRepository.findAllTurmasByProfessorId(idProfessor);

        if (!existeProfessor) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "O professor de Id: " + idProfessor + " não existe"
            );
        }
        return listTurmas;

    };

}
