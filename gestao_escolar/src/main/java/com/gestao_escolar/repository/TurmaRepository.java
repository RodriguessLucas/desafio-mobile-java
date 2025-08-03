package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {
}
