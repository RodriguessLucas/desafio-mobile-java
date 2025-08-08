package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, UUID> {
}
