package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {


    UserDetails findByLogin(String nome);


}
