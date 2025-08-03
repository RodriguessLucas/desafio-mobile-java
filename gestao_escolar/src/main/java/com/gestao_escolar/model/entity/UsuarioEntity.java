package com.gestao_escolar.model.entity;

import com.gestao_escolar.model.enums.PapelEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy  = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "papeis_usuarios",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name ="regra_id")
    )
    private List<RegraEntity> regras = new ArrayList<>();


    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();


    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<NotaEntity> notas = new ArrayList<>();

}
