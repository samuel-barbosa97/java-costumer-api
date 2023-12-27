# Costumer Management Application

Este é um projeto de aplicativo de gerenciamento de clientes (Costumers) desenvolvido em Java usando o framework Spring Boot e MongoDB para armazenamento de dados.

## Funcionalidades

1. **Criação de Costumer:**
   - Endpoint: `POST /costumers`
   - Cria um novo Costumer com base nos dados fornecidos.

2. **Listagem de Costumers:**
   - Endpoint: `GET /costumers`
   - Retorna uma lista de todos os Costumers cadastrados.

3. **Consulta de Costumer por ID:**
   - Endpoint: `GET /costumers/{id}`
   - Retorna os detalhes de um Costumer específico com base no ID.

4. **Atualização de Costumer:**
   - Endpoint: `PUT /costumers/{id}`
   - Atualiza os dados de um Costumer existente com base no ID.

5. **Exclusão de Costumer:**
   - Endpoint: `DELETE /costumers/{id}`
   - Exclui um Costumer com base no ID.

## Configuração do Banco de Dados

Este projeto utiliza MongoDB como banco de dados. Certifique-se de configurar o MongoDB antes de executar a aplicação.

### Configuração do Docker Compose

```yaml
version: '3'

services:
  mongodb:
    image: 'mongo:latest'
    environment:
      - 'MONGO_INITDB_DATABASE=mydatabase'
      - 'MONGO_INITDB_ROOT_PASSWORD=secret'
      - 'MONGO_INITDB_ROOT_USERNAME=root'
    ports:
      - '27017'
```

Execute `docker-compose up` para iniciar o MongoDB.

## Como Executar o Projeto

1. Clone este repositório.
2. Configure o MongoDB usando o Docker Compose conforme descrito acima.
3. Execute a aplicação Spring Boot usando o Gradle.

```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8080`.

## Exemplos de Requisições

### Criação de Costumer

```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "nome": "Nome do Cliente",
  "endereco": "Endereço do Cliente",
  "email": "cliente@example.com",
  "telefone": "123-456-7890"
}' http://localhost:8080/costumers
```

### Listagem de Costumers

```bash
curl http://localhost:8080/costumers
```

### Consulta de Costumer por ID

```bash
curl http://localhost:8080/costumers/1
```

### Atualização de Costumer por ID

```bash
curl -X PUT -H "Content-Type: application/json" -d '{
  "nome": "Novo Nome do Cliente",
  "endereco": "Novo Endereço do Cliente",
  "email": "novo.cliente@example.com",
  "telefone": "987-654-3210"
}' http://localhost:8080/costumers/1
```

### Exclusão de Costumer por ID

```bash
curl -X DELETE http://localhost:8080/costumers/1
```
