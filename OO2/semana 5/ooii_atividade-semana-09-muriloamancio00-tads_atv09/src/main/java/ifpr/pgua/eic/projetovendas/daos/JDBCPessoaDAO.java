package ifpr.pgua.eic.projetovendas.daos;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

public class JDBCPessoaDAO implements PessoaDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCPessoaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Pessoa p) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO pessoas(nome,email,telefone) VALUES (?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getEmail());
        pstmt.setString(3, p.getTelefone());

        pstmt.execute();

        // pegando o id gerado para a pessoa
        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        p.setId(id);

        return true;

    } 

    @Override
    public boolean atualizar(int id, Pessoa p) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE pessoas SET nome=?,email=?,telefone=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getEmail());
        pstmt.setString(3, p.getTelefone());
        pstmt.setInt(4, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;

    }

    @Override
    public boolean remover (int id) throws SQLException {
        Connection con = fabricaConexoes.getConnection();
    
        //String sql = "DELETE * FROM produtos WHERE id=?";

        String sql = "UPDATE pessoas SET ativos =0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret ==1;
    }

    @Override
    public ArrayList<Pessoa> listar() throws Exception {
        ArrayList<Pessoa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pessoas WHERE ativos =1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String telefone = rs.getString("telefone");

            Pessoa p = new Pessoa(id,nome, email, telefone);

            lista.add(p);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Pessoa buscar(int id) throws Exception {
        Pessoa p = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM pessoas WHERE id = ? and ativos = 1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        

        while(rs.next()){
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String telefone = rs.getString("telefone");

            p = new Pessoa(id,nome, email, telefone);

        }

        rs.close();
        pstmt.close();
        con.close();

        return p;
    }
}


