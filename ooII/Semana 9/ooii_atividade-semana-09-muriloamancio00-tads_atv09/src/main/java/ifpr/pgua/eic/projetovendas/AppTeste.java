package ifpr.pgua.eic.projetovendas;

import java.io.IOException;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.JDBCPessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.JDBCProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.JDBCVendaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioPessoas;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioProdutos;
import ifpr.pgua.eic.projetovendas.repositorios.RepositorioVendas;
import ifpr.pgua.eic.projetovendas.telas.Home;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

/**
 * JavaFX App
 */
public class AppTeste {

    public static void main(String[] args) throws Exception {
        FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

        JDBCVendaDAO vendaDAO = new JDBCVendaDAO(fabricaConexoes);

        System.out.println(vendaDAO.totalVendasPessoa(14));
        
        
    }

}