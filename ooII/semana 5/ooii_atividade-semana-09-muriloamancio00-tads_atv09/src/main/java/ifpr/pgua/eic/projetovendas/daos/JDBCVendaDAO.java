package ifpr.pgua.eic.projetovendas.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

import ifpr.pgua.eic.projetovendas.daos.interfaces.VendaDAO;
import ifpr.pgua.eic.projetovendas.models.ItemVenda;
import ifpr.pgua.eic.projetovendas.models.Venda;
import ifpr.pgua.eic.projetovendas.utils.FabricaConexoes;

public class JDBCVendaDAO implements VendaDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCVendaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }

    private void salvarItens(Venda venda) throws Exception{
        Connection con = fabricaConexoes.getConnection();
        String sql = "INSERT INTO itensvendas(idVenda,idProduto,quantidade,valor) VALUES (?,?,?,?)";
        
        PreparedStatement pstmt = con.prepareStatement(sql);

        for(ItemVenda item:venda.getItens()){
            pstmt.setInt(1,venda.getId());
            pstmt.setInt(2,item.getProduto().getId());
            pstmt.setInt(3,item.getQuantidade());
            pstmt.setDouble(4, item.getValor());

            pstmt.execute();
        }

        pstmt.close();
        con.close();
    
    }


    @Override
    public boolean salvar(Venda venda) throws Exception {
        
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO vendas(idPessoa,dataHora,valorTotal) VALUES (?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        
        pstmt.setInt(1, venda.getPessoa().getId());
        pstmt.setTimestamp(2, Timestamp.valueOf(venda.getDataHora()));
        pstmt.setDouble(3, venda.calcularTotal());

        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();
        rsId.next();
        int id = rsId.getInt(1);

        venda.setId(id);

        rsId.close();
        pstmt.close();
        con.close();
        
        salvarItens(venda);

        return true;
    }

    private ArrayList<ItemVenda> carregarItensVenda(int idVenda) throws Exception{
        ArrayList<ItemVenda> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM itensvendas WHERE idVenda=?";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, idVenda);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("id");
            int quantidade = rs.getInt("quantidade");
            double valor = rs.getDouble("valor");

            ItemVenda item = new ItemVenda(id,null, quantidade, valor);

            lista.add(item);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }


    @Override
    public ArrayList<Venda> listar() throws Exception {
        ArrayList<Venda> lista = new ArrayList<>();
        
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM vendas";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            int id = rs.getInt("id");
            LocalDateTime dataHora = rs.getTimestamp("dataHora").toLocalDateTime();
            double valorTotal = rs.getDouble("valorTotal");

            ArrayList<ItemVenda> itens = carregarItensVenda(id);

            Venda venda = new Venda(id, null, dataHora, valorTotal, itens);

            lista.add(venda);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Venda buscar(int id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double totalVendas() throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL total_vendas(?)";

        CallableStatement call = con.prepareCall(sql);

        call.registerOutParameter(1, Types.REAL);

        call.execute();

        double totalVendas = call.getDouble(1);

        call.close();
        con.close();
        
        return totalVendas;
    }

    @Override
    public double totalVendasPessoa(int idPessoa) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL total_vendas_cliente(?,?)";

        CallableStatement call = con.prepareCall(sql);

        call.setInt(1,idPessoa);
        call.registerOutParameter(2, Types.REAL);

        call.execute();

        double total = call.getDouble(2);

        call.close();
        con.close();

        return total;
    }
    



}
