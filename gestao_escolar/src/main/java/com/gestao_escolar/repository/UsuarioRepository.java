package com.gestao_escolar.repository;

import com.gestao_escolar.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {


    UserDetails findByLogin(String nome);


}
