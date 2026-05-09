# Relatório de Entrega: Sistema de Gestão de Estoque de Veículos

## 1. Descrição do Projeto
O sistema é uma aplicação completa (Full Stack) para gerenciamento de estoque de uma concessionária. 
- **Backend:** Desenvolvido com Spring Boot, utilizando arquitetura em camadas (Controller, Service, DTO, Repository) e banco de dados H2/MySQL.
- **Frontend:** Desenvolvido em JavaFX, com integração via API REST utilizando o HttpClient do Java 21, apresentando formatação de moeda brasileira (R$) e fluxos de cadastro otimizados.

---

## 2. Códigos Principais (Backend)

### VeiculoService.java (Regras de Negócio e Integridade)
O serviço garante que modelos e marcas existam antes de salvar um veículo e gerencia a conversão de dados.

```java
@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository repository;
    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional
    public VeiculoDTO salvar(VeiculoDTO dto) {
        Modelo modelo = modeloRepository.findById(dto.modelo().id())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado"));

        Veiculo veiculo = new Veiculo();
        mapearDtoParaEntidade(dto, veiculo);
        veiculo.setModelo(modelo);

        return VeiculoDTO.fromEntity(repository.save(veiculo));
    }
}
```

---

## 3. Códigos Principais (Frontend)

### ApiService.java (Integração REST)
Utiliza o HttpClient moderno do Java para comunicação assíncrona e segura com o servidor.

```java
public class ApiService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public Veiculo salvarVeiculo(Veiculo veiculo) throws Exception {
        String json = mapper.writeValueAsString(veiculo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/veiculos" + (veiculo.getId() != null ? "/" + veiculo.getId() : "")))
                .header("Content-Type", "application/json")
                .method(veiculo.getId() != null ? "PUT" : "POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
        // ... execução e tratamento de resposta
    }
}
```

### MainController.java (Formatação de Moeda BRL)
Implementação da lógica para exibir e converter preços no padrão `25.000,00`.

```java
private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

private void configurarTabela() {
    colPreco.setCellValueFactory(cellData -> {
        BigDecimal preco = cellData.getValue().getPreco();
        return new SimpleStringProperty(preco != null ? nf.format(preco) : "R$ 0,00");
    });
}

private BigDecimal converterParaBigDecimal(String valorStr) {
    String limpo = valorStr.replaceAll("[R$\\s.]", "").replace(",", ".");
    return new BigDecimal(limpo);
}
```

---

## 4. Prints do Sistema (Inserir Aqui)
*(Nesta seção, o desenvolvedor deve inserir os screenshots das telas funcionando)*
