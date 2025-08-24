# AuthAdmin

Sistema de autenticação e gerenciamento de usuários para um sistema qualquer. Esta API fornece funcionalidades essenciais como login e registro de usuários, utilizando autenticação baseada em JWT e seguindo boas práticas de organização com Spring Boot.

### 🧰 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **MapStruct**
- **Flyway**
- **PostgreSQL**
- **Maven**

### 📦 Funcionalidades

- Registro de novos usuários
- Autenticação de usuários com geração de token JWT
- Validação e tratamento global de erros
- Scripts de migração com Flyway

### ▶️ Como Executar o Projeto

#### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

#### Configuração

1. Configure seu banco de dados PostgreSQL e crie um banco chamado `auth_admin` (ou outro nome desejado).

2. Ajuste o arquivo `src/main/resources/application.yml` com suas credenciais de acesso ao banco:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auth_admin
    username: seu_usuario
    password: sua_senha
```

3. Execute as migrações automaticamente com o Flyway (configuração padrão já habilitada).

### 🔐 Autenticação

O login é feito via JWT. Após autenticar com o endpoint `/auth/login`, você receberá um token no corpo da resposta. Use esse token para autenticar chamadas subsequentes passando-o no header:

```
Authorization: Bearer <token>
```

### 📂 Estrutura do Projeto

```
src/main/java/com/leumas/finance/admin/
├── config          # Configurações de segurança, JWT e CORS
├── controller      # Endpoints de autenticação e usuários
├── entity          # Entidades JPA
├── exception       # Exceções personalizadas
├── mapper          # MapStruct para conversão DTO ↔ entidade
├── repository      # Interfaces JPA para acesso a dados
└── service         # Regras de negócio
```

### 🗃️ Scripts de Banco de Dados

Localizados em `src/main/resources/db/migration/` e gerenciados via Flyway. O projeto já inclui um script para criação da tabela de usuários:

- `V0__create_users_table.sql`

### 📌 Melhorias Futuras(features)

- Documentação da API com Swagger
- Criação de perfis de usuário (admin, comum)
- Rate limiting ou controle de tentativas de login
- Reset de senha

---

by Samuel NS.