package com.gestao_escolar.repository;

import com.gestao_escolar.model.dto.ProfessorMateriaDTO;
import com.gestao_escolar.model.entity.ProfessorMateriaEntity;
import com.gestao_escolar.model.enums.PapelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProfessorMateriaRepository extends JpaRepository<ProfessorMateriaEntity, UUID> {


    @Query("SELECT new com.gestao_escolar.model.dto.ProfessorMateriaDTO(u.id, u.nome, m.nome) " +
            "FROM UsuarioEntity u " +
            "JOIN u.alocacoes a " +
            "JOIN a.materia m " +
            "WHERE u.papel = :papel " +
            "ORDER BY u.nome, m.nome")
    List<ProfessorMateriaDTO> findAllProfessoresAndMaterias(@Param("papel") PapelEnum papel);

}
