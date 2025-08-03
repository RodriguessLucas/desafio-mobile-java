package com.gestao_escolar.model.entity;

import com.gestao_escolar.model.enums.PapelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name= "regras")
public class RegraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PapelEnum regra;

    @ManyToMany(mappedBy = "regras", fetch = FetchType.LAZY)
    private Set<UsuarioEntity> usuarios = new HashSet<>();



}
