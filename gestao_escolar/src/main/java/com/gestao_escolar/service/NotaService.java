package com.gestao_escolar.service;

import com.gestao_escolar.model.dto.ReqCadastrarNotaDTO;
import com.gestao_escolar.model.entity.NotaEntity;
import com.gestao_escolar.repository.AlunoTurmaRepository;
import com.gestao_escolar.repository.NotaRepository;
import com.gestao_escolar.repository.ProfessorMateriaRepository;
import com.gestao_escolar.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private ProfessorMateriaRepository professorMateriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoTurmaRepository  alunoTurmaRepository;

    @Transactional
    public void cadastrarNota(List<ReqCadastrarNotaDTO> listaNotaDTO) {
        List<NotaEntity> notasCriadas = new ArrayList<>();

        for (ReqCadastrarNotaDTO notaAluno : listaNotaDTO) {
            var aluno = usuarioRepository.findById(notaAluno.idAluno()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado com o ID fornecido.")
            );

            var alocacao = professorMateriaRepository.findByMateriaIdAndTurmaId(notaAluno.idMateria(), notaAluno.idTurma());

            if(alocacao == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não existe um professor alocado para esta matéria nesta turma.");
            }

            boolean alunoEstaNaTurma = alunoTurmaRepository.existsByUsuarioIdAndTurmaId(notaAluno.idAluno(), notaAluno.idTurma());
            if (!alunoEstaNaTurma) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O aluno " + aluno.getNome() + " não está matriculado nesta turma.");
            }

            if(notaAluno.nota() < 0 || notaAluno.nota() > 10) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O aluno " + aluno.getNome() + " só pode ter nota entre 0 a 10");
            }
            NotaEntity notaEntity = new NotaEntity(notaAluno.nota(), alocacao, aluno);
            notasCriadas.add(notaEntity);

        }

        notaRepository.saveAll(notasCriadas);

    }


}
