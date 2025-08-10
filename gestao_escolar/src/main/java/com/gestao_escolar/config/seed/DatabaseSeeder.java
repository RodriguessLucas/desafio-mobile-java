package com.gestao_escolar.config.seed;

import com.gestao_escolar.model.entity.*;
import com.gestao_escolar.model.enums.PapelEnum;
import com.gestao_escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@Profile("seed") // Este componente só será executado quando o perfil "seed" estiver ativo
public class DatabaseSeeder implements CommandLineRunner {

    // Injeção de todos os repositórios necessários
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private MateriaRepository materiaRepository;
    @Autowired private TurmaRepository turmaRepository;
    @Autowired private ProfessorMateriaRepository professorMateriaRepository;
    @Autowired private AlunoTurmaRepository alunoTurmaRepository; // Repositório para matrículas
    @Autowired private NotaRepository notaRepository;

    // Injeção do PasswordEncoder para criptografar as senhas
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- [PERFIL 'seed' ATIVO] Iniciando o processo de seeding do banco de dados ---");

        // Condição para não executar o seeder se o banco já tiver dados
        if (usuarioRepository.count() > 0) {
            System.out.println("--- [AVISO] O banco de dados já está populado. Seeding não será executado. ---");
            return;
        }

        System.out.println("[OK] Banco de dados vazio. Criando e salvando novas entidades...");

        // 1. Criar entidades primárias (sem dependências)
        List<UsuarioEntity> usuarios = createUsuarios();
        List<MateriaEntity> materias = createMaterias();
        List<TurmaEntity> turmas = createTurmas();

        // 2. Salvar entidades primárias para que tenham IDs
        usuarioRepository.saveAll(usuarios);
        materiaRepository.saveAll(materias);
        turmaRepository.saveAll(turmas);

        // 3. Criar e salvar entidades de relacionamento
        List<ProfessorMateriaEntity> alocacoes = createAlocacoesProfessores(usuarios, materias, turmas);
        professorMateriaRepository.saveAll(alocacoes);

        List<AlunoTurmaEntity> matriculas = createMatriculas(usuarios, turmas);
        alunoTurmaRepository.saveAll(matriculas);

        List<NotaEntity> notas = createNotas(matriculas, alocacoes);
        notaRepository.saveAll(notas);

        System.out.println("\n--- [SUCESSO] Banco de dados populado com dados de teste consistentes! ---");
    }

    private List<UsuarioEntity> createUsuarios() {
        System.out.println("   - Criando usuários...");
        List<UsuarioEntity> usuarios = new ArrayList<>();
        String senhaPadraoCriptografada = passwordEncoder.encode("123456");

        // Alunos
        String[] alunosNomes = {"Miguel Silva", "Arthur Costa", "Gael Almeida", "Heitor Martins", "Theo Oliveira", "Helena Pereira", "Alice Rodrigues", "Laura Ferreira", "Maria Alice Souza", "Sophia Gomes"};
        for (String nome : alunosNomes) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().replace(" ", ".") + "@aluno.com", senhaPadraoCriptografada, nome, PapelEnum.ALUNO));
        }

        // Professores
        String[] professoresNomes = {"Carlos Drummond", "Cecília Meireles", "Machado de Assis", "Clarice Lispector", "Graciliano Ramos"};
        for (String nome : professoresNomes) {
            usuarios.add(new UsuarioEntity(nome.toLowerCase().replace(" ", ".") + "@prof.com", senhaPadraoCriptografada, nome, PapelEnum.PROFESSOR));
        }

        // Gestão
        usuarios.add(new UsuarioEntity("monteiro.lobato@dir.com", senhaPadraoCriptografada, "Monteiro Lobato", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("augusto.anjos@admin.com", senhaPadraoCriptografada, "Augusto dos Anjos", PapelEnum.ADMIN));

        // Usuários de teste fixos
        usuarios.add(new UsuarioEntity("aluno.teste@gmail.com", senhaPadraoCriptografada, "Aluno Teste", PapelEnum.ALUNO));
        usuarios.add(new UsuarioEntity("professor.teste@gmail.com", senhaPadraoCriptografada, "Professor Teste", PapelEnum.PROFESSOR));
        usuarios.add(new UsuarioEntity("diretor.teste@gmail.com", senhaPadraoCriptografada, "Diretor Teste", PapelEnum.DIRETOR));
        usuarios.add(new UsuarioEntity("admin.teste@gmail.com", senhaPadraoCriptografada, "Admin Teste", PapelEnum.ADMIN));

        System.out.println("   - Criados " + usuarios.size() + " usuários.");
        return usuarios;
    }

    private List<MateriaEntity> createMaterias() {
        System.out.println("   - Criando matérias...");
        List<MateriaEntity> materias = List.of(
                new MateriaEntity("Matemática"), new MateriaEntity("Português"),
                new MateriaEntity("História"), new MateriaEntity("Geografia"), new MateriaEntity("Ciências")
        );
        materiaRepository.saveAll(materias);
        System.out.println("   - Criadas " + materias.size() + " matérias.");
        return materias;
    }

    private List<TurmaEntity> createTurmas() {
        System.out.println("   - Criando turmas...");
        List<TurmaEntity> turmas = List.of(
                new TurmaEntity("9º Ano", 'A', "Manhã", 2025),
                new TurmaEntity("9º Ano", 'B', "Tarde", 2025),
                new TurmaEntity("8º Ano", 'A', "Manhã", 2025)
        );
        turmaRepository.saveAll(turmas);
        System.out.println("   - Criadas " + turmas.size() + " turmas.");
        return turmas;
    }

    private List<ProfessorMateriaEntity> createAlocacoesProfessores(List<UsuarioEntity> usuarios, List<MateriaEntity> materias, List<TurmaEntity> turmas) {
        System.out.println("   - Alocando professores em matérias e turmas...");
        List<ProfessorMateriaEntity> alocacoes = new ArrayList<>();
        List<UsuarioEntity> professores = usuarios.stream().filter(u -> u.getPapel() == PapelEnum.PROFESSOR).toList();

        // Professor 0 (Carlos) -> Matemática -> Turmas 0 e 1
        alocacoes.add(new ProfessorMateriaEntity(professores.get(0), materias.get(0), turmas.get(0)));
        alocacoes.add(new ProfessorMateriaEntity(professores.get(0), materias.get(0), turmas.get(1)));

        // Professor 1 (Cecília) -> Português -> Turmas 0 e 2
        alocacoes.add(new ProfessorMateriaEntity(professores.get(1), materias.get(1), turmas.get(0)));
        alocacoes.add(new ProfessorMateriaEntity(professores.get(1), materias.get(1), turmas.get(2)));

        // Professor 2 (Machado) -> História -> Todas as turmas
        for (TurmaEntity turma : turmas) {
            alocacoes.add(new ProfessorMateriaEntity(professores.get(2), materias.get(2), turma));
        }

        System.out.println("   - Criadas " + alocacoes.size() + " alocações de professores.");
        return alocacoes;
    }

    private List<AlunoTurmaEntity> createMatriculas(List<UsuarioEntity> usuarios, List<TurmaEntity> turmas) {
        System.out.println("   - Matriculando alunos em turmas...");
        List<AlunoTurmaEntity> matriculas = new ArrayList<>();
        List<UsuarioEntity> alunos = usuarios.stream()
                .filter(u -> u.getPapel() == PapelEnum.ALUNO && !u.getLogin().contains("teste"))
                .toList();

        // Distribui os alunos pelas turmas de forma equilibrada
        for (int i = 0; i < alunos.size(); i++) {
            TurmaEntity turma = turmas.get(i % turmas.size()); // Ex: 0%3=0, 1%3=1, 2%3=2, 3%3=0, ...
            matriculas.add(new AlunoTurmaEntity(alunos.get(i), turma));
        }

        System.out.println("   - Criadas " + matriculas.size() + " matrículas de alunos.");
        return matriculas;
    }

    private List<NotaEntity> createNotas(List<AlunoTurmaEntity> matriculas, List<ProfessorMateriaEntity> alocacoes) {
        System.out.println("   - Gerando notas para os alunos...");
        List<NotaEntity> notas = new ArrayList<>();

        // Para cada matrícula (vínculo aluno-turma)
        for (AlunoTurmaEntity matricula : matriculas) {
            UsuarioEntity aluno = matricula.getUsuario();
            TurmaEntity turma = matricula.getTurma();

            // Encontra todas as matérias que são lecionadas naquela turma
            List<MateriaEntity> materiasDaTurma = alocacoes.stream()
                    .filter(alocacao -> alocacao.getTurma().getId().equals(turma.getId()))
                    .map(ProfessorMateriaEntity::getMateria)
                    .distinct()
                    .toList();

            // Para cada matéria da turma, cria uma nota para o aluno
            for (MateriaEntity materia : materiasDaTurma) {
                double notaAleatoria = ThreadLocalRandom.current().nextDouble(4.0, 10.0);
                BigDecimal notaFormatada = new BigDecimal(notaAleatoria).setScale(1, RoundingMode.HALF_UP);

                // Criando a nota (você pode querer adicionar a matéria na NotaEntity também)
                notas.add(new NotaEntity(notaFormatada.doubleValue(), turma, aluno));
            }
        }
        System.out.println("   - Criadas " + notas.size() + " notas.");
        return notas;
    }
}