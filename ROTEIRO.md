# Roteiro de Vídeo: Apresentação do Projeto de Gestão de Estoque

Este roteiro foi criado para guiar sua gravação. Siga os tempos sugeridos para um vídeo de aproximadamente 3 a 5 minutos.

---

## 🕒 Parte 1: Introdução e Problema (0:00 - 1:00)
- **O que falar:** "Olá, professor! Este é o meu projeto de Gestão de Estoque de Veículos. O problema que identifiquei foi a desorganização em agências que ainda usam papel ou planilhas fragmentadas, o que dificulta o acesso rápido aos dados e gera erros de cadastro."
- **O que mostrar:** Mostre a tela inicial do programa ou o slide com o título do trabalho.

## 🕒 Parte 2: Arquitetura e Tecnologias (1:00 - 2:00)
- **O que falar:** "Para resolver isso, desenvolvi uma solução Client-Server. No Backend, utilizei **Spring Boot** com **Spring Data JPA** para gerenciar o banco de dados **H2**. No Frontend, utilizei **JavaFX** para criar uma interface intuitiva. A comunicação entre os dois é feita via JSON, seguindo o padrão REST API."
- **O que mostrar:** Mostre rapidamente a estrutura de pastas no VS Code ou IntelliJ (pastas `backend` e `frontend`).

## 🕒 Parte 3: Demonstração das Funcionalidades (2:00 - 4:00)
- **Ação 1 (Listagem):** "Aqui podemos ver a listagem de veículos que já vem pré-carregada do banco." (Mostre a tabela).
- **Ação 2 (Filtro):** "Posso filtrar rapidamente por marca ou status, o que resolve o problema da dificuldade de busca." (Digite 'Toyota' no filtro e clique em filtrar).
- **Ação 3 (Cadastro):** "Agora vou cadastrar um novo veículo. Seleciono a Marca, o Modelo, digito o ano, preço e salvo. O sistema valida os dados e já atualiza a lista automaticamente." (Realize um cadastro ao vivo).
- **Ação 4 (Remoção):** "Também podemos remover um veículo quando ele é vendido." (Selecione um carro e clique em remover).

## 🕒 Parte 4: Conceitos de POO e Conclusão (4:00 - 5:00)
- **O que falar:** "Durante o desenvolvimento, apliquei conceitos fundamentais de POO, como **Encapsulamento** nos modelos, e **Associação** entre as classes Marca, Modelo e Veículo. Isso garante que o código seja organizado e fácil de manter. Obrigado pela atenção!"
- **O que mostrar:** Mostre o código de uma das classes (ex: `Veiculo.java`) destacando os atributos privados e getters/setters.

---
**Dica:** Tente manter um tom calmo e mostre o programa funcionando de verdade! Boa sorte na gravação!
