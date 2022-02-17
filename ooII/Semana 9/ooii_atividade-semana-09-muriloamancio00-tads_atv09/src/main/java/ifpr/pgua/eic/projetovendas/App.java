package ifpr.pgua.eic.projetovendas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import ifpr.pgua.eic.projetovendas.daos.JDBCPessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.JDBCProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.JDBCVendaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import ifpr.pgua.eic.projetovendas.telas.Home;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

/**
 * JavaFX App
 */
public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    PessoaDAO pessoaDAO = new JDBCPessoaDAO(fabricaConexoes);
    ProdutoDAO produtoDAO = new JDBCProdutoDAO(fabricaConexoes);
    VendaDAO vendaDAO = new JDBCVendaDAO(fabricaConexoes);

    RepositorioProdutos repositorioProdutos = new RepositorioProdutos(produtoDAO);
    RepositorioPessoas repositorioPessoas = new RepositorioPessoas(pessoaDAO);
    RepositorioVendas repositorioVendas = new RepositorioVendas(vendaDAO,pessoaDAO,produtoDAO);

    @Override
    public void start(Stage stage) throws IOException {
        

        Scene scene = new Scene(loadTela("fxml/home.fxml", o->new Home(repositorioPessoas,repositorioProdutos,repositorioVendas)), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    
    public static Parent loadTela(String fxml, Callback controller){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            loader.setControllerFactory(controller);

            root = loader.load();
            
        }catch (Exception e){
            System.out.println("Problema no arquivo fxml. Está correto?? "+fxml);
            e.printStackTrace();
            System.exit(0);
        }
        return root;   
    }

    public static void main(String[] args) {
        launch();
    }

}