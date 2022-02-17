package ifpr.pgua.eic.projetovendas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import ifpr.pgua.eic.projetovendas.daos.JDBCPessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.JSONPessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import ifpr.pgua.eic.projetovendas.telas.Home;

/**
 * JavaFX App
 */
public class App extends Application {

    PessoaDAO pessoaDAO = new JDBCPessoaDAO();
    
    RepositorioVendas repositorio = new RepositorioVendas();
    RepositorioPessoas repositorioPessoas = new RepositorioPessoas(pessoaDAO);


    @Override
    public void start(Stage stage) throws IOException {
        

        Scene scene = new Scene(loadTela("fxml/home.fxml", o->new Home(repositorioPessoas,repositorio)), 640, 480);
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