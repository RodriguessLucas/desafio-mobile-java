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
@Table(name = "turmas")
public class TurmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String serie;

    @Column(nullable = false, name = "letra_turma")
    private Character letraTurma;

    @Column(nullable = false)
    private String turno;

    @Column(nullable = false, name = "ano_letivo")
    private Integer anoLetivo;

    @OneToMany(mappedBy = "turma")
    private List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();


    @OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
    private List<NotaEntity> alunoNotas = new ArrayList<>();



    public TurmaEntity(String serie, char letraTurma, String turno, int anoLetivo) {
        this.serie = serie;
        this.letraTurma = letraTurma;
        this.turno = turno;
        this.anoLetivo = anoLetivo;
    }
}
