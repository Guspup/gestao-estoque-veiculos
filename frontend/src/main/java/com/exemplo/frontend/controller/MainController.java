package com.exemplo.frontend.controller;

import com.exemplo.frontend.model.Marca;
import com.exemplo.frontend.model.Modelo;
import com.exemplo.frontend.model.Veiculo;
import com.exemplo.frontend.service.ApiService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Controller principal da interface JavaFX com formatação de moeda brasileira.
 */
public class MainController {

    @FXML private TableView<Veiculo> tableVeiculos;
    @FXML private TableColumn<Veiculo, Long> colId;
    @FXML private TableColumn<Veiculo, String> colMarca, colModelo, colStatus, colCor;
    @FXML private TableColumn<Veiculo, Integer> colAno;
    @FXML private TableColumn<Veiculo, String> colPreco; // Alterado para String para formatação
    @FXML private TableColumn<Veiculo, Integer> colKM;

    @FXML private TextField txtBuscaMarca, txtBuscaModelo, txtBuscaAno, txtBuscaPreco;
    @FXML private TextField txtAno, txtCor, txtPreco, txtKM;
    @FXML private TextField txtNovaMarca, txtNovoModelo, txtNovoAno, txtNovaCor, txtNovoKM, txtNovoPreco;
    @FXML private ComboBox<String> cbBuscaStatus, cbStatus;
    @FXML private ComboBox<Marca> cbMarca, cbMarcaAux;
    @FXML private ComboBox<Modelo> cbModelo;

    private final ApiService apiService = new ApiService();
    private Long veiculoIdSelecionado = null;
    
    // Formatador para Real Brasileiro
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDadosIniciais();
        
        cbMarca.setOnAction(e -> carregarModelos(cbMarca, cbModelo));
        
        tableVeiculos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarVeiculoParaEdicao(newSelection);
            }
        });
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarca.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getModelo() != null ? cellData.getValue().getModelo().getMarca().getNome() : "N/A"));
        colModelo.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getModelo() != null ? cellData.getValue().getModelo().getNome() : "N/A"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        
        // Formatação de Preço na Tabela
        colPreco.setCellValueFactory(cellData -> {
            BigDecimal preco = cellData.getValue().getPreco();
            return new SimpleStringProperty(preco != null ? nf.format(preco) : "R$ 0,00");
        });

        colKM.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<String> statusList = FXCollections.observableArrayList("Disponível", "Vendido", "Em Manutenção");
        cbBuscaStatus.setItems(FXCollections.observableArrayList(statusList));
        cbStatus.setItems(FXCollections.observableArrayList(statusList));
    }

    private void carregarDadosIniciais() {
        try {
            atualizarTabela();
            List<Marca> marcas = apiService.listarMarcas();
            cbMarca.setItems(FXCollections.observableArrayList(marcas));
            cbMarcaAux.setItems(FXCollections.observableArrayList(marcas));
        } catch (Exception e) {
            alerta("Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }

    private void carregarModelos(ComboBox<Marca> comboMarca, ComboBox<Modelo> comboModelo) {
        Marca marca = comboMarca.getValue();
        if (marca != null) {
            try {
                comboModelo.setItems(FXCollections.observableArrayList(apiService.listarModelos(marca.getId())));
            } catch (Exception e) {
                alerta("Erro ao carregar modelos.");
            }
        }
    }

    private void carregarVeiculoParaEdicao(Veiculo v) {
        veiculoIdSelecionado = v.getId();
        txtAno.setText(v.getAno().toString());
        txtCor.setText(v.getCor());
        
        // Formata o preço ao carregar para edição
        txtPreco.setText(v.getPreco() != null ? formatarParaInput(v.getPreco()) : "");
        
        txtKM.setText(v.getQuilometragem().toString());
        cbStatus.setValue(v.getStatus());
        
        if (v.getModelo() != null) {
            cbMarca.setValue(v.getModelo().getMarca());
            carregarModelos(cbMarca, cbModelo);
            cbModelo.setValue(v.getModelo());
        }
    }

    @FXML
    private void filtrar() { 
        atualizarTabela(); 
    }

    @FXML
    private void limparFiltros() {
        txtBuscaMarca.clear(); 
        txtBuscaModelo.clear(); 
        txtBuscaAno.clear(); 
        txtBuscaPreco.clear(); 
        cbBuscaStatus.setValue(null);
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            Integer ano = null;
            if (txtBuscaAno != null && !txtBuscaAno.getText().isEmpty()) {
                ano = Integer.parseInt(txtBuscaAno.getText());
            }
            
            BigDecimal precoMax = null;
            if (txtBuscaPreco != null && !txtBuscaPreco.getText().isEmpty()) {
                precoMax = converterParaBigDecimal(txtBuscaPreco.getText());
            }

            List<Veiculo> veiculos = apiService.listarVeiculos(
                txtBuscaMarca.getText(), 
                txtBuscaModelo.getText(), 
                cbBuscaStatus.getValue(),
                ano,
                precoMax
            );
            tableVeiculos.setItems(FXCollections.observableArrayList(veiculos));
        } catch (Exception e) {
            alerta("Erro ao atualizar tabela: " + e.getMessage());
        }
    }

    @FXML
    private void salvar() {
        try {
            if (!validarCampos()) return;

            Veiculo v = new Veiculo();
            v.setId(veiculoIdSelecionado);
            v.setModelo(cbModelo.getValue());
            v.setAno(Integer.parseInt(txtAno.getText()));
            v.setCor(txtCor.getText());
            v.setPreco(converterParaBigDecimal(txtPreco.getText()));
            v.setQuilometragem(Integer.parseInt(txtKM.getText()));
            v.setStatus(cbStatus.getValue());

            apiService.salvarVeiculo(v);
            limparFormulario();
            atualizarTabela();
            alerta("Veículo salvo com sucesso!");
        } catch (Exception e) {
            alerta("Erro ao salvar: Verifique os campos numéricos.");
        }
    }

    private boolean validarCampos() {
        if (cbModelo.getValue() == null || txtAno.getText().isEmpty() || 
            txtCor.getText().isEmpty() || txtPreco.getText().isEmpty() || 
            txtKM.getText().isEmpty() || cbStatus.getValue() == null) {
            alerta("Erro: Todos os campos são obrigatórios.");
            return false;
        }
        return true;
    }

    @FXML
    private void salvarMarca() {
        try {
            if (txtNovaMarca.getText().isEmpty()) {
                alerta("Erro: Digite o nome da marca.");
                return;
            }
            apiService.salvarMarca(new Marca(txtNovaMarca.getText()));
            txtNovaMarca.clear();
            carregarDadosIniciais();
            alerta("Marca cadastrada!");
        } catch (Exception e) { alerta("Erro ao cadastrar marca."); }
    }

    @FXML
    private void salvarModelo() {
        try {
            if (txtNovoModelo.getText().isEmpty() || cbMarcaAux.getValue() == null) {
                alerta("Erro: Selecione a marca e digite o nome do modelo.");
                return;
            }

            Modelo novoModelo = apiService.salvarModelo(new Modelo(txtNovoModelo.getText(), cbMarcaAux.getValue()));

            if (!txtNovoAno.getText().isEmpty() && !txtNovoPreco.getText().isEmpty()) {
                Veiculo v = new Veiculo();
                v.setModelo(novoModelo);
                v.setAno(Integer.parseInt(txtNovoAno.getText()));
                v.setCor(txtNovaCor.getText());
                v.setQuilometragem(Integer.parseInt(txtNovoKM.getText()));
                v.setPreco(converterParaBigDecimal(txtNovoPreco.getText()));
                v.setStatus("Disponível");

                apiService.salvarVeiculo(v);
                alerta("Modelo e Veículo cadastrados!");
            } else {
                alerta("Modelo cadastrado!");
            }

            txtNovoModelo.clear(); txtNovoAno.clear(); txtNovaCor.clear();
            txtNovoKM.clear(); txtNovoPreco.clear();
            atualizarTabela(); carregarDadosIniciais();
        } catch (Exception e) { alerta("Erro ao cadastrar."); }
    }

    @FXML
    private void remover() {
        Veiculo selecionado = tableVeiculos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                apiService.deletarVeiculo(selecionado.getId());
                limparFormulario();
                atualizarTabela();
            } catch (Exception e) { alerta("Erro ao remover"); }
        }
    }

    @FXML
    private void limparFormulario() {
        veiculoIdSelecionado = null;
        txtAno.clear(); txtCor.clear(); txtPreco.clear(); txtKM.clear();
        cbMarca.setValue(null); cbModelo.setValue(null); cbStatus.setValue(null);
        tableVeiculos.getSelectionModel().clearSelection();
    }

    // --- MÉTODOS DE CONVERSÃO E FORMATAÇÃO ---

    private BigDecimal converterParaBigDecimal(String valorStr) {
        if (valorStr == null || valorStr.isEmpty()) return BigDecimal.ZERO;
        try {
            // Remove símbolos de moeda, pontos de milhar e troca vírgula por ponto
            String limpo = valorStr.replaceAll("[R$\\s.]", "").replace(",", ".");
            return new BigDecimal(limpo);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private String formatarParaInput(BigDecimal valor) {
        if (valor == null) return "";
        // Formata para o padrão brasileiro sem o "R$" para facilitar a edição
        String formatado = nf.format(valor).replace("R$", "").trim();
        return formatado;
    }

    private void alerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
