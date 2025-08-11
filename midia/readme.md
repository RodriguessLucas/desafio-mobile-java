# 📱 Desafio Técnico - App de Gestão Escolar (React Native) - Vaga P/ Bolsista

# Tópicos:
    OBJETIVO;
    PERFIL E FUNCIONALIDADE
    TECNOLOGIAs USADAs
    COMO FOI ESTRUTURADO E REFERENCIAS
    COMO TESTAR
        LOCAL
        DEPLOY

# 🎯 Objetivo:
- Desenvolver um aplicativo mobile em React Native que simula um sistema de gestão escolar, com diferentes funcionalidades para aluno, professor e diretor.

- Este desafio visa avaliar suas habilidades com React Native, organização de código, boas práticas e atenção a detalhes. O backend pode ser fake (simulado), mas é um diferencial implementar um backend real com Java + Spring Boot.

- Desafio: Início 04/08 até dia 18/08/2025.

# 👥 Perfis e Funcionalidades:
👦 Aluno
 - Fazer login (simulado)

 - Ver suas notas por disciplina

 - Ver dados da turma (nome, série, professor responsável)

👨‍🏫 Professor
 - Fazer login (simulado)

 - Ver lista das turmas que leciona

 - Ver alunos da turma

 - Cadastrar notas por aluno e disciplina

👨‍💼 Diretor
 - Fazer login (simulado)

 - Ver todas as turmas

 - Ver todos os professores e alunos

 - Ver notas gerais dos alunos (modo leitura)

🧾 Regras de Negócio: 
- Um aluno só pode ver suas próprias informações.

- Um professor só pode cadastrar/ver notas dos alunos de suas turmas.

- O diretor tem acesso a todos os dados, mas não edita notas.

- As turmas podem ter múltiplos alunos e um professor responsável.



# Tecnologias Usadas
    BACKEND - JAVA/SPRING BOOT 
    BANCO DE DADOS - POSTGRES 
    MOBILE APP - REACT NATIVE(EXPO)

    DEPLOY:
        BACKEND + DADOS - RENDER
        MOBILE APP: EXPO + FIREBASE


# COMO FOI ESTRUTURADO E REFERENCIAS

NA MODELAGEM DE DADOS, FOI FEITO ALGO SIMPLES MAS que consiga representar o uso do dia a dia de um sistema escolar, segue a img abaixo da modelagem:

E QUANTO a popular os dados para que possamos simular o funcionamento real, foi desenvoltido um data base seeder, onde é gerados uma quantidade razoavel e usuarios e acoes feitas pelos mesmos(participarem de uma turma, terem notas, ministrar aulas e etc)


NO BACK END ULTILIZAMOS A ARQUITETURA MVC(PADRAO DO SPRING) voltada para REST APIS, com segurança nos endpoints, onde cada tipo de usuario(ALUNO, DIRETOR, ADMIN E PROFESSOR) so poderiam acessar os endpoints tendo o token de autenticação e a role pertencente.
COMO FOI ultilziado  a camada de segurança, protegemos os endpoints e configuramos o cors, onde permite e delega apenas tentativas de acesso que foram previamente descritas.
ULTILIZAMOS Tambem criptografia para SENHAS e IDs, pois é o padrão ultilizado para dificultar o possivel invasor de conseguir ter conhecimento do banco de dados. E para isso ultilizamos o sha256, onde por padrão nunca repete o valor criptografado, por mais que antes de serem criptografados sejam a mesma senha "123456" por exemplo, quando criptografadas nunca terao o mesmo hash.








