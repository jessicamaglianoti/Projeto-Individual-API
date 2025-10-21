# Projeto Individual - Serratec Music API

## 💻 Desenvolvedor:
**Nome:** Jéssica Magliano
Trabalho Prático - Construção de API RESTful, Serratec.

---

## 🎯 Sobre o Projeto:
Construção do *back-end* da plataforma "Serratec Music", uma API RESTful desenvolvida em **Java** com o framework **Spring Boot**.

A API é responsável por gerenciar dados de Usuários, Perfis, Artistas, Músicas e Playlists. Todos os dados são gravados em um banco de dados **PostgreSQL**.

### 🛠️ Requisitos Técnicos Principais
**Arquitetura:** O projeto deve ser organizado em pacotes (`controller`, `repository`, `domain/entity`, `exception`).
**Persistência:** Uso de JPA e Hibernate, com conexão ao PostgreSQL.
**Relacionamentos:** Implementação correta dos relacionamentos One-to-One, One-to-Many e Many-to-Many.
**Validação:** Uso de Bean Validation (`@Valid`, `@NotBlank`, etc.) nos dados de entrada.
**Tratamento de Erros:** Utilização de `@ControllerAdvice` para tratamento centralizado de exceções.
**Documentação:** Documentação automática com Swagger/Springdoc OpenAPI, incluindo descrições customizadas.

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
Para executar este projeto, você precisará ter instalado:
1.  **Java 17** ou superior (configurado com o JDK).
2.  **Maven** (para gerenciar dependências).
3.  **PostgreSQL** (Rodando localmente, geralmente na porta `5432`).

### 1. Configuração do Banco de Dados:

Crie um banco de dados PostgreSQL com o seguinte nome:
* **Nome do Banco:** `serratec_music_db`

### 2. Configuração do `application.properties`

Abra o arquivo `src/main/resources/application.properties` no projeto e configure as credenciais do seu PostgreSQL.

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/serratec_music_db
spring.datasource.username=postgres
spring.datasource.password=123456

# Configurações do JPA/Hibernate
# O 'update' é usado para que o Hibernate crie e atualize as tabelas automaticamente.
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Caso precise, mude a porta se a 8080 estiver ocupada
# server.port=8081
