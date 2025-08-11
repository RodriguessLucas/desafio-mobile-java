# 📱 Desafio Técnico — App de Gestão Escolar (React Native)  
**Vaga para Bolsista**

---

## 🎯 Objetivo  
Desenvolver um aplicativo mobile em **React Native** que simula um sistema de **gestão escolar**, com funcionalidades distintas para **Aluno**, **Professor** e **Diretor**.  
O desafio busca avaliar:  
- Habilidades com React Native  
- Organização de código e boas práticas  
- Atenção a detalhes  

> **Diferencial:** Implementar backend real com **Java + Spring Boot** (opcionalmente pode ser fake).  

**Período:** 04/08 a 18/08/2025.

---

## 👥 Perfis e Funcionalidades  

### 👦 Aluno
- Login (simulado)  
- Visualizar **notas por disciplina**  
- Visualizar **dados da turma** (nome, série, professor responsável)  

### 👨‍🏫 Professor
- Login (simulado)  
- Visualizar **turmas que leciona**  
- Visualizar **alunos da turma**  
- **Cadastrar notas** por aluno e disciplina  

### 👨‍💼 Diretor
- Login (simulado)  
- Visualizar **todas as turmas**  
- Visualizar **todos os professores e alunos**  
- Visualizar **notas gerais** (modo leitura)  

---

## 🧾 Regras de Negócio
- Aluno: apenas suas próprias informações.  
- Professor: apenas notas de suas turmas.  
- Diretor: acesso total em leitura.  
- Turmas: múltiplos alunos e **1 professor responsável**.

---

## 🛠 Tecnologias Usadas  
- **Backend:** Java / Spring Boot  
- **Banco de Dados:** PostgreSQL  
- **Mobile App:** React Native (Expo)  
- **Deploy:**  
  - Backend: Render  
  - Mobile: Expo + Firebase  

---

## 📂 Estrutura e Referências  

- **Modelagem de Dados:** Simples, mas representando o uso real de um sistema escolar.  
- **Seeder:** Popula dados de forma automatizada (usuários, turmas, notas, professores).  

- **Design de Telas:** Parcialmente baseado no trabalho prático da UFC  
  - [Figma - Trabalho Prático ES 2025.2](https://www.figma.com/design/eMNaVvGUoBekdMPBYBHHhX/Trabalho-Pratico-02-ES-2025.2?node-id=0-1&t=a77x28bZOULz9N40-1)

---

## 🖼 Prints e Diagramas

### 📌 Diagrama do Banco de Dados
![Diagrama Banco de Dados](assets/diagrama-banco.png)

### 📱 Telas do Aplicativo

**Tela de Login**
![Tela Login](/midia/login.jpeg)

**Dashboard Aluno**
![Dashboard Aluno](/midia/dashboardAluno.jpeg)

**Dashboard Professor**
![Dashboard Professor](/midia/dashboardProfessor.jpeg)

**Dashboard Diretor**
![Dashboard Diretor](/midia/dashboardDiretor.jpeg)

---

## 🏗 Backend
- Arquitetura **MVC** (Spring) para REST APIs  
- Segurança nos endpoints (JWT + Roles: Aluno, Diretor, Admin, Professor)  
- Configuração de CORS restritiva  
- Criptografia de **senhas e IDs** com SHA-256  

---

## 📱 Frontend
- **React Native** com Expo  
- **Axios** para requisições  
- **SecureStore** para criptografar dados sensíveis (ex.: retorno de login)  

---

## 🧪 Como Testar  

### Usuários de Teste  
Todos com senha **123456**:  
    **diretor.teste@gmail.com**   
    **professor.teste@gmail.com**  
    **machado.de.assis@prof.com**  
    **admin.teste@gmail.com**  
    **aluno.teste@gmail.com**

#### OBSERVAÇÃO: o usuario machado.de.assis possui relacionamento com turma, logo para vizualização de dados na tela de dashboard de professor, é o ideal.


---

### Ambiente Local  

**Frontend:**
```bash
npx start
a  # para abrir no emulador Android

OBS: Ou escanear o QR Code pelo app do Expo no celular (rede local).
```

**Backend:**  
[Swagger Local](http://localhost:8080/swagger-ui/index.html)  

---

## 🚀 Deploy  

**Frontend:**  
[Firebase App Distribution](https://appdistribution.firebase.dev/i/8189cb170efbc944)  

**Backend:**  
[Swagger no Render](https://gestao-escolar-m4yq.onrender.com/swagger-ui/index.html)  

---

## ⚠️ Dificuldades  
- **Primeiro contato** com React Native → atraso no desenvolvimento por integrar endpoints junto às telas.  
- **Primeira configuração** de segurança e CORS no Spring Boot.  

> Apesar dos desafios, a experiência foi **enriquecedora** e agregou novos aprendizados.
