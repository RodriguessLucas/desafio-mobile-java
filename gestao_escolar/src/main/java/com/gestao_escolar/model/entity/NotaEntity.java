package com.gestao_escolar.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "notas")
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @Column(nullable = false)
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "id_professor_materia")
    private ProfessorMateriaEntity professorMateria;

    public NotaEntity(Double nota, ProfessorMateriaEntity professorMateria , UsuarioEntity aluno) {
        this.nota = nota;
        this.professorMateria = professorMateria;
        this.usuario = aluno;
    }
}
