package ifpr.pgua.eic.projetovendas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FabricaConexoes {
    
    private static int MAX_CONNECTIONS=5;
    private static String URL_DB = "//wagnerweinert.com.br:3306/";
    private static String DB_NAME = "tads20_murilo";
    private static String USERNAME = "tads20_murilo";
    private static String PASSWORD = "tads20_murilo";

    private static String CON_STRING = "jdbc:mysql:"+URL_DB+DB_NAME;

    private Connection[] conexoes;

    private static FabricaConexoes instance;

    private FabricaConexoes(){
        conexoes = new Connection[MAX_CONNECTIONS];
    }

    public static FabricaConexoes getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new FabricaConexoes();
        return instance;
    }

    public Connection getConnection() throws SQLException{

        for(int i=0; i<MAX_CONNECTIONS; i++){
            if(instance.conexoes[i]==null || instance.conexoes[i].isClosed()){
                instance.conexoes[i] = DriverManager.getConnection(CON_STRING, USERNAME, PASSWORD);
                return instance.conexoes[i];
            }
        }
        throw new SQLException("Maximo de conexao");
    }
}
