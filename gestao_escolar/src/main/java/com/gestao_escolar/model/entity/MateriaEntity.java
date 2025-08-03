package com.gestao_escolar.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "materiais")
public class MateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "materia", fetch = FetchType.LAZY)
    private List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();


    @OneToMany(mappedBy = "materia", fetch = FetchType.LAZY)
    private List<AvaliacaoEntity> avaliacoes = new ArrayList<>();
}
