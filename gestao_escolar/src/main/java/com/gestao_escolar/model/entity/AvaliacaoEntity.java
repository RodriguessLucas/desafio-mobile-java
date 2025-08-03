package com.gestao_escolar.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "avaliacoes")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private MateriaEntity materia;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private TurmaEntity turma;

    @Column(nullable = false, name = "data_aplicacao")
    private LocalDate dataAplicacao;

    @OneToMany(mappedBy = "avaliacao", fetch = FetchType.LAZY)
    private List<NotaEntity> notas = new ArrayList<>();


}
