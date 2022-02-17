package ifpr.pgua.eic.projetovendas.telas;

import ifpr.pgua.eic.projetovendas.models.ItemVenda;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RealizaVenda {

    @FXML
    private ComboBox<Pessoa> cbPessoas;

    @FXML
    private TextField tfDataHora;

    @FXML
    private ComboBox<Produto> cbProdutos;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<ItemVenda> tbItensVenda;

    @FXML
    private TableColumn<ItemVenda,String> tbcProduto;

    @FXML
    private TableColumn<ItemVenda,Double> tbcValorUnitario;

    @FXML
    private TableColumn<ItemVenda,Integer> tbcQuantidade;

    @FXML
    private TableColumn<ItemVenda,Double> tbcSubTotal;


    @FXML
    private Button btAdicionaProduto;

    @FXML
    private Button btFinalizarVenda;



    private RepositorioPessoas repositorioPessoas;

    private RepositorioProdutos repositorioProdutos;
    private RepositorioVendas repositorioVendas;

    public RealizaVenda(RepositorioPessoas repositorioPessoas, RepositorioProdutos repositorioProdutos,
            RepositorioVendas repositorioVendas) {
        this.repositorioPessoas = repositorioPessoas;
        this.repositorioProdutos = repositorioProdutos;
        this.repositorioVendas = repositorioVendas;
    }

    @FXML
    public void initialize(){
        
        btAdicionaProduto.setDisable(true);
        cbProdutos.setDisable(true);
        tfQuantidade.setDisable(true);
        btFinalizarVenda.setDisable(true);
        tbItensVenda.setDisable(true);
        
        tbcProduto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getNome()));
        tbcValorUnitario.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValor()).asObject());
        tbcQuantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        tbcSubTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSubTotal()).asObject());
        
        try{
            cbPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
            cbProdutos.getItems().addAll(repositorioProdutos.listarProdutos());    
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR,e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void iniciarVenda(){
        Pessoa pessoa = cbPessoas.getSelectionModel().getSelectedItem();
        if(pessoa != null){
            repositorioVendas.iniciaVenda(pessoa);
            tfDataHora.setText(repositorioVendas.getVenda().getDataHora().toString());
            
            cbPessoas.setDisable(true);
            btAdicionaProduto.setDisable(false);
            cbProdutos.setDisable(false);
            tfQuantidade.setDisable(false);
            btFinalizarVenda.setDisable(false);
            tbItensVenda.setDisable(false);
        }else{
            Alert alert = new Alert(AlertType.ERROR,"Selecione uma pessoa!");
            alert.showAndWait();
        }
    }

    @FXML
    private void adicionarItem(){
        String strQuantidade = tfQuantidade.getText();
        Produto produto = cbProdutos.getSelectionModel().getSelectedItem();

        int quantidade = 0;
        boolean temErro = false;
        String msg = "";

        try{
            quantidade = Integer.parseInt(strQuantidade);
        }catch(NumberFormatException e){
            temErro = true;
            msg = "Quantidade inválida!\n";
        }

        if(quantidade <= 0 ){
            temErro = true;
            msg = "Quantidade inválida!\n";
        }

        if(produto == null){
            temErro = true;
            msg += "Selecione um produto!\n";
        }

        if(!temErro){
            repositorioVendas.adicionaProduto(produto, quantidade);

            atualizaTabela();

            cbProdutos.getSelectionModel().clearSelection();
            tfQuantidade.clear();
        }else{
            Alert alert = new Alert(AlertType.ERROR,msg);
            alert.showAndWait();
        }
    }

    private void atualizaTabela(){
        tbItensVenda.getItems().clear();
        tbItensVenda.getItems().addAll(repositorioVendas.getVenda().getItens());
    }

    @FXML
    private void finalizarVenda(){
        if(repositorioVendas.getVenda().getItens().size() > 0){
            try{
                repositorioVendas.finalizaVenda();
                reiniciar();
            }catch(Exception e){
                Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                alert.showAndWait();
            }
            
        }
    }

    private void reiniciar(){
        tbItensVenda.getItems().clear();
        btAdicionaProduto.setDisable(true);
        cbProdutos.setDisable(true);
        tfQuantidade.setDisable(true);
        btFinalizarVenda.setDisable(true);
        tbItensVenda.setDisable(true);
    }

}
