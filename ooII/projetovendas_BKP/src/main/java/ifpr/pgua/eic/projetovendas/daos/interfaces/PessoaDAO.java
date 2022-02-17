package ifpr.pgua.eic.projetovendas.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Pessoa;

public interface PessoaDAO {
    
    boolean cadastrar(Pessoa p) throws Exception;
    boolean atualizar(int id, Pessoa p) throws Exception;
    boolean remover(Pessoa p) throws Exception;
    ArrayList<Pessoa> listar() throws Exception;

}
