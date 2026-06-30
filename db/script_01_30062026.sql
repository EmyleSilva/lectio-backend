CREATE TABLE usuarios (
    id              SERIAL PRIMARY KEY,
    nome            VARCHAR(120) NOT NULL,
    email           VARCHAR(160) NOT NULL UNIQUE,
    senha_hash      VARCHAR(255) NOT NULL,
    foto_perfil_url VARCHAR(255),
    bio             TEXT,
    data_cadastro   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE editoras (
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE autores (
    id              SERIAL PRIMARY KEY,
    nome            VARCHAR(160) NOT NULL,
    biografia       TEXT,
    data_nascimento DATE,
    nacionalidade   VARCHAR(80)
);

CREATE TABLE generos (
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE livros (
    id              SERIAL PRIMARY KEY,
    titulo          VARCHAR(255) NOT NULL,
    sinopse         TEXT,
    isbn            VARCHAR(20) UNIQUE,
    ano_publicacao  INT,
    idioma          VARCHAR(40),
    numero_paginas  INT,
    capa_url        VARCHAR(255),
    editora_id      INT REFERENCES editoras(id) ON DELETE SET NULL
);

CREATE TABLE livro_autores (
    livro_id INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    autor_id INT NOT NULL REFERENCES autores(id) ON DELETE CASCADE,
    PRIMARY KEY (livro_id, autor_id)
);

CREATE TABLE livro_generos (
    livro_id  INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    genero_id INT NOT NULL REFERENCES generos(id) ON DELETE CASCADE,
    PRIMARY KEY (livro_id, genero_id)
);

CREATE TABLE estantes (
    id            SERIAL PRIMARY KEY,
    usuario_id    INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    nome          VARCHAR(80) NOT NULL,
    tipo          VARCHAR(20) NOT NULL DEFAULT 'customizada', -- 'padrao' | 'customizada'
    descricao     TEXT,
    data_criacao  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE estante_livros (
    id                  SERIAL PRIMARY KEY,
    estante_id          INT NOT NULL REFERENCES estantes(id) ON DELETE CASCADE,
    livro_id            INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    status_leitura      VARCHAR(20) NOT NULL DEFAULT 'quero_ler',
        -- 'quero_ler' | 'lendo' | 'lido' | 'pausado' | 'abandonado'
    data_adicionado     TIMESTAMP NOT NULL DEFAULT NOW(),
    data_inicio_leitura DATE,
    data_fim_leitura    DATE,
    pagina_atual        INT DEFAULT 0,
    UNIQUE (estante_id, livro_id)
);

CREATE TABLE historico_leituras (
    id           SERIAL PRIMARY KEY,
    usuario_id   INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    livro_id     INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    data_inicio  DATE,
    data_fim     DATE,
    status_final VARCHAR(20) NOT NULL -- 'lido' | 'abandonado'
);

CREATE TABLE atualizacoes_leitura (
    id               SERIAL PRIMARY KEY,
    usuario_id       INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    livro_id         INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    estante_livro_id INT REFERENCES estante_livros(id) ON DELETE SET NULL,
    pagina_atual     INT,
    percentual       DECIMAL(5,2),
    comentario       TEXT,
    data             TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE metas_leitura (
    id                SERIAL PRIMARY KEY,
    usuario_id        INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    ano               INT NOT NULL,
    tipo_meta         VARCHAR(20) NOT NULL DEFAULT 'livros', -- 'livros' | 'paginas'
    quantidade_meta   INT NOT NULL,
    quantidade_atual  INT NOT NULL DEFAULT 0,
    data_criacao      TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (usuario_id, ano, tipo_meta)
);

CREATE TABLE avaliacoes (
    id             SERIAL PRIMARY KEY,
    usuario_id     INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    livro_id       INT NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    nota           SMALLINT CHECK (nota BETWEEN 1 AND 5),
    resenha_texto  TEXT,
    contem_spoiler BOOLEAN NOT NULL DEFAULT FALSE,
    data           TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (usuario_id, livro_id)
);