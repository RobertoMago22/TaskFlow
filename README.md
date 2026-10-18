# TaskFlow — Gerenciador de Tarefas

Uma aplicação web para gerenciamento de tarefas, desenvolvida com **Java e Spring Boot** no backend e **HTML, CSS e JavaScript** no frontend, com banco de dados MySQL.

O projeto foi desenvolvido para praticar e demonstrar conhecimentos em desenvolvimento backend, construção de APIs REST, persistência de dados e integração entre frontend e backend.

## Demonstração

- **Aplicação online:** https://taskflow-frontend-6yva.onrender.com
- **Repositório:** https://github.com/RobertoMago22/TaskFlow

## Funcionalidades

- Cadastro e autenticação de usuários.
- Criação, consulta, atualização e exclusão de tarefas (CRUD).
- Associação das tarefas ao usuário autenticado.
- Organização das tarefas por status e prioridade.
- Busca e filtragem de tarefas.
- Validação de dados no backend.
- Controle de sessão utilizando `HttpSession`.
- Comunicação entre frontend e backend por requisições HTTP.

## Tecnologias utilizadas

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

### Frontend
- HTML5
- CSS3
- JavaScript
- Fetch API

### Banco de dados e infraestrutura
- MySQL
- Aiven — hospedagem do banco de dados
- Render — hospedagem da aplicação
- Git e GitHub — versionamento de código

## Arquitetura do projeto

O TaskFlow utiliza uma arquitetura separada entre frontend, backend e banco de dados.

- **Frontend:** interface responsável pela interação com o usuário e pelo envio de requisições HTTP.
- **Backend:** API REST desenvolvida com Spring Boot, responsável pelo processamento das requisições, validações e regras de negócio.
- **Banco de dados:** MySQL, utilizado para persistir usuários e tarefas.

O acesso aos dados é realizado por meio do Spring Data JPA e do Hibernate. A autenticação utiliza sessões HTTP para identificar o usuário nas requisições protegidas.

## Estrutura do repositório

```text
TaskFlow/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── style.css
│   ├── script.js
│   └── ...
├── .gitignore
└── README.md
```

*Observação: a estrutura acima é ilustrativa. Ajuste os nomes dos arquivos conforme a estrutura real do seu projeto.*

## Como executar localmente

### Pré-requisitos

- Java compatível com a versão utilizada no projeto.
- Maven.
- MySQL, local ou remoto.
- Navegador web.

### 1. Clone o repositório

```bash
git clone https://github.com/RobertoMago22/TaskFlow.git
cd TaskFlow
```

### 2. Configure o banco de dados

Configure as variáveis de ambiente utilizadas pelo Spring Boot para acessar o MySQL:

```properties
DB_URL=sua_url_do_banco
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

Utilize os nomes e formatos exigidos pela configuração real do projeto. Não publique credenciais reais no GitHub.

### 3. Execute o backend

Entre na pasta `backend` e execute:

```bash
mvn spring-boot:run
```

Verifique as configurações da aplicação para confirmar a porta utilizada pela API.

### 4. Execute o frontend

Abra os arquivos do frontend em um servidor web local, como o Live Server do Visual Studio Code.

Confira a URL da API configurada no JavaScript para garantir que corresponda ao ambiente em que o backend está sendo executado.

## Deploy

O projeto está publicado online, com o frontend e o backend hospedados e o banco de dados MySQL disponibilizado remotamente.

- Frontend: Render
- Backend: Render
- Banco de dados: Aiven

## Objetivo do projeto

O TaskFlow foi desenvolvido como projeto de estudo e portfólio, com o objetivo de consolidar conhecimentos em:

- Desenvolvimento backend com Java e Spring Boot.
- Criação e organização de APIs REST.
- Arquitetura em camadas: Controller, Service e Repository.
- Mapeamento objeto-relacional com JPA e Hibernate.
- Operações CRUD e persistência com MySQL.
- Autenticação baseada em sessão HTTP.
- Integração entre frontend e backend.
- Versionamento de código com Git e GitHub.
- Publicação de aplicações em ambientes online.

## Autor

**Roberto Mago de Souza**

- GitHub: https://github.com/RobertoMago22

Desenvolvido como parte da minha jornada de aprendizado e evolução em desenvolvimento de software, com foco em Java e backend.
