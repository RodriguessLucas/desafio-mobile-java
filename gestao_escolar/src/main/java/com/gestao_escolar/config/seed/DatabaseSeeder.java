package com.gestao_escolar.config.seed;

import com.gestao_escolar.model.entity.*;
import com.gestao_escolar.model.enums.PapelEnum;
import com.gestao_escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("seed")
public class DatabaseSeeder implements CommandLineRunner {

    // Injeção dos repositórios
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private MateriaRepository materiaRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private ProfessorMateriaRepository professorMateriaRepository;
    @Autowired private AvaliacaoRepository avaliacaoRepository;
    @Autowired private NotaRepository notaRepository;

    // AJUSTE 1: Injetando o encoder de senhas
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- [PERFIL 'seed' ATIVO] Iniciando o processo de seeding do banco de dados ---");

        if (usuarioRepository.count() > 0) {
            System.out.println("--- [AVISO] O banco de dados já está populado. Seeding não será executado. ---");
            return;
        }

        System.out.println("[OK] Banco de dados vazio. Criando e salvando novas entidades...");

        // A lógica de criação e salvamento permanece a mesma
        List<UsuarioEntity> usuarios = createUsuarios();
        List<MateriaEntity> materias = createMaterias();
        List<TurmaEntity> turmas = createTurmas();

        usuarioRepository.saveAll(usuarios);
        materiaRepository.saveAll(materias);
        turmaRepository.saveAll(turmas);

        List<ProfessorMateriaEntity> associacoes = createProfessoresMaterias(usuarios, materias, turmas);
        professorMateriaRepository.saveAll(associacoes);

        List<AvaliacaoEntity> avaliacoes = createAvaliacoes(associacoes);
        avaliacaoRepository.saveAll(avaliacoes);

        List<NotaEntity> notas = createNotas(usuarios, avaliacoes);
        notaRepository.saveAll(notas);

        System.out.println("\n--- [SUCESSO] Banco de dados populado e senhas criptografadas! ---");
    }

    private List<UsuarioEntity> createUsuarios() {
        List<UsuarioEntity> usuarios = new ArrayList<>();

        // AJUSTE 2: Usando o passwordEncoder para gerar o hash da senha
        String senhaPadraoCriptografada = passwordEncoder.encode("123456");

        // 10 Alunos
        String[] alunosNomes = {"Miguel Silva", "Arthur Costa", "Gael Almeida", "Heitor Martins", "Theo Oliveira", "Helena Pereira", "Alice Rodrigues", "Laura Ferreira", "Maria Alice Souza", "Sophia Gomes"};
        for (String nome : alunosNomes) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().split(" ")[0] + "@aluno.com", senhaPadraoCriptografada, nome, PapelEnum.ALUNO));
        }

        // 5 Professores
        String[] professoresNomes = {"Carlos Drummond", "Cecília Meireles", "Machado de Assis", "Clarice Lispector", "Graciliano Ramos"};
        for (String nome : professoresNomes) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().split(" ")[0] + "@prof.com", senhaPadraoCriptografada, nome, PapelEnum.PROFESSOR));
        }

        // 1 Diretor e 1 Admin
        usuarios.add(new UsuarioEntity("monteiro@dir.com", senhaPadraoCriptografada, "Monteiro Lobato", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("augusto@admin.com", senhaPadraoCriptografada, "Augusto dos Anjos", PapelEnum.ADMIN));

        // Usuários de teste com a mesma senha padrão criptografada
        System.out.println("   - Criando usuários de teste com senha padrão criptografada...");
        usuarios.add(new UsuarioEntity("aluno.teste@gmail.com", senhaPadraoCriptografada, "Aluno Teste", PapelEnum.ALUNO));
        usuarios.add(new UsuarioEntity("professor.teste@gmail.com", senhaPadraoCriptografada, "Professor Teste", PapelEnum.PROFESSOR));
        usuarios.add(new UsuarioEntity("diretor.teste@gmail.com", senhaPadraoCriptografada, "Diretor Teste", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("admin.teste@gmail.com", senhaPadraoCriptografada, "Admin Teste", PapelEnum.ADMIN));

        System.out.println("   - Criados " + usuarios.size() + " usuários.");
        return usuarios;
    }

    // ... O restante dos métodos (createMaterias, createTurmas, etc.) permanecem exatamente iguais ...
    // ... (cole o restante dos métodos da resposta anterior aqui) ...
    private List<MateriaEntity> createMaterias() {
        List<MateriaEntity> materias = new ArrayList<>();
        materias.add(new MateriaEntity("Matemática"));
        materias.add(new MateriaEntity("Português"));
        materias.add(new MateriaEntity("História"));
        materias.add(new MateriaEntity("Geografia"));
        materias.add(new MateriaEntity("Ciências"));
        System.out.println("   - Criadas " + materias.size() + " matérias.");
        return materias;
    }

    private List<TurmaEntity> createTurmas() {
        List<TurmaEntity> turmas = new ArrayList<>();
        turmas.add(new TurmaEntity("9", 'A', "Manhã", 2025));
        turmas.add(new TurmaEntity("9", 'B', "Tarde", 2025));
        turmas.add(new TurmaEntity("8", 'A', "Manhã", 2025));
        turmas.add(new TurmaEntity("8", 'B', "Tarde", 2025));
        turmas.add(new TurmaEntity("7", 'C', "Manhã", 2025));
        System.out.println("   - Criadas " + turmas.size() + " turmas.");
        return turmas;
    }

    private List<ProfessorMateriaEntity> createProfessoresMaterias(List<UsuarioEntity> usuarios, List<MateriaEntity> materias, List<TurmaEntity> turmas) {
        List<ProfessorMateriaEntity> associacoes = new ArrayList<>();
        List<UsuarioEntity> professores = usuarios.stream().filter(u -> u.getPapel() == PapelEnum.PROFESSOR).toList();

        associacoes.add(new ProfessorMateriaEntity(professores.get(0), materias.get(0), turmas.get(0)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(0), materias.get(0), turmas.get(1)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(1), materias.get(1), turmas.get(0)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(1), materias.get(1), turmas.get(2)));
        for (TurmaEntity turma : turmas) {
            associacoes.add(new ProfessorMateriaEntity(professores.get(2), materias.get(2), turma));
        }
        associacoes.add(new ProfessorMateriaEntity(professores.get(3), materias.get(3), turmas.get(1)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(3), materias.get(3), turmas.get(3)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(4), materias.get(4), turmas.get(0)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(4), materias.get(4), turmas.get(2)));
        associacoes.add(new ProfessorMateriaEntity(professores.get(4), materias.get(4), turmas.get(4)));

        System.out.println("   - Criadas " + associacoes.size() + " associações de professores.");
        return associacoes;
    }

    private List<AvaliacaoEntity> createAvaliacoes(List<ProfessorMateriaEntity> associacoes) {
        List<AvaliacaoEntity> avaliacoes = new ArrayList<>();

        avaliacoes.add(new AvaliacaoEntity(LocalDate.of(2025, 3, 15), associacoes.get(0).getMateria(), associacoes.get(0).getTurma()));
        avaliacoes.add(new AvaliacaoEntity(LocalDate.of(2025, 3, 22), associacoes.get(2).getMateria(), associacoes.get(2).getTurma()));
        avaliacoes.add(new AvaliacaoEntity(LocalDate.of(2025, 4, 5), associacoes.get(4).getMateria(), associacoes.get(4).getTurma()));
        avaliacoes.add(new AvaliacaoEntity(LocalDate.of(2025, 4, 10), associacoes.get(3).getMateria(), associacoes.get(3).getTurma()));

        System.out.println("   - Criadas " + avaliacoes.size() + " avaliações.");
        return avaliacoes;
    }


    private List<NotaEntity> createNotas(List<UsuarioEntity> usuarios, List<AvaliacaoEntity> avaliacoes) {
        List<NotaEntity> notas = new ArrayList<>();
        List<UsuarioEntity> alunos = usuarios.stream().filter(u -> u.getPapel() == PapelEnum.ALUNO).toList();
        List<TurmaEntity> turmas = turmaRepository.findAll();

        Map<TurmaEntity, List<UsuarioEntity>> alunosPorTurma = new HashMap<>();
        for(int i = 0; i < alunos.size(); i++){
            if(i >= 11) break; // Ignora o aluno de teste para a distribuição simples
            TurmaEntity turmaDoAluno = turmas.get(i % turmas.size());
            alunosPorTurma.computeIfAbsent(turmaDoAluno, k -> new ArrayList<>()).add(alunos.get(i));
        }

        for (AvaliacaoEntity avaliacao : avaliacoes) {
            TurmaEntity turmaDaAvaliacao = avaliacao.getTurma();
            List<UsuarioEntity> alunosDaTurma = alunosPorTurma.getOrDefault(turmaDaAvaliacao, Collections.emptyList());

            for (UsuarioEntity aluno : alunosDaTurma) {
                double notaAleatoria = ThreadLocalRandom.current().nextDouble(4.0, 10.0);
                BigDecimal notaFormatada = new BigDecimal(notaAleatoria).setScale(1, RoundingMode.HALF_UP);
                notas.add(new NotaEntity(notaFormatada.doubleValue(), avaliacao, aluno));
            }
        }
        System.out.println("   - Criadas " + notas.size() + " notas.");
        return notas;
    }
}