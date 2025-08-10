package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.AlunoTurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurmaEntity, UUID> {
}
