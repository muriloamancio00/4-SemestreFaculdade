package ifpr.pgua.eic.projetovendas.telas;

import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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


    private RepositorioVendas repositorio;

    public CadastroProduto(RepositorioVendas repositorio){
        this.repositorio = repositorio;
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
            boolean ret = repositorio.cadastrarProduto(nome, descricao, quantidadeEstoque, valor);
            if(ret){
                msg = "Produto cadastrado com sucesso!";
                limpar();
            }else{
                msg = "Erro ao cadastrar produto!";
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
    }



}
