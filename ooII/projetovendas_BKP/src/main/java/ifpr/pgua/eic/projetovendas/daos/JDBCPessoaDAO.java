package ifpr.pgua.eic.projetovendas.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;

public class JDBCPessoaDAO implements PessoaDAO {

    @Override
    public boolean cadastrar(Pessoa p) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user",
                "user12345");

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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remover(Pessoa p) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Pessoa> listar() throws Exception {
        ArrayList<Pessoa> lista = new ArrayList<>();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user",
                "user12345");

        String sql = "SELECT * FROM pessoas";

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

}
