package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
}
