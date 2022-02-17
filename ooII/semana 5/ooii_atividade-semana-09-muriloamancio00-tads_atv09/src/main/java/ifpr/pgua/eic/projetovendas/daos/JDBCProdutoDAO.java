package ifpr.pgua.eic.projetovendas.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.ProdutoDAO;
import ifpr.pgua.eic.projetovendas.models.Produto;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

public class JDBCProdutoDAO implements ProdutoDAO {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    @Override
    public boolean cadastrar(Produto p) throws SQLException {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO produtos(nome,descricao,quantidadeEstoque,valor) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getDescricao());
        pstmt.setInt(3, p.getQuantidadeEstoque());
        pstmt.setDouble(4, p.getValor());

        pstmt.execute();

        //pegando o id gerado para o produto
        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        p.setId(id);

        rsId.close();
        pstmt.close();
        con.close();

        return true;
 
    }

    @Override
    public boolean atualizar(int id, Produto p) throws SQLException {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE produtos SET nome=?,descricao=?,quantidadeEstoque=?,valor=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getDescricao());
        pstmt.setInt(3, p.getQuantidadeEstoque());
        pstmt.setDouble(4, p.getValor());
        pstmt.setInt(5, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover (int id) throws SQLException {
        Connection con = fabricaConexoes.getConnection();

        //String sql = "DELETE * FROM produtos WHERE id=?";

        String sql = "UPDATE produtos SET ativos=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret ==1;
    }

    @Override
    public ArrayList<Produto> lista() throws SQLException {
        ArrayList<Produto> lista = new ArrayList<>();
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM produtos WHERE ativos=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            int quantidadeEstoque = rs.getInt("quantidadeEstoque");
            double valor = rs.getDouble("valor");

            Produto p = new Produto(id, nome, descricao, quantidadeEstoque, valor);

            lista.add(p);
        }
        rs.close();
        pstmt.close();
        con.close();
        return lista;
    }

    @Override
    public Produto buscarId(int id) throws SQLException {
        Produto p = null;
        
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM produtos WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            int quantidadeEstoque = rs.getInt("quantidadeEstoque");
            double valor = rs.getDouble("valor");

            p = new Produto(id, nome, descricao, quantidadeEstoque, valor);

        }
        rs.close();
        pstmt.close();
        con.close();

        return p;
    }

    @Override
    public ArrayList<Produto> buscarNome(String parte) throws SQLException {
        ArrayList<Produto> lista = new ArrayList<>();
        
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM produtos WHERE nome LIKE ? and ativos = 1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        //erro deve estar aqui
        pstmt.setString(1, parte+"%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            int quantidadeEstoque = rs.getInt("quantidadeEstoque");
            double valor = rs.getDouble("valor");

            Produto p = new Produto(id, nome, descricao, quantidadeEstoque, valor);

            lista.add(p);

        }
        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }
}

