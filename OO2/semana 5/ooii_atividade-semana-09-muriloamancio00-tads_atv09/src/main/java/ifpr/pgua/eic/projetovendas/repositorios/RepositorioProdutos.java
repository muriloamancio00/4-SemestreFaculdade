package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.result.SqlDateValueFactory;

import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.models.Produto;

public class RepositorioProdutos {
    
    private ProdutoDAO produtoDAO;
    private ArrayList<Produto> produtos;

    public RepositorioProdutos(ProdutoDAO produtoDAO){
        produtos = new ArrayList<>();
        this.produtoDAO = produtoDAO;
    }

    public boolean cadastrarProduto(String nome, String descricao, int quantidadeEstoque, double valor) throws SQLException{

        if(buscarProduto(nome) == null){
            try{
                
                Produto p = new Produto(nome, descricao, quantidadeEstoque, valor);
                
                produtoDAO.cadastrar(p);

                this.produtos.add(p);
            
                return true;
            }catch(Exception e){
                throw new SQLException(e.getCause());
            }
        }

        return false;
    }


    public boolean atualizar(int id,String nome, String descricao, int quantidadeEstoque, double valor) throws SQLException{

        Produto produto = new Produto(nome, descricao, quantidadeEstoque, valor);
        
        try{
        
            produtoDAO.atualizar(id, produto);
        
        }catch(Exception e){
            throw new SQLException(e.getCause());
        }
        

        return true;
    }

    public boolean remover(Produto p) throws SQLException{
        try{
            return produtoDAO.remover(p.getId());
        }catch(Exception e){
            throw new SQLException(e.getCause());
        }
        
    }


    public Produto buscarProduto(String nome){
        return this.produtos.stream().filter((produto)->produto.getNome().equals(nome)).findFirst().orElse(null);
    }

    public ArrayList<Produto> listarProdutos() throws Exception{
        return produtoDAO.lista();
    }
    
}
