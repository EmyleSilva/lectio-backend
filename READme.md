## 🚀 Como rodar o Back-end (Projeto Lectio)

Este projeto utiliza Docker para subir a API em Java e o banco de dados PostgreSQL simultaneamente, sem precisar instalar nada além do próprio Docker.

### 1. Subindo o Servidor
Abra o terminal na pasta raiz do back-end e execute o comando abaixo:
`docker compose up --build -d`

A API estará disponível em: **http://localhost:8080**

> *Para desligar o servidor quando terminar de trabalhar, use o comando: `docker compose down`*

---

### 2. Acessando o Swagger (Testes de API)
Para testar as rotas de criação de usuários, livros e estantes sem precisar do front-end, acesse:
**http://localhost:8080/swagger-ui.html**

---

### 3. Acessando o Banco de Dados (pgAdmin)
O painel do banco de dados já sobe configurado junto com o Docker.
- **URL de acesso:** http://localhost:5050
- **E-mail:** [COLOQUE_AQUI_O_EMAIL_DO_DOCKER_COMPOSE]
- **Senha:** [COLOQUE_AQUI_A_SENHA_DO_DOCKER_COMPOSE]

**Conectando o seu Banco de Dados (LectioDB):**
Quando você entra pela primeira vez, o PgAdmin4 é apenas um painel vazio. Você precisa dizer a ele onde está o seu banco.

1. No canto superior esquerdo, clique com o botão direito em **Servers** e vá em **Register > Server...**
2. Vai abrir uma janelinha. Na aba **General**, no campo **Name**, digite o nome que você quiser dar para essa conexão (ex: Meu Banco Lectio).
3. Agora, mude para a aba **Connection** (é a aba do lado de General) e preencha exatamente assim:
   - **Host name/address:** `lectiodb` (Isso é muito importante! Como o PgAdmin e o PostgreSQL estão rodando dentro do Docker, eles não usam "localhost" para conversar entre si, eles usam o nome do contêiner).
   - **Port:** 5432 (já deve estar preenchido).
   - **Maintenance database:** lectio
   - **Username:** postgres
   - **Password:** postgres
   - *(Opcional: marque a caixinha "Save password" para não ter que digitar de novo depois)*.
4. Clique em **Save**.

**Visualizando suas Tabelas e Dados:**
Pronto! No menu lateral esquerdo, você vai ver o seu servidor conectado. 

- Para achar as tabelas que o script de 30/06 criou, expanda o menu clicando na setinha: **Servers > Meu Banco Lectio > Databases > lectio > Schemas > public > Tables**. 
- Lá estarão todas as suas 13 tabelas (usuarios, livros, estantes, etc). 
- Para ver os dados dentro de uma delas (por exemplo, usuarios), clique com o botão direito na tabela e selecione **View/Edit Data > All Rows**.