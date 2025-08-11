package com.gestao_escolar.repository;

import com.gestao_escolar.model.dto.ResListAlunoDTO;
import com.gestao_escolar.model.dto.ResListMateriaDTO;
import com.gestao_escolar.model.dto.ResListTurmaDTO;
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


    @Query("SELECT new com.gestao_escolar.model.dto.ResListTurmaDTO(t.id, t.serie, t.turno) " +
            "FROM TurmaEntity t " +
            "JOIN t.alocacoes pm " +
            "WHERE pm.usuario.id = :idProfessor " +
            "ORDER BY t.serie, t.turno "
    )
    List<ResListTurmaDTO> findAllTurmasByProfessorId(@Param("idProfessor") UUID idProfessor);




}
