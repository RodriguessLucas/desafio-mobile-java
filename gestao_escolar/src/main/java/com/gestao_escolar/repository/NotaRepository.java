package com.gestao_escolar.repository;

import com.gestao_escolar.model.dto.MateriaNotaDTO;
import com.gestao_escolar.model.entity.NotaEntity;
import com.gestao_escolar.model.entity.ProfessorMateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotaRepository extends JpaRepository<NotaEntity, UUID> {

    @Query("SELECT new com.gestao_escolar.model.dto.MateriaNotaDTO(m.id, m.nome, u.id, u.nome, n.nota) " +
            "FROM NotaEntity n " +
            "JOIN n.professorMateria pm "+
            "JOIN pm.materia m " +
            "JOIN pm.usuario u " +
            "WHERE n.usuario.id = :idAluno "
    )
    List<MateriaNotaDTO> findAllNotasAluno(@Param("idAluno") UUID idAluno);
}
