package ifpr.pgua.eic.projetovendas.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.mysql.cj.protocol.x.ReusableOutputStream;

public class Venda {
    
    private int id;
    private Pessoa pessoa;
    private LocalDateTime dataHora;
    private double valorTotal;
    private ArrayList<ItemVenda> itens;

    public Venda(int id, Pessoa pessoa, LocalDateTime dataHora, double valorTotal, ArrayList<ItemVenda> itens) {
        this.setId(id);
        this.setPessoa(pessoa);
        this.setDataHora(dataHora);
        this.setValorTotal(valorTotal);
        this.setItens(itens);
    }


    public Venda(Pessoa pessoa, LocalDateTime dataHora) {
        this(-1, pessoa, dataHora, 0.0, new ArrayList<>());
    }

    public void adicionar(ItemVenda itemAdd){
        for(ItemVenda item:itens){
            if(item.getProduto().getId() == itemAdd.getProduto().getId()){
                item.setQuantidade(item.getQuantidade()+itemAdd.getQuantidade());
                return;
            }
        }
        itens.add(itemAdd);
    }

    public double calcularTotal(){
        double total = 0.0;
        for(ItemVenda item:itens){
            total += item.getValor()*item.getQuantidade();
        }

        this.valorTotal = total;
        
        return this.valorTotal;
    }

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Pessoa:"+pessoa.getNome()+"Itens:"+itens;
    }


    

    
}
