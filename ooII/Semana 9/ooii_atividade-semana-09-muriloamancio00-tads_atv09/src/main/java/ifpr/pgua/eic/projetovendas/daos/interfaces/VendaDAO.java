package ifpr.pgua.eic.projetovendas.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Venda;

public interface VendaDAO {
    
    boolean salvar(Venda venda) throws Exception;
    ArrayList<Venda> listar() throws Exception;
    Venda buscar(int id) throws Exception;
    double totalVendas() throws Exception;
    double totalVendasPessoa(int idPessoa) throws Exception;

}
