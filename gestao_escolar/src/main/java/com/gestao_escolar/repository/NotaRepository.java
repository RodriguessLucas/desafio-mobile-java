package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.ProfessorMateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<ProfessorMateriaEntity, Long> {
}
