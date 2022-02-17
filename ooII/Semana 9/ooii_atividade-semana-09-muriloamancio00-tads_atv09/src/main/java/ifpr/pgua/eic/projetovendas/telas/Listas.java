package ifpr.pgua.eic.projetovendas.telas;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ifpr.pgua.eic.projetovendas.App;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.models.Venda;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;


public class Listas {

    @FXML
    private ListView<Pessoa> lstPessoas;

    @FXML
    private ListView<Produto> lstProdutos;

    @FXML
    private Label lbListaVaziaProdutos;

    @FXML
    private Label lbTotalVendas;

    @FXML
    private FlowPane rootPane;

    @FXML
    private TableView<Venda> tbVendas;
    
    @FXML
    private TableColumn<Venda,Integer> tbcIdVenda;

    @FXML
    private TableColumn<Venda,String> tbcDataVenda;

    @FXML
    private TableColumn<Venda,String> tbcClienteVenda;

    @FXML
    private TableColumn<Venda,Double> tbcTotalVenda;

    
    private RepositorioProdutos repositorio;
    private RepositorioPessoas repositorioPessoas;
    private RepositorioVendas repositorioVendas;

    public Listas(RepositorioPessoas repositorioPessoas, RepositorioProdutos repositorio, RepositorioVendas repositorioVendas){
        this.repositorio = repositorio;
        this.repositorioPessoas = repositorioPessoas;
        this.repositorioVendas = repositorioVendas;
    }

    public void initialize(){

        lstPessoas.setCellFactory(lista -> new ListCell<>(){
            protected void updateItem(Pessoa pessoa, boolean alterou) {
                super.updateItem(pessoa, alterou);
                if(pessoa != null){
                    setText("("+pessoa.getId()+")"+pessoa.getNome());
                }else{
                    setText(null);
                }
            };
        });

        //configura colunas

        tbcIdVenda.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        tbcDataVenda.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tbcClienteVenda.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPessoa().getNome()));
        tbcTotalVenda.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());
        
        
        try{
            lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
            List<Produto> produtos = repositorio.listarProdutos();
            lstProdutos.getItems().addAll(produtos);
            
            
            tbVendas.getItems().addAll(repositorioVendas.listar());

            lbTotalVendas.setText("Total Vendas R$ "+repositorioVendas.totalVendas());
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

        
        
    }

    @FXML
    private void atualizarRemoverPessoa(MouseEvent event){
        Pessoa pessoaSelecionada = lstPessoas.getSelectionModel().getSelectedItem();

        if(event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2){
            if(pessoaSelecionada != null){
                try{
                    repositorioPessoas.removerPessoa(pessoaSelecionada.getId());
                    lstPessoas.getItems().clear();
                    lstPessoas.getItems().addAll(repositorioPessoas.listarPessoas());
                }catch(Exception e){
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
                
            }
        }else if(event.getClickCount() == 2){
            
            if(pessoaSelecionada != null){
                //substituir o painelCentral do Home
                StackPane painelCentral = (StackPane) rootPane.getParent();

                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_pessoa.fxml", o->new CadastroPessoa(pessoaSelecionada, repositorioPessoas)));
            }
        }
    }

    @FXML
    private void atualizarRemoverProduto(MouseEvent event){
        Produto produtoSelecionado = lstProdutos.getSelectionModel().getSelectedItem();

        if(event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2){
            if(produtoSelecionado != null){
                try{
                    repositorio.remover(produtoSelecionado);
                    lstProdutos.getItems().clear();
                    lstProdutos.getItems().addAll(repositorio.listarProdutos());
                }catch(Exception e){
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
                
            }
        }else if(event.getClickCount() == 2){
            
            if(produtoSelecionado != null){
                //substituir o painelCentral do Home
                StackPane painelCentral = (StackPane) rootPane.getParent();

                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", o->new CadastroProduto(produtoSelecionado, repositorio)));
            }
        }
    }


}
