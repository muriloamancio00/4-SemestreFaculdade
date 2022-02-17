package ifpr.pgua.eic.projetovendas.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.projetovendas.daos.interfaces.PessoaDAO;
import ifpr.pgua.eic.projetovendas.models.Pessoa;

public class RepositorioPessoas {

    private ArrayList<Pessoa> pessoas;

    private PessoaDAO pessoaDAO;

    public RepositorioPessoas(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
        pessoas = new ArrayList<>();
    }

    public boolean cadastrarPessoa(String nome, String email, String telefone) throws SQLException {
        Pessoa p = new Pessoa(nome, email, telefone);

        try {
            pessoaDAO.cadastrar(p);
            this.pessoas.add(p);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        
    }

    public boolean atualizarPessoa(int id, String nome, String email, String telefone) throws SQLException {

        Pessoa pessoa = new Pessoa(nome, email, telefone);

        try {
            return pessoaDAO.atualizar(id, pessoa);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerPessoa(int id) throws SQLException {
        try {
            return pessoaDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Pessoa buscar(int id) throws SQLException {
        try {
            return pessoaDAO.buscar(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Pessoa buscarPessoa(String email) {
        return this.pessoas.stream().filter((pessoa) -> pessoa.getEmail().equals(email)).findFirst().orElse(null);
    }

    public ArrayList<Pessoa> listarPessoas() throws Exception {
        return pessoaDAO.listar();
    }

}
