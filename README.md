# SpaceAlert API - Global Solution 2026/1

## Integrantes

| Nome               | RM       |
| ------------------ | -------- |
| Henrique Pecora    | RM556612 |
| Santhiago de Gobbi | RM98420  |

---

## Descrição da Solução

O **SpaceAlert API** é uma solução desenvolvida para a Global Solution 2026/1, cujo tema é a economia espacial.

A proposta do projeto é utilizar o conceito de monitoramento por satélite para registrar regiões monitoradas e alertas ambientais ou climáticos. A aplicação permite cadastrar regiões, registrar alertas associados a essas regiões e consultar as informações persistidas em banco de dados.

A solução está relacionada ao uso de dados e infraestrutura espacial para resolver problemas reais na Terra, como prevenção de desastres, monitoramento ambiental e acompanhamento de áreas de risco.

---

## Objetivo da Entrega de DevOps

A entrega de DevOps Tools & Cloud Computing tem como objetivo conteinerizar uma aplicação Java utilizando Docker e Docker Compose, criando um ambiente em nuvem com dois containers integrados:

* um container para a aplicação Java Spring Boot;
* um container para o banco de dados PostgreSQL.

A aplicação foi executada em uma máquina virtual Ubuntu na Azure.

---

## Tecnologias Utilizadas

| Tecnologia      | Finalidade                  |
| --------------- | --------------------------- |
| Java 17         | Linguagem da aplicação      |
| Spring Boot     | Framework da API REST       |
| Spring Web      | Criação dos endpoints       |
| Spring Data JPA | Persistência dos dados      |
| PostgreSQL      | Banco de dados relacional   |
| Docker          | Conteinerização             |
| Docker Compose  | Orquestração dos containers |
| Azure VM        | Ambiente em nuvem           |
| GitHub          | Versionamento e entrega     |

---

## Arquitetura da Solução

A solução foi executada em uma VM Linux na Azure, com dois containers Docker na mesma rede.

### Componentes

| Componente      | Descrição                                                |
| --------------- | -------------------------------------------------------- |
| Usuário         | Acessa a API via navegador ou terminal                   |
| Azure VM        | Máquina virtual Ubuntu onde os containers são executados |
| Container App   | Executa a API Java Spring Boot                           |
| Container Banco | Executa o PostgreSQL                                     |
| Rede Docker     | Permite a comunicação entre App e Banco                  |
| Volume Docker   | Mantém os dados persistidos do PostgreSQL                |

### Fluxo da Arquitetura

```text
Usuário
  |
  | HTTP - Porta 8080
  v
Azure VM - Ubuntu
  |
  v
Container da Aplicação - app-rm556612
  |
  | JDBC
  v
Container do Banco - db-rm556612
  |
  v
Volume Nomeado - spacealert_data
```

---

## Desenho Macro da Arquitetura

A imagem abaixo representa a arquitetura macro da solução executada em nuvem, utilizando uma VM Ubuntu na Azure com dois containers Docker integrados: aplicação Java Spring Boot e banco PostgreSQL.

![Arquitetura Macro - SpaceAlert](https://github.com/user-attachments/assets/3c634db3-448a-4389-962d-930729dd252e)

---

## Modelagem de Dados

A solução possui duas tabelas principais:

| Tabela  | Descrição                                |
| ------- | ---------------------------------------- |
| regioes | Armazena regiões monitoradas             |
| alertas | Armazena alertas relacionados às regiões |

### Relacionamento

```text
regioes 1:N alertas
```

Uma região pode possuir vários alertas, e cada alerta pertence a uma região.

---

## Estrutura do Projeto

```text
spacealert-devops-gs/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/fiap/spacealert/
│       │       ├── controller/
│       │       │   ├── AlertaController.java
│       │       │   └── RegiaoController.java
│       │       ├── model/
│       │       │   ├── Alerta.java
│       │       │   └── Regiao.java
│       │       ├── repository/
│       │       │   ├── AlertaRepository.java
│       │       │   └── RegiaoRepository.java
│       │       └── SpacealertApplication.java
│       └── resources/
│           └── application.properties
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Containers da Solução

| Container    | Função               | Porta |
| ------------ | -------------------- | ----- |
| app-rm556612 | API Java Spring Boot | 8080  |
| db-rm556612  | Banco PostgreSQL     | 5432  |

---

## Variáveis de Ambiente

### Container da Aplicação

| Variável    | Valor                                         |
| ----------- | --------------------------------------------- |
| DB_URL      | jdbc:postgresql://db-rm556612:5432/spacealert |
| DB_USERNAME | postgres                                      |
| DB_PASSWORD | postgres                                      |
| SERVER_PORT | 8080                                          |

### Container do Banco

| Variável          | Valor      |
| ----------------- | ---------- |
| POSTGRES_DB       | spacealert |
| POSTGRES_USER     | postgres   |
| POSTGRES_PASSWORD | postgres   |

---

## Volume Nomeado

O banco de dados utiliza um volume nomeado para persistência dos dados.

```text
spacealert_data:/var/lib/postgresql/data
```

---

## Pré-requisitos

Para executar o projeto em nuvem, é necessário ter:

* VM Linux Ubuntu criada na Azure;
* acesso SSH à VM;
* Docker instalado na VM;
* Docker Compose instalado na VM;
* porta 8080 liberada no grupo de segurança da Azure;
* Git instalado na VM.

---

# How To - Execução em Nuvem

Esta seção apresenta o passo a passo para executar a solução em uma VM Linux na Azure.

---

## 1. Conectar na VM Azure via SSH

No terminal local, execute:

```bash
ssh azureuser@IP_PUBLICO_DA_VM
```

Exemplo:

```bash
ssh azureuser@130.131.47.7
```

---

## 2. Atualizar pacotes da VM

```bash
sudo apt update
sudo apt upgrade -y
```

---

## 3. Instalar Docker e Docker Compose

```bash
sudo apt install -y docker.io docker-compose
```

---

## 4. Iniciar e habilitar o Docker

```bash
sudo systemctl start docker
sudo systemctl enable docker
```

---

## 5. Permitir uso do Docker pelo usuário da VM

```bash
sudo usermod -aG docker azureuser
newgrp docker
```

---

## 6. Validar instalação do Docker

```bash
docker --version
docker-compose --version
docker run hello-world
```

Resultado esperado:

```text
Hello from Docker!
```

---

## 7. Clonar o repositório

```bash
git clone https://github.com/hpecora/spacealert-devops-gs.git
```

---

## 8. Acessar a pasta do projeto

```bash
cd spacealert-devops-gs
```

---

## 9. Conferir arquivos do projeto

```bash
ls -l
```

Arquivos esperados:

```text
Dockerfile
docker-compose.yml
pom.xml
mvnw
mvnw.cmd
src
README.md
```

---

## 10. Subir os containers em background

```bash
docker-compose up -d --build
```

Esse comando realiza:

* build da imagem personalizada da aplicação;
* criação do container da aplicação;
* criação do container do banco;
* criação da rede Docker;
* criação do volume nomeado;
* execução dos containers em segundo plano.

---

## 11. Verificar containers em execução

```bash
docker ps
```

Resultado esperado:

```text
app-rm556612
db-rm556612
```

---

# Testes da API

## 1. Testar endpoint de regiões

```bash
curl http://localhost:8080/regioes
```

---

## 2. Testar endpoint de alertas

```bash
curl http://localhost:8080/alertas
```

---

## 3. Cadastrar uma região

```bash
curl -X POST http://localhost:8080/regioes \
-H "Content-Type: application/json" \
-d '{"nome":"Amazonia Monitorada","estado":"Amazonas","pais":"Brasil","tipoMonitoramento":"Satelite e sensores ambientais"}'
```

---

## 4. Cadastrar um alerta vinculado à região

```bash
curl -X POST http://localhost:8080/alertas/regiao/1 \
-H "Content-Type: application/json" \
-d '{"titulo":"Risco critico de queimadas","descricao":"Temperatura elevada detectada por satelite na regiao","nivel":"CRITICO"}'
```

---

## 5. Listar regiões cadastradas

```bash
curl http://localhost:8080/regioes
```

---

## 6. Listar alertas cadastrados

```bash
curl http://localhost:8080/alertas
```

---

# CRUD da Aplicação

A aplicação possui operações de CRUD para as entidades `Regiao` e `Alerta`.

## Regiões

| Método | Endpoint      | Descrição               |
| ------ | ------------- | ----------------------- |
| GET    | /regioes      | Lista todas as regiões  |
| GET    | /regioes/{id} | Busca uma região por ID |
| POST   | /regioes      | Cria uma nova região    |
| PUT    | /regioes/{id} | Atualiza uma região     |
| DELETE | /regioes/{id} | Remove uma região       |

## Alertas

| Método | Endpoint                        | Descrição                             |
| ------ | ------------------------------- | ------------------------------------- |
| GET    | /alertas                        | Lista todos os alertas                |
| GET    | /alertas/{id}                   | Busca um alerta por ID                |
| POST   | /alertas/regiao/{regiaoId}      | Cria um alerta vinculado a uma região |
| PUT    | /alertas/{id}/regiao/{regiaoId} | Atualiza um alerta                    |
| DELETE | /alertas/{id}                   | Remove um alerta                      |

---

# Teste de Update

## Atualizar região

```bash
curl -X PUT http://localhost:8080/regioes/1 \
-H "Content-Type: application/json" \
-d '{"nome":"Amazonia Monitorada","estado":"Amazonas","pais":"Brasil","tipoMonitoramento":"Satelite e sensores ambientais"}'
```

---

## Atualizar alerta

```bash
curl -X PUT http://localhost:8080/alertas/1/regiao/1 \
-H "Content-Type: application/json" \
-d '{"titulo":"Risco critico de queimadas","descricao":"Temperatura elevada detectada por satelite na regiao","nivel":"CRITICO"}'
```

---

# Teste de Delete

## Criar alerta para teste de exclusão

```bash
curl -X POST http://localhost:8080/alertas/regiao/1 \
-H "Content-Type: application/json" \
-d '{"titulo":"Teste de exclusao","descricao":"Alerta criado apenas para testar delete","nivel":"BAIXO"}'
```

---

## Deletar alerta de teste

```bash
curl -X DELETE http://localhost:8080/alertas/2
```

---

## Confirmar exclusão

```bash
curl http://localhost:8080/alertas
```

---

# Logs dos Containers

## Logs da aplicação

```bash
docker logs app-rm556612
```

Nesse log é possível verificar:

* inicialização da aplicação Spring Boot;
* execução na porta 8080;
* conexão com o banco PostgreSQL;
* criação das tabelas;
* execução dos comandos SQL pelo Hibernate.

---

## Logs do banco

```bash
docker logs db-rm556612
```

Nesse log é possível verificar:

* inicialização do PostgreSQL;
* criação do banco;
* banco pronto para aceitar conexões.

---

# Acesso aos Containers com docker container exec

## Container da Aplicação

```bash
docker container exec -it app-rm556612 sh
```

Dentro do container:

```bash
pwd
ls -l
whoami
exit
```

Resultado esperado:

```text
/app
app.jar
spaceuser
```

O usuário `spaceuser` demonstra que a aplicação não está sendo executada como root.

---

## Container do Banco de Dados

```bash
docker container exec -it -u postgres db-rm556612 sh
```

Dentro do container:

```bash
pwd
ls -l
whoami
exit
```

Resultado esperado:

```text
postgres
```

---

# Evidência de Persistência no Banco

Para acessar diretamente o PostgreSQL dentro do container do banco:

```bash
docker container exec -it db-rm556612 psql -U postgres -d spacealert
```

---

## Listar tabelas

```sql
\dt
```

Resultado esperado:

```text
alertas
regioes
```

---

## Consultar regiões

```sql
SELECT * FROM regioes;
```

Resultado esperado:

```text
id | estado   | nome                | pais   | tipo_monitoramento
1  | Amazonas | Amazonia Monitorada | Brasil | Satelite e sensores ambientais
```

---

## Consultar alertas

```sql
SELECT * FROM alertas;
```

Resultado esperado:

```text
id | descricao                                           | nivel   | titulo                     | regiao_id
1  | Temperatura elevada detectada por satelite na regiao | CRITICO | Risco critico de queimadas | 1
```

---

## Sair do PostgreSQL

```sql
\q
```

---

# Acesso Público da Aplicação

Com a porta 8080 liberada no grupo de segurança da Azure, a API pode ser acessada pelo IP público da VM.

## Endpoints públicos

```text
http://IP_PUBLICO_DA_VM:8080/regioes
http://IP_PUBLICO_DA_VM:8080/alertas
```

Exemplo:

```text
http://130.131.47.7:8080/regioes
http://130.131.47.7:8080/alertas
```

---

# Comandos Úteis

## Parar os containers

```bash
docker-compose down
```

---

## Subir novamente

```bash
docker-compose up -d --build
```

---

## Ver containers

```bash
docker ps
```

---

## Ver imagens

```bash
docker images
```

---

## Ver volumes

```bash
docker volume ls
```

---

## Ver redes

```bash
docker network ls
```

---

# Checklist de Requisitos Atendidos

| Requisito                                 | Status   |
| ----------------------------------------- | -------- |
| Aplicação conteinerizada                  | Atendido |
| Imagem personalizada via Dockerfile       | Atendido |
| Docker Compose                            | Atendido |
| Dois containers integrados                | Atendido |
| Container da aplicação                    | Atendido |
| Container do banco de dados               | Atendido |
| Banco PostgreSQL                          | Atendido |
| Volume nomeado para persistência          | Atendido |
| Variável de ambiente no App               | Atendido |
| Variável de ambiente no Banco             | Atendido |
| Porta exposta no App                      | Atendido |
| Porta exposta no Banco                    | Atendido |
| Containers com RM no nome                 | Atendido |
| App com usuário não-root                  | Atendido |
| Diretório de trabalho no Dockerfile       | Atendido |
| CRUD completo                             | Atendido |
| Duas tabelas relacionadas                 | Atendido |
| Execução em nuvem                         | Atendido |
| Containers em background                  | Atendido |
| Logs dos dois containers                  | Atendido |
| docker container exec nos dois containers | Atendido |
| pwd, ls -l e whoami nos dois containers   | Atendido |
| SELECT direto no banco                    | Atendido |
| How To desde o clone do repositório       | Atendido |

---

# Link do Repositório

```text
https://github.com/hpecora/spacealert-devops-gs
```

---

# Link do Vídeo

```text
Adicionar aqui o link do vídeo publicado no YouTube
```
