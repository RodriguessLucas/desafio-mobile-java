package com.gestao_escolar.model.entity;

import com.gestao_escolar.model.enums.PapelEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy  = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PapelEnum papel;

    public UsuarioEntity(String login, String senha, String nome, PapelEnum papel) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.papel = papel;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private  List<AlunoTurmaEntity> matriculados =  new ArrayList<>();


    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<NotaEntity> notas = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(papel.getAuthority()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
