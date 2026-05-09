package com.exemplo.frontend.service;

import com.exemplo.frontend.model.Veiculo;
import com.exemplo.frontend.model.Marca;
import com.exemplo.frontend.model.Modelo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Serviço para integração com a API REST do Backend.
 */
public class ApiService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Veiculo> listarVeiculos(String marca, String modelo, String status, Integer ano, BigDecimal precoMax) throws Exception {
        StringBuilder url = new StringBuilder(BASE_URL + "/veiculos?");
        if (marca != null && !marca.isEmpty()) url.append("marca=").append(URLEncoder.encode(marca, StandardCharsets.UTF_8)).append("&");
        if (modelo != null && !modelo.isEmpty()) url.append("modelo=").append(URLEncoder.encode(modelo, StandardCharsets.UTF_8)).append("&");
        if (status != null && !status.isEmpty()) url.append("status=").append(URLEncoder.encode(status, StandardCharsets.UTF_8)).append("&");
        if (ano != null) url.append("ano=").append(ano).append("&");
        if (precoMax != null) url.append("precoMax=").append(precoMax);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url.toString())).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao buscar veículos: " + response.statusCode());
        }
        
        return mapper.readValue(response.body(), new TypeReference<List<Veiculo>>() {});
    }

    public Veiculo salvarVeiculo(Veiculo veiculo) throws Exception {
        String json = mapper.writeValueAsString(veiculo);
        String url = BASE_URL + "/veiculos" + (veiculo.getId() != null ? "/" + veiculo.getId() : "");
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method(veiculo.getId() != null ? "PUT" : "POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao salvar veículo: " + response.body());
        }
        return mapper.readValue(response.body(), Veiculo.class);
    }

    public Marca salvarMarca(Marca marca) throws Exception {
        String json = mapper.writeValueAsString(marca);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/marcas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao salvar marca: " + response.body());
        }
        return mapper.readValue(response.body(), Marca.class);
    }

    public Modelo salvarModelo(Modelo modelo) throws Exception {
        String json = mapper.writeValueAsString(modelo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/modelos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao salvar modelo: " + response.body());
        }
        return mapper.readValue(response.body(), Modelo.class);
    }

    public List<Marca> listarMarcas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/marcas")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<List<Marca>>() {});
    }

    public List<Modelo> listarModelos(Long marcaId) throws Exception {
        String url = BASE_URL + "/modelos" + (marcaId != null ? "?marcaId=" + marcaId : "");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<List<Modelo>>() {});
    }

    public void deletarVeiculo(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/veiculos/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao deletar veículo: " + response.body());
        }
    }
}
