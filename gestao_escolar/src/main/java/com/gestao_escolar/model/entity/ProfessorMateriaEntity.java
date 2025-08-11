package com.gestao_escolar.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "professores_materias")
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

    @OneToMany(mappedBy = "professorMateria")
    private List<NotaEntity> notas = new ArrayList<>();

    public ProfessorMateriaEntity(UsuarioEntity usuario, MateriaEntity materia, TurmaEntity turma) {
        this.usuario = usuario;
        this.materia = materia;
        this.turma = turma;
    }
}
