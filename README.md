# Projeto Sobrevidas Back-end (POC)

Repositório destinado à gerência de configuração e execução da Prova de Conceito (POC) do Back-end do Projeto Sobrevidas. A aplicação consiste em uma API RESTful para o gerenciamento de pacientes, incluindo processamento em lote via arquivos CSV.

## Introdução

### O que você precisa ter para rodar o projeto
- Sistema Operacional: Linux, macOS ou Windows
- Java versão 21
- Maven versão 3.8+ (ou utilizar o Wrapper embutido)
- Docker e Docker Compose
- Garantir que o Docker não precise de `sudo` para execução dos contêineres em ambientes Linux.

## Organização do projeto (diretórios)

    ./
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/pocsobrevidas/
    │   │   │       ├── controller/  # endpoints da API REST e documentação Swagger
    │   │   │       ├── domain/      # entidades de mapeamento objeto-relacional (JPA)
    │   │   │       ├── mapper/      # interfaces MapStruct para conversão DTO/Entidade
    │   │   │       ├── repository/  # persistência de dados e queries customizadas
    │   │   │       ├── requests/    # Data Transfer Objects (DTOs) de entrada
    │   │   │       ├── service/     # regras de negócio e lógica de importação de CSV
    │   │   │       └── PocSobrevidasApplication.java # classe inicial do Spring Boot
    │   │   └── resources/           # application.properties (configurações do Spring)
    │   └── test/                    # suíte de testes unitários (JUnit 5 + Mockito)
    │       └── java/
    │           └── com/example/pocsobrevidas/
    │               ├── controller/ 
    │               ├── repository/
    │               ├── service/ 
    │               └── util/        # factory classes (Creators) para isolamento de testes
    ├── docker-compose.yml           # configuração dos serviços Docker (PostgreSQL)
    └── pom.xml                      # arquivo de gerenciamento de dependências do Maven

##  Passo a Passo da Instalação e Execução

### 1. Clonando o Repositório
Para obter o código na sua máquina, abra o terminal e execute:
```bash
git clone https://github.com/EnzoViniski/poc-sobrevidas.git
cd poc-sobrevidas
```
### 2. Importando na IDE
* Abra a sua IDE (IntelliJ IDEA, Eclipse ou VS Code).
* Selecione a opção **"Open"** ou **"Import Project"**.
* Selecione a pasta `poc-sobrevidas` que você acabou de clonar.
* **Importante:** A IDE deve reconhecer automaticamente o arquivo `pom.xml` e começar a baixar as dependências do Maven. Aguarde o fim desse processo.

### 3. Conexão com o Banco de Dados (Docker)
A aplicação está pré-configurada para se conectar automaticamente ao banco PostgreSQL. Você não precisa alterar nenhuma configuração de senha, desde que suba o banco utilizando o nosso arquivo Docker.

No terminal, dentro da pasta raiz do projeto, suba o banco de dados:
```bash
docker compose up -d
```
*(O banco rodará na porta 5432, com o usuário `root`, senha `123` e database `sobrevidas_db`, exatamente como o Spring Boot espera no arquivo `application.properties`).*

---

###  Guia de Comandos Docker Úteis

Para todos os comandos abaixo, certifique-se de estar no diretório **raiz** do projeto (onde o arquivo `docker-compose.yml` está localizado).

* **Parar todos os contêineres definidos no projeto:**
```bash
docker compose stop
````
* **Parar e remover todos os contêineres e redes:**
```bash
docker compose down
```
* **Parar e remover todos os contêineres, redes e volumes associados (Atenção: isto apagará os dados do banco):**

```bash
docker compose down -v
```

* **Verificar os contêineres em execução:**
```bash
docker ps
```
* **Verificar logs do contêiner do banco de dados, seguindo em tempo real:**
```bash
docker logs -f sobrevidas_db
```
* **Entrar no banco de dados PostgreSQL rodando no Docker:**
```bash
docker exec -it sobrevidas_db psql -U root -d sobrevidas_db
```
---

##  Como Executar a Aplicação Spring Boot

Com o banco de dados rodando via Docker, você pode iniciar a aplicação de duas formas:

**Via IDE:**
Basta executar a classe principal `PocSobrevidasApplication.java` diretamente na sua IDE.

**Via Terminal (Maven):**
Na raiz do projeto, execute o comando:
mvn spring-boot:run
*(A aplicação estará inicializada e disponível na porta 8080)*

###  População Inicial do Banco (Primeiro Uso)

Para facilitar a avaliação e os testes da API, disponibilizamos um arquivo `pacientes.csv` na raiz deste repositório contendo uma carga inicial de dados reais/fictícios.

Para popular seu banco de dados local rapidamente em menos de 1 minuto:
1. Com a aplicação rodando, acesse o **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
2. Expanda o endpoint `POST /pacientes/importar`.
3. Clique no botão **"Try it out"**.
4. No campo de upload, clique em **"Escolher arquivo"** e selecione o arquivo `pacientes.csv` localizado na raiz deste projeto.
5. Clique no botão azul **"Execute"**.

Pronto! Seu banco de dados agora está populado com centenas de registros e pronto para você testar as rotas de listagem, buscas filtradas, atualizações e deleções com dados volumosos.
##  Documentação da API

A aplicação utiliza o **SpringDoc OpenAPI (Swagger)** para documentação e interface de testes interativa.

Com a aplicação em execução, acesse a documentação através do navegador:
* **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Lá, você encontrará todos os detalhes dos contratos de requisição e resposta para a API, incluindo:
* `POST /pacientes/importar`: Upload `multipart/form-data` do CSV para inserção em lote.
* `GET /pacientes/buscar/cpf/{cpf}`: Busca filtrada por cpf.
* Operações padrão de CRUD (`GET` listagem e por ID, `POST` criação manual, `PUT` atualização e `DELETE`).

##  Testes

O projeto conta com uma cobertura rigorosa de testes unitários nas camadas de Controller, Service e Repository. O isolamento de dependências foi garantido através do framework **Mockito**, e as validações de persistência utilizam um banco de dados em memória (**H2**).

Para executar a suíte de testes completa via terminal, utilize o comando:
```bash
mvn test
```