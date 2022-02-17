package ifpr.pgua.eic.projetovendas.daos.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Produto;

public interface ProdutoDAO {

    boolean cadastrar(Produto p) throws Exception;
    boolean atualizar(int id, Produto p) throws Exception;
    boolean remover(Produto p) throws Exception;
    ArrayList<Produto> lista() throws Exception;
    Produto buscar(int id) throws Exception;
    Produto buscarProdutoItem(int idItem) throws Exception;
}
