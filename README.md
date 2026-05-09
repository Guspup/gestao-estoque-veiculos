# Sistema de Gestão de Estoque de Veículos

Este projeto foi desenvolvido como um trabalho acadêmico para resolver a desorganização no controle de estoque de agências de veículos. O sistema utiliza uma arquitetura Client-Server com Spring Boot no Backend e JavaFX no Frontend.

## 🚀 Tecnologias Utilizadas
- **Backend:** Spring Boot 3, Spring Data JPA, H2 Database (Banco em memória).
- **Frontend:** JavaFX 21, FXML, HttpClient (JSON).
- **Linguagem:** Java 17.

## 📦 Estrutura do Projeto
- `/backend`: API REST que gerencia o banco de dados e as regras de negócio.
- `/frontend`: Aplicação Desktop para interação com o usuário.

## 🛠️ Como Executar

### 1. Rodando o Backend
1. Navegue até a pasta `backend`.
2. Execute o comando: `mvn spring-boot:run`.
3. O servidor estará rodando em `http://localhost:8080`.
4. Você pode acessar o console do banco de dados em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:estoque_db`).

### 2. Rodando o Frontend
1. Navegue até a pasta `frontend`.
2. Execute o comando: `mvn javafx:run`.
3. A interface gráfica será aberta.

## 📋 Funcionalidades
- Cadastro de Veículos, Marcas e Modelos.
- Listagem com filtros por Marca, Modelo e Status.
- Remoção de veículos do estoque.
- Persistência de dados em memória (H2).

## 🎓 Conceitos de POO Aplicados
- **Classes e Objetos:** Representação de Veículos, Marcas e Modelos.
- **Encapsulamento:** Uso de atributos privados e métodos getters/setters.
- **Associação:** Relacionamento entre Veiculo -> Modelo -> Marca.
