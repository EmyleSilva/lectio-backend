-- ============================================================
-- SEED DE DADOS DE TESTE — Lectio
-- Baseado nos livros que apareciam mockados no front-end antigo
-- (feed, biblioteca, perfil, avaliar-livro)
-- ============================================================
-- Senha de todos os usuários de teste: "123456"
-- (hash BCrypt já gerado abaixo, não precisa recriar)

-- ---------- USUÁRIOS ----------
INSERT INTO usuarios (nome, email, senha_hash, foto_perfil_url, bio) VALUES
('Mariana Silva', 'mariana@lectio.com', '$2b$12$aTqID/10VVpRAZCKHfLB7udLpqLEkUCbQVYMIGTTEPom4g0WfIQj.', NULL, 'Apaixonada por fantasia e ficção literária.'),
('Lucas Almeida', 'lucas@lectio.com', '$2b$12$tgKeGpAsfib6XJ7XeG8qL.oJNO5g/YBgF.DeEoKyha1Cb9aqvKYaW', NULL, 'Lendo um pouco de tudo.'),
('Ana Souza', 'ana@lectio.com', '$2b$12$ho9NC.h7ZtUdrCOQjwrETOSpOG0ABovpFb3pLqhiYHmcrLGke/Su6', NULL, 'Clássicos brasileiros são minha praia.');

-- ---------- EDITORAS ----------
INSERT INTO editoras (nome) VALUES
('Intrínseca'),
('HarperCollins Brasil'),
('Companhia das Letras'),
('Objetiva'),
('Editora Globo');

-- ---------- AUTORES ----------
INSERT INTO autores (nome, biografia, nacionalidade) VALUES
('R. F. Kuang', 'Autora premiada de fantasia e ficção literária, conhecida por The Poppy War e Babel.', 'Estadunidense/Chinesa'),
('J. R. R. Tolkien', 'Autor britânico, criador da Terra-média (O Hobbit, O Senhor dos Anéis).', 'Britânica'),
('Machado de Assis', 'Um dos maiores escritores da literatura brasileira, fundador da ABL.', 'Brasileira'),
('Chico Felitti', 'Jornalista brasileiro, autor do podcast e livro "O Podcast Feminista" e "O Massacre da Família Hope".', 'Brasileira'),
('Daniel Kahneman', 'Psicólogo e Nobel de Economia, autor de "Rápido e Devagar".', 'Israelense/Estadunidense'),
('Tahereh Mafi', 'Autora estadunidense de ficção distópica/YA, série Shatter Me.', 'Estadunidense');

-- ---------- GÊNEROS ----------
INSERT INTO generos (nome) VALUES
('Fantasia'),
('Ficção'),
('Romance'),
('Não-ficção'),
('Distopia'),
('Clássico Brasileiro'),
('True Crime');

-- ---------- LIVROS ----------
INSERT INTO livros (titulo, sinopse, isbn, ano_publicacao, idioma, numero_paginas, capa_url, editora_id) VALUES
('A Guerra da Papoula', 'Uma jovem órfã ingressa na academia militar mais prestigiada do império e descobre poderes xamânicos ligados a uma guerra devastadora.', '9788551008520', 2020, 'pt-BR', 528, 'https://m.media-amazon.com/images/I/613YunBw8yL._AC_UF1000,1000_QL80_.jpg', 1),
('A República do Dragão', 'Continuação de A Guerra da Papoula: Rin busca vingança em meio a uma guerra civil movida por deuses xamânicos.', '9788551009855', 2021, 'pt-BR', 704, 'https://m.media-amazon.com/images/I/818fnagZbWL._UF1000,1000_QL80_.jpg', 1),
('Katabasis', 'Dois estudantes de magia acadêmica descem ao inferno para resgatar seu professor e salvar suas carreiras.', '9788551012060', 2025, 'pt-BR', 480, 'https://skoob.s3.amazonaws.com/livros/122442868/KATABASIS_1738777025122442868SK-V11738777025B.jpg', 1),
('Yellowface', 'Uma escritora rouba o manuscrito de uma colega falecida e o publica como seu, expondo os bastidores tóxicos do mercado editorial.', '9786555656565', 2023, 'pt-BR', 336, 'https://m.media-amazon.com/images/I/71OFqSHm+8L._AC_UF1000,1000_QL80_.jpg', 2),
('O Hobbit', 'Bilbo Bolseiro é convocado para uma aventura inesperada ao lado de treze anões em busca de um tesouro guardado por um dragão.', '9788595084742', 1937, 'pt-BR', 310, 'https://m.media-amazon.com/images/I/91M9xPIf10L.jpg', 2),
('Dom Casmurro', 'Bentinho narra, já adulto, sua paixão de infância por Capitu e a dúvida que atormentou sua vida: ela o traiu ou não?', '9788535910663', 1899, 'pt-BR', 256, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfuZqvqcrTCvzd-gNcoBPIwnOr-lRXycivbg&s', 3),
('O Massacre da Família Hope', 'Investigação jornalística sobre o brutal assassinato de uma família em São Paulo, baseado no podcast de sucesso.', '9786555841234', 2023, 'pt-BR', 288, 'https://m.media-amazon.com/images/I/815bfY-u6-L.jpg', 4),
('Rápido e Devagar: Duas Formas de Pensar', 'O Nobel de Economia explora os dois sistemas que guiam nosso pensamento: o intuitivo/rápido e o racional/lento.', '9788539004119', 2011, 'pt-BR', 624, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0tJDOIsikze21T8buch-Nbo5cUGhUsoO8wA&s', 5),
('Estilhaça-me', 'Em um mundo distópico, Juliette possui um toque mortal e é mantida presa até ser recrutada por um regime opressor.', '9788580575678', 2011, 'pt-BR', 352, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPLhMd74x_nZGsiqPwFkl6RxtdMnMrFHeRcQ&s', 4);

-- ---------- LIVRO_AUTORES ----------
INSERT INTO livro_autores (livro_id, autor_id) VALUES
((SELECT id FROM livros WHERE titulo = 'A Guerra da Papoula'), (SELECT id FROM autores WHERE nome = 'R. F. Kuang')),
((SELECT id FROM livros WHERE titulo = 'A República do Dragão'), (SELECT id FROM autores WHERE nome = 'R. F. Kuang')),
((SELECT id FROM livros WHERE titulo = 'Katabasis'), (SELECT id FROM autores WHERE nome = 'R. F. Kuang')),
((SELECT id FROM livros WHERE titulo = 'Yellowface'), (SELECT id FROM autores WHERE nome = 'R. F. Kuang')),
((SELECT id FROM livros WHERE titulo = 'O Hobbit'), (SELECT id FROM autores WHERE nome = 'J. R. R. Tolkien')),
((SELECT id FROM livros WHERE titulo = 'Dom Casmurro'), (SELECT id FROM autores WHERE nome = 'Machado de Assis')),
((SELECT id FROM livros WHERE titulo = 'O Massacre da Família Hope'), (SELECT id FROM autores WHERE nome = 'Chico Felitti')),
((SELECT id FROM livros WHERE titulo = 'Rápido e Devagar: Duas Formas de Pensar'), (SELECT id FROM autores WHERE nome = 'Daniel Kahneman')),
((SELECT id FROM livros WHERE titulo = 'Estilhaça-me'), (SELECT id FROM autores WHERE nome = 'Tahereh Mafi'));

-- ---------- LIVRO_GENEROS ----------
INSERT INTO livro_generos (livro_id, genero_id) VALUES
((SELECT id FROM livros WHERE titulo = 'A Guerra da Papoula'), (SELECT id FROM generos WHERE nome = 'Fantasia')),
((SELECT id FROM livros WHERE titulo = 'A República do Dragão'), (SELECT id FROM generos WHERE nome = 'Fantasia')),
((SELECT id FROM livros WHERE titulo = 'Katabasis'), (SELECT id FROM generos WHERE nome = 'Fantasia')),
((SELECT id FROM livros WHERE titulo = 'Yellowface'), (SELECT id FROM generos WHERE nome = 'Ficção')),
((SELECT id FROM livros WHERE titulo = 'O Hobbit'), (SELECT id FROM generos WHERE nome = 'Fantasia')),
((SELECT id FROM livros WHERE titulo = 'Dom Casmurro'), (SELECT id FROM generos WHERE nome = 'Clássico Brasileiro')),
((SELECT id FROM livros WHERE titulo = 'Dom Casmurro'), (SELECT id FROM generos WHERE nome = 'Romance')),
((SELECT id FROM livros WHERE titulo = 'O Massacre da Família Hope'), (SELECT id FROM generos WHERE nome = 'True Crime')),
((SELECT id FROM livros WHERE titulo = 'O Massacre da Família Hope'), (SELECT id FROM generos WHERE nome = 'Não-ficção')),
((SELECT id FROM livros WHERE titulo = 'Rápido e Devagar: Duas Formas de Pensar'), (SELECT id FROM generos WHERE nome = 'Não-ficção')),
((SELECT id FROM livros WHERE titulo = 'Estilhaça-me'), (SELECT id FROM generos WHERE nome = 'Distopia'));

-- ---------- ESTANTES (uma padrão de cada tipo, por usuário) ----------
INSERT INTO estantes (usuario_id, nome, tipo, descricao) VALUES
((SELECT id FROM usuarios WHERE email = 'mariana@lectio.com'), 'Quero Ler', 'padrao', 'Livros na fila'),
((SELECT id FROM usuarios WHERE email = 'mariana@lectio.com'), 'Lendo', 'padrao', 'Leituras atuais'),
((SELECT id FROM usuarios WHERE email = 'mariana@lectio.com'), 'Lidos', 'padrao', 'Já concluídos'),
((SELECT id FROM usuarios WHERE email = 'lucas@lectio.com'), 'Quero Ler', 'padrao', 'Livros na fila'),
((SELECT id FROM usuarios WHERE email = 'lucas@lectio.com'), 'Lendo', 'padrao', 'Leituras atuais'),
((SELECT id FROM usuarios WHERE email = 'ana@lectio.com'), 'Lidos', 'padrao', 'Já concluídos');

-- ---------- ESTANTE_LIVROS (refletindo o mock do front: Mariana lendo Guerra da Papoula, Hobbit na fila...) ----------
INSERT INTO estante_livros (estante_id, livro_id, status_leitura, pagina_atual) VALUES
(
  (SELECT id FROM estantes WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'mariana@lectio.com') AND nome = 'Lendo'),
  (SELECT id FROM livros WHERE titulo = 'A Guerra da Papoula'),
  'lendo', 222
),
(
  (SELECT id FROM estantes WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'mariana@lectio.com') AND nome = 'Quero Ler'),
  (SELECT id FROM livros WHERE titulo = 'O Hobbit'),
  'quero_ler', 0
),
(
  (SELECT id FROM estantes WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'mariana@lectio.com') AND nome = 'Lidos'),
  (SELECT id FROM livros WHERE titulo = 'Yellowface'),
  'lido', 336
),
(
  (SELECT id FROM estantes WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'lucas@lectio.com') AND nome = 'Lendo'),
  (SELECT id FROM livros WHERE titulo = 'A República do Dragão'),
  'lendo', 150
),
(
  (SELECT id FROM estantes WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'ana@lectio.com') AND nome = 'Lidos'),
  (SELECT id FROM livros WHERE titulo = 'Dom Casmurro'),
  'lido', 256
);

-- ---------- AVALIAÇÕES ----------
INSERT INTO avaliacoes (usuario_id, livro_id, nota, resenha_texto, contem_spoiler) VALUES
((SELECT id FROM usuarios WHERE email = 'mariana@lectio.com'), (SELECT id FROM livros WHERE titulo = 'Yellowface'), 5, 'Impossível largar, uma crítica ácida ao mercado editorial.', FALSE),
((SELECT id FROM usuarios WHERE email = 'mariana@lectio.com'), (SELECT id FROM livros WHERE titulo = 'A Guerra da Papoula'), 4, 'Pesado emocionalmente, mas muito bem construído.', TRUE),
((SELECT id FROM usuarios WHERE email = 'ana@lectio.com'), (SELECT id FROM livros WHERE titulo = 'Dom Casmurro'), 5, 'Capitu traiu ou não? Releio esse livro todo ano.', FALSE);
