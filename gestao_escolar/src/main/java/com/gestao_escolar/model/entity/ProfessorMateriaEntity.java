package com.gestao_escolar.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "professores_materiais")
public class ProfessorMateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_materia")
    private MateriaEntity materia;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_turma")
    private TurmaEntity turma;

}
