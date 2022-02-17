package ifpr.pgua.eic.projetovendas.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroProduto {
    
    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfQuantidadeEstoque;

    @FXML
    private TextField tfValor;

    @FXML
    private Button btCadastrar;


    private RepositorioProdutos repositorio;
    private Produto produtoAntigo = null;
    
    public CadastroProduto(RepositorioProdutos repositorio){
        this(null,repositorio);
    }

    public CadastroProduto(Produto produtoAntigo, RepositorioProdutos repositorio){
        this.repositorio = repositorio;
        this.produtoAntigo = produtoAntigo;
    }

    @FXML
    public void initialize(){
        if(produtoAntigo != null){
            tfNome.setText(produtoAntigo.getNome());
            tfDescricao.setText(produtoAntigo.getDescricao());
            tfQuantidadeEstoque.setText(""+produtoAntigo.getQuantidadeEstoque());
            tfValor.setText(""+produtoAntigo.getValor());

            btCadastrar.setText("Atualizar");
        }
    }

    @FXML
    private void cadastrar(){
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        String strQuantidadeEstoque = tfQuantidadeEstoque.getText();
        String strValor = tfValor.getText();

        boolean temErro = false;
        String msg="";

        int quantidadeEstoque = 0;
        double valor = 0;

        if(nome.isEmpty() || nome.isBlank()){
            temErro = true;
            msg += "Nome não pode ser vazio!\n";
        }

        if(descricao.isEmpty() || descricao.isBlank()){
            temErro = true;
            msg += "Descrição não pode ser vazio!\n";
        }

        try{
            quantidadeEstoque = Integer.parseInt(strQuantidadeEstoque);
        }catch(NumberFormatException e){
            temErro = true;
            msg += "Quantidade em estoque inválida!";
        }

        try{
            valor = Double.parseDouble(strValor);
        }catch(NumberFormatException e){
            temErro = true;
            msg += "Valor inválido!";
        }


        if(!temErro){
            try{
                boolean ret = false;
                if(produtoAntigo != null){
                    ret = repositorio.atualizar(produtoAntigo.getId(), nome, descricao, quantidadeEstoque, valor);
                }else{
                    ret = repositorio.cadastrarProduto(nome, descricao, quantidadeEstoque, valor);
                }
                
                if(ret){
                    msg = "Produto cadastrado com sucesso!";
                    limpar();
                }else{
                    msg = "Erro ao cadastrar produto!";
                }
            }catch(SQLException e){
                temErro = true;
                msg = e.getMessage();
            } 
        }

        Alert alert = new Alert(temErro?AlertType.ERROR:AlertType.INFORMATION,msg);
        alert.showAndWait();
    }

    @FXML
    private void limpar(){
        tfDescricao.clear();
        tfNome.clear();
        tfQuantidadeEstoque.clear();
        tfValor.clear();
    }



}
