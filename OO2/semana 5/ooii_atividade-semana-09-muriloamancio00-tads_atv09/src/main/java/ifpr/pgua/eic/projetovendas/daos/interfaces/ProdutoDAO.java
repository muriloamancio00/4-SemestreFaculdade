package ifpr.pgua.eic.projetovendas.daos.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Produto;

public interface ProdutoDAO {

    boolean cadastrar(Produto p) throws SQLException;
    boolean atualizar(int id, Produto p) throws SQLException;
    boolean remover(int id) throws SQLException;
    Produto buscarId(int id) throws SQLException;
    ArrayList<Produto> buscarNome(String nome) throws SQLException;
    ArrayList<Produto> lista() throws SQLException;

}
