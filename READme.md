# 📚 Lectio — Back-end

API REST do Lectio, aplicação para leitores registrarem, organizarem em estantes e avaliarem os livros que leem. Este serviço é consumido pelo [front-end em Angular](https://github.com/EmyleSilva/lectio) do projeto.

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 4.1.0**
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-webmvc`
  - `spring-boot-starter-validation`
  - `spring-security-crypto` (apenas o hashing de senha, sem autenticação de rotas)
- **PostgreSQL 16**
- **Lombok**
- **springdoc-openapi** (Swagger UI)
- **Maven**
- **Docker / Docker Compose**

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas tradicional do Spring Boot:

```
Controller  →  Service  →  Repository  →  Model (JPA Entity)
                  ↑
                DTO (Request / Response)
```

- **`controller/`** — expõe os endpoints REST, faz apenas validação de entrada (`@Valid`) e delega para os serviços.
- **`service/`** — regra de negócio, transações (`@Transactional`) e conversão entre entidades e DTOs.
- **`repository/`** — interfaces `JpaRepository` (Spring Data JPA) para acesso ao banco.
- **`model/`** — entidades JPA mapeadas para as tabelas do PostgreSQL.
- **`dto/`** — records de request/response, separando o contrato da API do modelo de persistência.
- **`exception/`** — exceções customizadas + `@RestControllerAdvice` para respostas de erro padronizadas.
- **`config/`** — configuração de CORS e do bean de hashing de senha (BCrypt).

### Estrutura de pastas

```
src/main/java/uesc/web/lectio/
├── LectioApplication.java
├── config/
│   ├── CorsConfig.java          # libera CORS para todas as origens
│   └── SecurityBeansConfig.java # bean do PasswordEncoder (BCrypt)
├── controller/
│   ├── AuthController.java
│   ├── UsuarioController.java
│   ├── LivroController.java
│   ├── AutorController.java
│   ├── EditoraController.java
│   ├── GeneroController.java
│   ├── EstanteController.java
│   └── EstanteLivroController.java
├── dto/
│   ├── AuthDTO.java / UsuarioDTO.java / LivroDTO.java
│   ├── AutorDTO.java / EditoraDTO.java / GeneroDTO.java
│   └── EstanteDTO.java / EstanteLivroDTO.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── AuthenticationFailedException.java
├── model/
│   ├── Usuario.java / Livro.java / Autor.java / Editora.java / Genero.java
│   ├── Estante.java / EstanteLivro.java
│   └── enums/
│       ├── StatusLeitura.java
│       └── TipoEstante.java
├── repository/
│   └── (um JpaRepository por entidade)
└── service/
    └── (um Service por entidade)

db/
├── script_01_30062026.sql   # DDL — cria as tabelas
└── seed_02_livros.sql       # dados de exemplo (seed)
```

---

## 🚀 Como rodar

### Pré-requisitos

- [Docker](https://www.docker.com/) e Docker Compose

> Alternativamente, para rodar sem Docker: Java 21, Maven e uma instância PostgreSQL 16 acessível localmente.

### Subindo com Docker Compose (recomendado)

Na raiz do projeto:

```bash
docker compose up --build -d
```

Isso sobe três serviços:

| Serviço      | Descrição                                  | Porta local |
|--------------|----------------------------------------------|-------------|
| `lectio-app` | API Spring Boot                              | `8080`      |
| `lectiodb`   | PostgreSQL 16 (banco `lectio`)               | `5432`      |
| `pgadmin4`   | Painel web para o PostgreSQL                 | `15432`     |

Na **primeira vez** que o volume do banco é criado, os scripts `db/script_01_30062026.sql` (schema) e `db/seed_02_livros.sql` (dados de exemplo) são executados automaticamente.

Para derrubar o ambiente:

```bash
docker compose down
```

> Para apagar também os dados do banco (recomeçar do zero): `docker compose down -v`

### Acessando a API e a documentação

- **API:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI (JSON):** `http://localhost:8080/v3/api-docs`

### Acessando o pgAdmin

- **URL:** `http://localhost:15432`
- **E-mail:** `admin@admin.com`
- **Senha:** `admin`

Para conectar ao banco dentro do pgAdmin (**Servers → Register → Server...**):

| Campo (aba Connection) | Valor       |
|-------------------------|-------------|
| Host name/address        | `lectiodb`  |
| Port                     | `5432`      |
| Maintenance database     | `lectio`    |
| Username                 | `postgres`  |
| Password                 | `postgres`  |

> O host é `lectiodb` (nome do container), não `localhost`, pois o pgAdmin e o Postgres conversam dentro da rede interna do Docker.

### Rodando localmente sem Docker

```bash
./mvnw spring-boot:run
```

Nesse caso, a conexão usada é a de `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/lectio
    username: postgres
    password: postgres
```

Você precisa ter um PostgreSQL local rodando com esses dados e as tabelas de `db/script_01_30062026.sql` já criadas.

### Rodando os testes

```bash
./mvnw test
```

---

## 🗄️ Modelo de dados

Entidades mapeadas por JPA e expostas pela API:

| Entidade         | Tabela            | Descrição                                                        |
|-------------------|-------------------|---------------------------------------------------------------------|
| `Usuario`          | `usuarios`         | Conta do leitor (nome, e-mail, senha com hash, bio, foto)          |
| `Livro`            | `livros`           | Catálogo de livros (título, sinopse, ISBN, editora, autores, gêneros) |
| `Autor`            | `autores`          | Autor de livro                                                     |
| `Editora`          | `editoras`         | Editora do livro                                                   |
| `Genero`           | `generos`          | Gênero literário                                                   |
| `Estante`          | `estantes`         | Coleção de livros criada por um usuário                            |
| `EstanteLivro`     | `estante_livros`   | Relação livro ↔ estante, com status e progresso de leitura         |

Relacionamentos N:N (`livro_autores`, `livro_generos`) são resolvidos via tabelas associativas.

> O schema (`db/script_01_30062026.sql`) também define as tabelas `historico_leituras`, `atualizacoes_leitura`, `metas_leitura` e `avaliacoes`, mas **ainda não há entidades/controllers para elas** — são estrutura já preparada no banco para funcionalidades futuras (histórico de leitura, metas, avaliações de livro).

### Enums

```java
enum StatusLeitura { QUERO_LER, LENDO, LIDO, PAUSADO, ABANDONADO }
enum TipoEstante   { PADRAO, CUSTOMIZADA }
```

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080`

### Autenticação

| Método | Endpoint  | Descrição             | Corpo (request)                          | Resposta (200)                             |
|--------|-----------|-------------------------|---------------------------------------------|-----------------------------------------------|
| `POST` | `/login`  | Autentica um usuário    | `{ "email": string, "senha": string }`       | `{ "id": number, "nome": string, "email": string }` |

Retorna **401** (`AuthenticationFailedException`) se e-mail/senha não conferirem.

### Usuários

| Método   | Endpoint          | Descrição                | Corpo (request)                                                     | Resposta                          |
|----------|--------------------|-----------------------------|--------------------------------------------------------------------------|---------------------------------------|
| `POST`   | `/usuarios`        | Cria um usuário             | `{ nome, email, senha }`                                                  | `201` + usuário criado                |
| `GET`    | `/usuarios`        | Lista todos os usuários      | —                                                                          | `200` + lista                         |
| `GET`    | `/usuarios/{id}`   | Busca usuário por id         | —                                                                          | `200` + usuário / `404`               |
| `PUT`    | `/usuarios/{id}`   | Atualiza dados do usuário     | `{ nome, fotoPerfilUrl, bio }` *(sem senha)*                               | `200` + usuário atualizado / `404`    |
| `DELETE` | `/usuarios/{id}`   | Remove um usuário            | —                                                                          | `204` / `404`                         |

Resposta (`UsuarioDTO.Response`): `{ id, nome, email, fotoPerfilUrl, bio, dataCadastro }`

### Livros

| Método   | Endpoint         | Descrição                                   | Corpo (request)                                                                                          | Resposta                     |
|----------|-------------------|------------------------------------------------|---------------------------------------------------------------------------------------------------------------|-----------------------------------|
| `POST`   | `/livros`         | Cadastra um livro                                | `{ titulo, sinopse, isbn, anoPublicacao, idioma, numeroPaginas, capaUrl, editoraId, autorIds[], generoIds[] }` | `201` + livro criado             |
| `GET`    | `/livros?titulo=` | Lista livros (filtro opcional por título)        | —                                                                                                               | `200` + lista                    |
| `GET`    | `/livros/{id}`    | Busca livro por id                                | —                                                                                                               | `200` + livro / `404`            |
| `PUT`    | `/livros/{id}`    | Atualiza um livro                                 | igual ao `POST`                                                                                                 | `200` + livro atualizado / `404` |
| `DELETE` | `/livros/{id}`    | Remove um livro                                    | —                                                                                                               | `204` / `404`                    |

Resposta (`LivroDTO.Response`) inclui `editora`, `autores[]` e `generos[]` já expandidos.

### Autores / Editoras / Gêneros

Os três seguem o mesmo padrão CRUD:

| Método   | Endpoint                | Descrição            |
|----------|---------------------------|--------------------------|
| `POST`   | `/autores` `/editoras` `/generos`       | Cria                     |
| `GET`    | `/autores` `/editoras` `/generos`       | Lista todos              |
| `GET`    | `/autores/{id}` `/editoras/{id}` `/generos/{id}` | Busca por id       |
| `PUT`    | `/autores/{id}` `/editoras/{id}` `/generos/{id}` | Atualiza           |
| `DELETE` | `/autores/{id}` `/editoras/{id}` `/generos/{id}` | Remove              |

- `/autores` aceita `?nome=` para filtrar por nome (busca parcial, case-insensitive).
- `AutorDTO.Request`: `{ nome, biografia, dataNascimento, nacionalidade }`
- `EditoraDTO.Request` / `GeneroDTO.Request`: `{ nome }`

### Estantes *(aninhado em usuário)*

| Método   | Endpoint                              | Descrição                          | Corpo (request)                         |
|----------|------------------------------------------|---------------------------------------|----------------------------------------------|
| `POST`   | `/usuarios/{usuarioId}/estantes`         | Cria uma estante para o usuário        | `{ nome, tipo, descricao }`                    |
| `GET`    | `/usuarios/{usuarioId}/estantes`         | Lista as estantes do usuário            | —                                              |
| `GET`    | `/usuarios/{usuarioId}/estantes/{id}`    | Busca uma estante do usuário            | —                                              |
| `PUT`    | `/usuarios/{usuarioId}/estantes/{id}`    | Atualiza uma estante                     | `{ nome, tipo, descricao }`                    |
| `DELETE` | `/usuarios/{usuarioId}/estantes/{id}`    | Remove uma estante                       | —                                              |

`tipo` é um dos valores de `TipoEstante` (`PADRAO`, `CUSTOMIZADA`).

### Livros de uma estante *(aninhado em estante)*

| Método   | Endpoint                                          | Descrição                                     | Corpo (request)                                                                |
|----------|------------------------------------------------------|--------------------------------------------------|-------------------------------------------------------------------------------------|
| `POST`   | `/estantes/{estanteId}/livros`                       | Adiciona um livro à estante                        | `{ livroId, statusLeitura? }` *(default: `QUERO_LER`)*                                |
| `GET`    | `/estantes/{estanteId}/livros`                       | Lista os livros da estante                          | —                                                                                     |
| `PATCH`  | `/estantes/{estanteId}/livros/{estanteLivroId}`      | Atualiza status/progresso de leitura                | `{ statusLeitura?, dataInicioLeitura?, dataFimLeitura?, paginaAtual? }`                |
| `DELETE` | `/estantes/{estanteId}/livros/{estanteLivroId}`      | Remove o livro da estante                            | —                                                                                     |

Resposta (`EstanteLivroDTO.Response`): `{ id, estanteId, livro: { id, titulo, capaUrl, numeroPaginas }, statusLeitura, dataAdicionado, dataInicioLeitura, dataFimLeitura, paginaAtual }`

O `POST` retorna **400** se o livro já estiver na estante (atualmente sem mensagem de erro padronizada — ver seção de limitações abaixo).

---

## ⚠️ Tratamento de erros

O `GlobalExceptionHandler` padroniza as respostas de erro no formato:

```json
{
  "timestamp": "...",
  "status": 404,
  "erro": "Not Found",
  "mensagem": "..."
}
```

| Situação                                   | Status HTTP | Exceção                          |
|----------------------------------------------|-------------|-------------------------------------|
| Recurso não encontrado (id inválido)           | `404`       | `ResourceNotFoundException`          |
| Corpo da requisição inválido (`@Valid`)         | `400`       | `MethodArgumentNotValidException` *(inclui mapa `campos` com o erro de cada campo)* |
| Login com credenciais inválidas                 | `401`       | `AuthenticationFailedException`      |

> **Observação:** algumas regras de negócio (e-mail já cadastrado, livro duplicado numa estante) lançam `IllegalArgumentException`, que **não tem um handler dedicado** no `GlobalExceptionHandler` — hoje esses casos resultam em erro `500` genérico em vez de `400` com mensagem clara. Vale adicionar um `@ExceptionHandler(IllegalArgumentException.class)` para cobrir esse cenário.

---

## 🔐 Segurança

- Senhas são armazenadas com hash **BCrypt** (`SecurityBeansConfig`).
- `spring-boot-starter-security` está **comentado no `pom.xml`** — ou seja, **não há autenticação/autorização nas rotas** hoje: qualquer cliente pode chamar qualquer endpoint sem token/sessão.
- CORS está liberado para **todas as origens** (`CorsConfig`, `allowedOrigins("*")`), com suporte a `GET`, `POST`, `PUT`, `PATCH`, `DELETE`.

Esses dois pontos são adequados para desenvolvimento/protótipo, mas devem ser revistos antes de um deploy em produção (habilitar Spring Security, restringir CORS ao domínio do front-end, adicionar autenticação por token).
