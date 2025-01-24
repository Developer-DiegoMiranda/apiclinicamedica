# Projeto: Clínica Médica

Este projeto é uma aplicação desenvolvida em **Java** utilizando o framework **Spring Boot** e **Maven** para gerenciar dependências. A IDEA utilizada foi o **IntelliJ** excelente para trabalhar com JAVA e o banco de dados utilizado foi o **MySQL**. A aplicação permite gerenciar médicos e pacientes, incluindo funcionalidades como cadastro, atualização, exclusão/inativação, e listagem.

## Funcionalidades

A aplicação disponibiliza endpoints REST para as seguintes operações, testáveis por ferramentas como **Insomnia**:

### Autenticação
- **POST** - Efetuar login para gerar o token de acesso:
  ```
  http://localhost:8080/login
  ```

### Médicos
- **POST** - Cadastro de médicos:
  ```
  http://localhost:8080/medicos
  ```
- **GET** - Listagem de médicos:
  ```
  http://localhost:8080/medicos
  ```
- **GET** - Detalhar médico:
  ```
  http://localhost:8080/medicos/{id}
  ```
- **PUT** - Atualizar informações de um médico:
  ```
  http://localhost:8080/medicos
  ```
- **DELETE** - Inativar médico:
  ```
  http://localhost:8080/medicos/{id}
  ```

### Pacientes
- **POST** - Cadastro de pacientes:
  ```
  http://localhost:8080/pacientes
  ```
- **GET** - Detalhar paciente:
  ```
  http://localhost:8080/pacientes/{id}
  ```
- **PUT** - Atualizar informações de um paciente:
  ```
  http://localhost:8080/pacientes
  ```

## Estrutura do Projeto

A organização do projeto segue as boas práticas do Spring Boot, com as seguintes pastas e pacotes principais:

```
src/main/java/com/med.voll.api/clinicamedica
|
├── controller
|   ├── MedicoController.java
|   ├── PacienteController.java
|   └── AutenticacaoController.java
|
├── domain
|   ├── medico
|   │   ├── Medico.java
|   │   └── MedicoRepository.java
|   │   └── Especialidade.java
|   │   └── DadosListagemMedico.java
|   │   └── DadosDetalhamentoMedico.java
|   │   └── DadosCadastroMedico.java
|   │   └── DadosAtualizacaoMedico.java
|   ├── paciente
|   │   ├── Paciente.java
|   │   └── PacienteRepository.java
|   │   └── DadosDetalhamentoPaciente.java
|   │   └── DadosCadastroPacientes.java
|   │   └── DadosAtualizacaoPaciente.java
|   └── usuario
|       ├── Usuario.java
|       └── UsuarioRepository.java
|       └── DadosAutenticacao.java
|       └── AutenticacaoService.java
├── infra
|   └── exception
|       ├── TratadorDeErros.java
|   └── security
|   ├── SecurityConfigurations.java
|   ├── SecurityFilter.java
|   └── TokenService.java
├── security
|   ├── DadosTokenJWT.java
|ApiApplication
└── resources
    └── db.migration
        ├── V1__create-table-medicos.sql
        ├── V2__alter-table-medicos-add-column-telefone.sql
        ├── V3__create-table-pacientes.sql
        ├── V4__alter-table-medicos-add-column-ativo.sql
        └── V5__create-table-usuarios.sql
```

## Banco de Dados

As tabelas utilizadas no banco de dados MySQL foram criadas através de scripts na pasta `resources/db.migration`.




### Passos para Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/Developer-DiegoMiranda/clinicamedica.git
   ```
2. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/clinicamedica
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```
3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```
4. Teste os endpoints utilizando **Insomnia** ou outra ferramenta similar.

## Considerações Finais

Este projeto foi desenvolvido com o objetivo de demonstrar uma aplicação funcional que utiliza autenticação via JWT e implementa operações básicas de um sistema de gestão de clínica médica. Feedbacks e contribuições são bem-vindos!
