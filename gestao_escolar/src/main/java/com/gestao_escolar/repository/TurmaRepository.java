package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.TurmaEntity;
import com.gestao_escolar.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TurmaRepository extends JpaRepository<TurmaEntity, UUID> {
    List<TurmaEntity> findAllByOrderBySerieAsc();

//    List<UsuarioEntity> ;
}
