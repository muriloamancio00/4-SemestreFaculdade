package ifpr.pgua.eic.projetovendas.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Pessoa;

public interface PessoaDAO {
    
    boolean cadastrar(Pessoa p) throws Exception;
    boolean atualizar(int id, Pessoa p) throws Exception;
    boolean remover(int id) throws Exception;
    ArrayList<Pessoa> listar() throws Exception;
    Pessoa buscar(int id) throws Exception;
    Pessoa buscarPessoaVenda(int idVenda) throws Exception;

}
