package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.models.Produto;

public class RepositorioVendas {
    
    private ArrayList<Pessoa> pessoas;
    private ArrayList<Produto> produtos;

    public RepositorioVendas(){
        pessoas = new ArrayList<>();
        produtos = new ArrayList<>();
    }

    public boolean cadastrarPessoa(String nome, String email, String telefone){
        if(buscarPessoa(email)==null){
            Pessoa p = new Pessoa(nome,email,telefone);
            this.pessoas.add(p);
            
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app","user","user12345");

                Statement stm = con.createStatement();

                String sql = "INSERT INTO pessoas(nome,email,telefone) VALUES ('"+nome+"','"+email+"','"+telefone+"')";

                stm.execute(sql,Statement.RETURN_GENERATED_KEYS);
                
                //pegando o id gerado para a pessoa
                ResultSet rsId = stm.getGeneratedKeys();
                rsId.next();
                int id = rsId.getInt(1);
                
                rsId.close();
                stm.close();
                con.close();

                p.setId(id);

                
                return true;
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public Pessoa buscarPessoa(String email){
        return this.pessoas.stream().filter((pessoa)->pessoa.getEmail().equals(email)).findFirst().orElse(null);
    }

    public ArrayList<Pessoa> listarPessoas(){
        return pessoas;
    }

    public boolean cadastrarProduto(String nome, String descricao, int quantidadeEstoque, double valor){

        if(buscarProduto(nome) == null){
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app","user","user12345");

                Statement stm = con.createStatement();

                String sql = "INSERT INTO produtos(nome,descricao,quantidadeEstoque,valor) VALUES ('"+nome+"','"+descricao+"',"+quantidadeEstoque+","+valor+")";
                
                stm.execute(sql,Statement.RETURN_GENERATED_KEYS);
                
                //pegando o id gerado para o produto
                ResultSet rsId = stm.getGeneratedKeys();
                rsId.next();
                int id = rsId.getInt(1);
                
                rsId.close();
                stm.close();
                con.close();

                Produto p = new Produto(nome, descricao, quantidadeEstoque, valor);
                
                p.setId(id);

                this.produtos.add(p);
            
                return true;
            }catch(SQLException e){
                e.printStackTrace();
                return false;
            }
            

        }

        return false;
    }

    public Produto buscarProduto(String nome){
        return this.produtos.stream().filter((produto)->produto.getNome().equals(nome)).findFirst().orElse(null);
    }

    public ArrayList<Produto> listarProdutos(){
        return this.produtos;
    }
    
}
