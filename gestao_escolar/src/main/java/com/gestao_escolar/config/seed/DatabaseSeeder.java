package com.gestao_escolar.config.seed;

import com.gestao_escolar.model.entity.*;
import com.gestao_escolar.model.enums.PapelEnum;
import com.gestao_escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("seed")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private MateriaRepository materiaRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private ProfessorMateriaRepository professorMateriaRepository;
    @Autowired private AlunoTurmaRepository alunoTurmaRepository;
    @Autowired private NotaRepository notaRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional // Executa todo o método 'run' dentro de uma única transação
    public void run(String... args) throws Exception {
        System.out.println("--- [PERFIL 'seed' ATIVO] Iniciando o processo de seeding do banco de dados ---");

        if (usuarioRepository.count() > 0) {
            System.out.println("--- [AVISO] O banco de dados já está populado. Seeding não será executado. ---");
            return;
        }

        System.out.println("[OK] Banco de dados vazio. Criando e salvando novas entidades...");

        // 1. Criar e salvar as entidades primárias
        List<UsuarioEntity> usuarios = createAndSaveUsuarios();
        List<MateriaEntity> materias = createAndSaveMaterias();
        List<TurmaEntity> turmas = createAndSaveTurmas();

        // 2. Usar as entidades salvas (com IDs) para criar e salvar os relacionamentos
        List<ProfessorMateriaEntity> alocacoes = createAndSaveAlocacoes(usuarios, materias, turmas);
        List<AlunoTurmaEntity> matriculas = createAndSaveMatriculas(usuarios, turmas);
        createAndSaveNotas(matriculas, alocacoes);

        System.out.println("\n--- [SUCESSO] Banco de dados populado com um conjunto de dados maior e consistente! ---");
    }

    private List<UsuarioEntity> createAndSaveUsuarios() {
        System.out.println("   - Criando usuários...");
        List<UsuarioEntity> usuarios = new ArrayList<>();
        String senhaPadraoCriptografada = passwordEncoder.encode("123456");

        // 30 Alunos
        String[] nomesAlunos = {
                "Miguel Silva", "Arthur Costa", "Gael Almeida", "Heitor Martins", "Theo Oliveira", "Davi Souza", "Bernardo Gomes", "Gabriel Lima", "Ravi Ferreira", "Noah Carvalho",
                "Helena Pereira", "Alice Rodrigues", "Laura Santos", "Maria Alice Ribeiro", "Sophia Alves", "Manuela Monteiro", "Maitê Castro", "Liz Cunha", "Cecília Pinto", "Isabella Correia",
                "Enzo Dias", "Lorenzo Cardoso", "Pedro Rocha", "Lucas Barbosa", "Benjamin Mendes", "Nicolas Nogueira", "Guilherme Sales", "Rafael Azevedo", "Joaquim Freire", "Samuel Barros"
        };
        for (String nome : nomesAlunos) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().replace(" ", ".") + "@aluno.com", senhaPadraoCriptografada, nome, PapelEnum.ALUNO));
        }

        // 8 Professores
        String[] nomesProfessores = {"Carlos Drummond", "Cecília Meireles", "Machado de Assis", "Clarice Lispector", "Graciliano Ramos", "Vinicius de Moraes", "Lygia Fagundes", "João Guimarães"};
        for (String nome : nomesProfessores) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().replace(" ", ".") + "@prof.com", senhaPadraoCriptografada, nome, PapelEnum.PROFESSOR));
        }

        // Gestão
        usuarios.add(new UsuarioEntity("monteiro.lobato@dir.com", senhaPadraoCriptografada, "Monteiro Lobato", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("augusto.anjos@admin.com", senhaPadraoCriptografada, "Augusto dos Anjos", PapelEnum.ADMIN));

        // Usuários de teste fixos (mantidos)
        usuarios.add(new UsuarioEntity("aluno.teste@gmail.com", senhaPadraoCriptografada, "Aluno Teste", PapelEnum.ALUNO));
        usuarios.add(new UsuarioEntity("professor.teste@gmail.com", senhaPadraoCriptografada, "Professor Teste", PapelEnum.PROFESSOR));
        usuarios.add(new UsuarioEntity("diretor.teste@gmail.com", senhaPadraoCriptografada, "Diretor Teste", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("admin.teste@gmail.com", senhaPadraoCriptografada, "Admin Teste", PapelEnum.ADMIN));

        System.out.println("   - Criados " + usuarios.size() + " usuários.");
        return usuarioRepository.saveAll(usuarios);
    }

    private List<MateriaEntity> createAndSaveMaterias() {
        System.out.println("   - Criando matérias...");
        List<MateriaEntity> materias = List.of(
                new MateriaEntity("Matemática"), new MateriaEntity("Português"), new MateriaEntity("História"),
                new MateriaEntity("Geografia"), new MateriaEntity("Ciências"), new MateriaEntity("Física"),
                new MateriaEntity("Química"), new MateriaEntity("Artes")
        );
        System.out.println("   - Criadas " + materias.size() + " matérias.");
        return materiaRepository.saveAll(materias);
    }

    private List<TurmaEntity> createAndSaveTurmas() {
        System.out.println("   - Criando turmas...");
        List<TurmaEntity> turmas = List.of(
                new TurmaEntity("9º Ano", 'A', "Manhã", 2025),
                new TurmaEntity("9º Ano", 'B', "Tarde", 2025),
                new TurmaEntity("8º Ano", 'A', "Manhã", 2025),
                new TurmaEntity("8º Ano", 'B', "Tarde", 2025),
                new TurmaEntity("7º Ano", 'A', "Manhã", 2025),
                new TurmaEntity("6º Ano", 'A', "Manhã", 2025)
        );
        System.out.println("   - Criadas " + turmas.size() + " turmas.");
        return turmaRepository.saveAll(turmas);
    }

    private List<ProfessorMateriaEntity> createAndSaveAlocacoes(List<UsuarioEntity> usuarios, List<MateriaEntity> materias, List<TurmaEntity> turmas) {
        System.out.println("   - Alocando professores em matérias e turmas...");
        List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();
        List<UsuarioEntity> professores = usuarios.stream().filter(u -> u.getPapel() == PapelEnum.PROFESSOR && !u.getLogin().contains("teste")).toList();

        // Lógica para distribuir os professores, matérias e turmas
        for (int i = 0; i < professores.size(); i++) {
            UsuarioEntity professor = professores.get(i);
            MateriaEntity materiaPrincipal = materias.get(i % materias.size()); // Cada prof com uma matéria principal

            // Aloca o professor em 2 turmas com sua matéria principal
            alocacoes.add(new ProfessorMateriaEntity(professor, materiaPrincipal, turmas.get(i % turmas.size())));
            alocacoes.add(new ProfessorMateriaEntity(professor, materiaPrincipal, turmas.get((i + 1) % turmas.size())));

            // Alguns professores ensinam uma matéria extra em uma turma
            if (i % 2 == 0) {
                MateriaEntity materiaExtra = materias.get((i + 2) % materias.size());
                alocacoes.add(new ProfessorMateriaEntity(professor, materiaExtra, turmas.get(i % turmas.size())));
            }
        }

        System.out.println("   - Criadas " + alocacoes.size() + " alocações de professores.");
        return professorMateriaRepository.saveAll(alocacoes);
    }

    private List<AlunoTurmaEntity> createAndSaveMatriculas(List<UsuarioEntity> usuarios, List<TurmaEntity> turmas) {
        System.out.println("   - Matriculando alunos em turmas...");
        List<AlunoTurmaEntity> matriculas = new ArrayList<>();
        List<UsuarioEntity> alunos = usuarios.stream().filter(u -> u.getPapel() == PapelEnum.ALUNO && !u.getLogin().contains("teste")).toList();

        // Distribui os alunos pelas turmas de forma equilibrada
        for (int i = 0; i < alunos.size(); i++) {
            TurmaEntity turma = turmas.get(i % turmas.size());
            matriculas.add(new AlunoTurmaEntity(alunos.get(i), turma));
        }

        System.out.println("   - Criadas " + matriculas.size() + " matrículas de alunos.");
        return alunoTurmaRepository.saveAll(matriculas);
    }

    private List<NotaEntity> createAndSaveNotas(List<AlunoTurmaEntity> matriculas, List<ProfessorMateriaEntity> alocacoes) {
        System.out.println("   - Gerando notas para os alunos...");
        List<NotaEntity> notas = new ArrayList<>();

        for (AlunoTurmaEntity matricula : matriculas) {
            UsuarioEntity aluno = matricula.getUsuario();
            TurmaEntity turma = matricula.getTurma();

            List<ProfessorMateriaEntity> alocacoesDaTurma = alocacoes.stream()
                    .filter(alocacao -> alocacao.getTurma().getId().equals(turma.getId()))
                    .toList();

            for (ProfessorMateriaEntity alocacao : alocacoesDaTurma) {
                double notaAleatoria = ThreadLocalRandom.current().nextDouble(4.0, 10.0);
                BigDecimal notaFormatada = new BigDecimal(notaAleatoria).setScale(1, RoundingMode.HALF_UP);
                notas.add(new NotaEntity(notaFormatada.doubleValue(), alocacao, aluno));
            }
        }

        System.out.println("   - Criadas " + notas.size() + " notas.");
        return notaRepository.saveAll(notas);
    }
}