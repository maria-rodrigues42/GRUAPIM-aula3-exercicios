# Contacts API - Aula 3 (APIs e Microsserviços em Java)

Este repositório contém uma API RESTful desenvolvida em Spring Boot para o gerenciamento de contatos e seus respectivos endereços. O projeto faz parte de uma atividade ou laboratório de estudo sobre APIs e Microsserviços em Java. 

Além do código-fonte da API, o repositório inclui respostas para questões teóricas sobre arquitetura de APIs (REST vs SOAP) e uma coleção do Postman para facilitar os testes dos endpoints.

## Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e bibliotecas:

* **Java 25**
* **Spring Boot** (Web, Data JPA, Validation)
* **Banco de Dados H2** (In-memory para desenvolvimento e testes)
* **Maven** (Gerenciamento de dependências e build via Maven Wrapper)

## Funcionalidades

A aplicação possui duas entidades principais: **Contact** e **Address**. As funcionalidades incluem:

* **Contatos:**
  * Criar um novo contato (Nome, Telefone e E-mail).
  * Listar todos os contatos.
  * Buscar um contato específico pelo ID.
  * Buscar contatos por nome (`/search?name=...`).
  * Atualizar um contato inteiro (PUT) ou parcialmente (PATCH).
  * Excluir um contato.
* **Endereços:**
  * Adicionar um endereço (Rua, Cidade, Estado, CEP) e vinculá-lo a um contato existente.
  * Listar todos os endereços.
  * Buscar todos os endereços pertencentes a um contato específico.

## Como Executar o Projeto

1. Clone este repositório em sua máquina local.
2. Navegue até o diretório do projeto (`contacts-api`).
3. Execute a aplicação utilizando o Maven Wrapper:
   * **No Linux/macOS:** `./mvnw spring-boot:run`
   * **No Windows:** `mvnw.cmd spring-boot:run`
4. A API estará disponível no endereço: `http://localhost:8080/api`

## 🗄️ Acesso ao Banco de Dados (H2 Console)

Como o projeto utiliza o H2 em memória, você pode visualizar e interagir com as tabelas geradas automaticamente pelo Hibernate acessando o H2 Console no navegador:

* **URL de acesso:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:contactsdb`
* **Username:** `sa`
* **Password:** *(deixe em branco)*

## 📡 Endpoints Principais

Abaixo está um resumo das rotas disponíveis na API:

**Contatos**
* `GET /api/contacts` - Retorna a lista de contatos.
* `POST /api/contacts` - Cria um novo contato.
* `GET /api/contacts/{id}` - Retorna detalhes do contato pelo ID.
* `PUT /api/contacts/{id}` - Atualiza um contato existente.
* `PATCH /api/contacts/{id}` - Atualiza parcialmente um contato.
* `DELETE /api/contacts/{id}` - Remove um contato.
* `GET /api/contacts/search?name={nome}` - Pesquisa contatos que contenham o nome informado.
* `GET /api/contacts/{id}/addresses` - Lista os endereços de um contato específico.

**Endereços**
* `GET /api/addresses` - Retorna todos os endereços.
* `GET /api/addresses/{id}` - Retorna um endereço pelo ID.
* `POST /api/addresses` - Cria um endereço vinculado a um contato.

## Testando com o Postman

Na raiz do projeto existe um arquivo chamado `aula3-api-microservicos.postman_collection.json`. Você pode importá-lo diretamente no Postman para ter acesso rápido e prático a todos os endpoints já configurados com exemplos de requisição (body).

## Atividade Teórica (Exercício 3)

O repositório também inclui as respostas para o exercício teórico da Aula 3, abordando os seguintes temas:
1. Principais diferenças entre REST e SOAP.
2. Cenários atuais de uso do SOAP.
3. Vantagens e desvantagens de REST vs SOAP.
4. Comparação de segurança (WS-Security vs métodos modernos do REST).
5. Modelo de Maturidade de Richardson e como ele se aplica.

As respostas podem ser consultadas no arquivo `contacts-api/respostas-exercicio-3.txt`.
