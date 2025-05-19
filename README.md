# Solari - Order Receiver Service

Este microsserviço é responsável por receber e processar pedidos no sistema **Solari**, incluindo a validação dos dados e o envio de mensagens para o Kafka. Ele faz parte do sistema de gerenciamento de pedidos do projeto **Solari**, desenvolvido no **Tech Challenge - Fase 4** da pós-graduação em Arquitetura e Desenvolvimento Java - FIAP.

---

## 🧩 Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot**: Framework para criação de aplicações Java.
- **Maven**: Gerenciador de dependências e build.
- **Spring Kafka**: Integração com o Apache Kafka.
- **JaCoCo**: Ferramenta para análise de cobertura de testes.
- **JUnit 5 + Mockito**: Frameworks para testes unitários e mocks.
- **Docker**: Containerização da aplicação.

---

## 🧱 Estrutura do Projeto

O projeto segue a Clean Architecture, dividindo responsabilidades em camadas bem definidas:

- **application**: Contém os casos de uso e regras de negócio.
- **domain**: Representa as entidades e objetos de domínio.
- **infrastructure**: Implementações de gateways, repositórios, controladores e configurações.
- **tests**: Testes unitários e de integração.

---

## 🚀 Como executar localmente

### Pré-requisitos
- Java 21+
- Maven
- Docker

### Passos
1. Clonar o repositório:
   git clone https://github.com/BrunaCasagrande/solari-order-receiver-service.git
   cd solari-order-receiver-service

2. Executar o projeto com Maven:
   mvn spring-boot:run

---

## 📌 Endpoints Principais

### Pedidos

- **POST** /solari/v1/order  
  Recebe e processa um novo pedido.

---

## ✅ Testes

Para executar os testes e gerar o relatório de cobertura com JaCoCo:

1. Executar os testes:
   mvn test

2. Gerar o relatório de cobertura:
   mvn jacoco:report

3. Acessar o relatório em:  
   file:///C:/solari/solari-order-receiver-service/target/site/jacoco/index.html

---

## 🐳 Executando com Docker

### Build da imagem Docker:
docker build -t solari-order-receiver-service .

### Executar o container:
docker run -p 8081:8081 solari-order-receiver-service

### Acessar a aplicação:
http://localhost:8081/solari/v1/order

---

## 👩‍💻 Autor

Desenvolvido por **Bruna Casagrande RM: 359536** como parte do projeto **Solari**.
