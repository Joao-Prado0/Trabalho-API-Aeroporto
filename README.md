# API Rest: Gerenciamento de Aeroportos ✈️

Este projeto consiste no desenvolvimento de uma API REST simples para o gerenciamento de dados de aeroportos globais. O sistema utiliza dados reais fornecidos pelo projeto *OpenFlights*,
realiza a sanitização dessas informações e as disponibiliza através de endpoints para operações de CRUD (Create, Read, Update, Delete). O projeto consiste em uma aplicação backend, utilizando Spring Boot e MySQL,
contendo testes unitários e de integração.

## 🎯 Objetivo

O objetivo principal da aplicação é reforçar os conhecimentos estudados na disciplina de Laboratório de Programação modular. Onde além de um implementarmos um trabalho prático completo com back e front end, também
aprendemos as bases do funcionamento de uma aplicação backend em Spring Boot. Dessa forma, esse trabalhp final, serve para reforçar esses conhecimentos e garantir a aplicação de boas práticas da POO, já que em trabalhos 
de maior porte essas abordagens podem se perder um pouco.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 4.0.0
* **Gerenciador de Dependências:** Maven
* **Banco de Dados (Produção):** MySQL
* **Banco de Dados (Testes):** H2 Database (Em memória)
* **Testes:** JUnit 5, Mockito, Spring Boot Test
* **Outras Bibliotecas:** Lombok, OpenCSV (lógica nativa implementada).

## ⚙️ Configuração do Ambiente

### Pré-requisitos
* Java JDK 21 instalado.
* Maven instalado.
* MySQL Server rodando na porta 3306.

### 1. Clonar o Repositório
```bash
git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
cd seu-repositorio
````

### 2\. Configuração do Banco de Dados

A aplicação está configurada para criar o banco de dados automaticamente se ele não existir (`createDatabaseIfNotExist=true`).

No entanto, verifique o arquivo `src/main/resources/application.properties` e ajuste as credenciais do seu MySQL local, se necessário:

```properties
spring.application.name=app

# --- JPA / Hibernate ---
spring.jpa.hibernate.ddl-auto=update

# --- Mostrar SQL no Console ---
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.highlight_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=Trace

# --- DataSource ---
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/aeroporto_api?createDatabaseIfNotExist=true
spring.datasource.username=SEU_USUARIO_AQUI
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# --- Scripts SQL (schema.sql / data.sql)
spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true
```

### 3\. Carga Inicial de Dados

O projeto possui um **Data Loader** inteligente. Ao executar a aplicação pela primeira vez (perfil padrão), o sistema irá:

1.  Ler o arquivo `airports.csv` (OpenFlights).
2.  Filtrar aeroportos sem código IATA.
3.  Converter altitudes de Pés para Metros.
4.  Sanitizar dados de texto e popular o banco MySQL automaticamente.

> **Nota:** Esta carga não é executada durante os testes automatizados para garantir performance.

### 4\.Dependências
As dependências do projeto (Spring Web, JPA, Security, H2, Lombok, etc.) são gerenciadas automaticamente pelo Maven. Não é necessário configuração manual, basta aguardar o download ao executar o projeto pela primeira vez.

## 🚀 Como Executar a Aplicação

PPara baixar as dependências, compilar e iniciar o servidor, execute o comando abaixo na raiz do projeto:

```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Endpoints Disponíveis

| Método | URL | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/v1/aeroportos/all` | Retorna todos os aeroportos cadastrados. |
| `GET` | `/api/v1/aeroportos/{iata}` | Busca um aeroporto específico pelo código IATA. |
| `POST` | `/api/v1/aeroportos` | Cadastra um novo aeroporto. |
| `PUT` | `/api/v1/aeroportos/{iata}` | Atualiza os dados de um aeroporto existente. |
| `DELETE` | `/api/v1/aeroportos/{iata}` | Remove um aeroporto do banco de dados. |

## 🧪 Executando os Testes

O projeto segue a estratégia de testes definida nos requisitos, separando testes de unidade e integração.

### Testes de Unidade (Unit Tests)

Focam na lógica de negócio isolada (Services e Utils), utilizando Mockito para simular o banco de dados. Configurado via **Maven Surefire Plugin**.

Para rodar apenas os testes de unidade:

```bash
mvn test
```

### Testes de Integração (Integration Tests)

Testam os endpoints de ponta a ponta, subindo o contexto do Spring e utilizando um banco de dados H2 em memória (isolado do MySQL de produção). Configurado via **Maven Failsafe Plugin**.

Para rodar os testes de integração (e os de unidade juntos):

```bash
mvn verify
```

-----

**Desenvolvido para a disciplina de Programação Modular.**
