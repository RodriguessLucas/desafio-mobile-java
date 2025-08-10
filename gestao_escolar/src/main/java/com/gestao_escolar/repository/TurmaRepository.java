package com.gestao_escolar.repository;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.entity.TurmaEntity;
import com.gestao_escolar.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TurmaRepository extends JpaRepository<TurmaEntity, UUID> {
    List<TurmaEntity> findAllByOrderBySerieAsc();


    @Query("SELECT new com.gestao_escolar.model.dto.ResListAlunoDTO(u.id, u.nome) FROM UsuarioEntity u " +
            "JOIN u.matriculados m "+
            "JOIN m.turma t "+
            "WHERE t.id = :turmaId "+
            "ORDER BY u.nome ASC "
    )
    List<ResListAlunoDTO> findByTurmaIdOrderByUsuarioNomeAsc(@Param("turmaId") UUID turmaId);
}


/*
SELECT u.nome, t.serie, t.letra_turma, t.turno FROM usuarios u
JOIN alunos_turmas k ON u.id = k.id_usuario
JOIN turmas t ON k.id_turma = t.id
WHERE t.id ='ec1d5481-b14e-4869-97bf-dbd90493fc65'
ORDER BY t.serie, t.letra_turma, t.turno;
 */