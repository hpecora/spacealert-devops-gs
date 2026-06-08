# SpaceAlert API - Global Solution 2026/1

## Integrantes

| Nome               | RM       |
| ------------------ | -------- |
| Henrique Pecora    | RM556612 |
| Santhiago de Gobbi | RM98420  |

---

## Descrição da Solução

O **SpaceAlert API** é uma solução desenvolvida para a Global Solution 2026/1 com o tema **economia espacial**.

A proposta do projeto é utilizar o conceito de monitoramento por satélite para registrar regiões monitoradas e alertas ambientais/climáticos. A aplicação permite cadastrar regiões, registrar alertas associados a essas regiões e consultar as informações persistidas em banco de dados.

A solução foi pensada para apoiar cenários como:

* monitoramento de queimadas;
* acompanhamento de áreas de risco;
* alertas climáticos;
* análise de regiões monitoradas por satélite;
* apoio à tomada de decisão em situações ambientais críticas.

---

## Tecnologias Utilizadas

| Tecnologia      | Finalidade                            |
| --------------- | ------------------------------------- |
| Java 17         | Linguagem da aplicação                |
| Spring Boot     | Framework para construção da API REST |
| Spring Web      | Criação dos endpoints REST            |
| Spring Data JPA | Persistência dos dados                |
| PostgreSQL      | Banco de dados relacional             |
| Docker          | Conteinerização da aplicação          |
| Docker Compose  | Orquestração dos containers           |
| Azure VM        | Ambiente em nuvem                     |
| GitHub          | Versionamento e entrega do código     |

---

## Arquitetura da Solução

A aplicação foi executada em uma máquina virtual Linux na Azure, utilizando dois containers Docker integrados na mesma rede.

### Componentes

| Componente      | Descrição                                                |
| --------------- | -------------------------------------------------------- |
| Usuário         | Acessa a API pela internet                               |
| Azure VM        | Máquina virtual Ubuntu onde os containers são executados |
| Container App   | Executa a API Java Spring Boot                           |
| Container Banco | Executa o PostgreSQL                                     |
| Volume Docker   | Mantém os dados persistidos do banco                     |
| Rede Docker     | Permite a comunicação entre App e Banco                  |

### Fluxo da Arquitetura

```text
Usuário
  |
  | HTTP - Porta 8080
  v
Azure VM
  |
  v
Container App - app-rm571402
  |
  | JDBC
  v
Container Banco - db-rm571402
  |
  v
Volume Nomeado - spacealert_data
```

---

## Modelagem de Dados

A solução possui duas tabelas principais:

| Tabela  | Descrição                                   |
| ------- | ------------------------------------------- |
| regioes | Armazena as regiões monitoradas             |
| alertas | Armazena os alertas relacionados às regiões |

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
│       │       ├── model/
│       │       ├── repository/
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
| app-rm571402 | API Java Spring Boot | 8080  |
| db-rm571402  | Banco PostgreSQL     | 5432  |

---

## Variáveis de Ambiente

### Container da Aplicação

| Variável    | Valor                                         |
| ----------- | --------------------------------------------- |
| DB_URL      | jdbc:postgresql://db-rm571402:5432/spacealert |
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

O banco de dados utiliza um volume nomeado para garantir a persistência dos dados.

```text
spacealert_data:/var/lib/postgresql/data
```

---

## Pré-requisitos

Para executar o projeto em nuvem, é necessário ter:

* uma VM Linux Ubuntu criada na Azure;
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
```

---

## 10. Subir os containers

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
app-rm571402
db-rm571402
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
docker logs app-rm571402
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
docker logs db-rm571402
```

Nesse log é possível verificar:

* inicialização do PostgreSQL;
* criação do banco;
* banco pronto para aceitar conexões.

---

# Acesso aos Containers com docker container exec

## Container da Aplicação

```bash
docker container exec -it app-rm571402 sh
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
docker container exec -it -u postgres db-rm571402 sh
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
docker container exec -it db-rm571402 psql -U postgres -d spacealert
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
id | descricao                                             | nivel   | titulo                       | regiao_id
1  | Temperatura elevada detectada por satelite na regiao   | CRITICO | Risco critico de queimadas   | 1
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

# Observações sobre a Entrega

A entrega atende aos principais requisitos técnicos da disciplina de DevOps Tools & Cloud Computing:

* aplicação conteinerizada com imagem personalizada;
* uso de Dockerfile;
* uso de Docker Compose;
* dois containers integrados;
* container da aplicação;
* container do banco de dados;
* banco PostgreSQL;
* volume nomeado para persistência;
* variáveis de ambiente nos containers;
* portas expostas;
* containers executando em segundo plano;
* container da aplicação com usuário não-root;
* diretório de trabalho definido no Dockerfile;
* CRUD completo;
* duas tabelas com relacionamento;
* execução em ambiente de nuvem;
* logs dos containers;
* acesso aos containers com `docker container exec`;
* demonstração de `pwd`, `ls -l` e `whoami`;
* evidência de persistência com `SELECT` direto no banco.

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
