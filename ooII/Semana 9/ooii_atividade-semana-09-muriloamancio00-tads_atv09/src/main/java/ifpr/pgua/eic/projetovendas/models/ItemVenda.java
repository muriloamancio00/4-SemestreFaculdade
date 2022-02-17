package ifpr.pgua.eic.projetovendas.models;

public class ItemVenda {
    
    private int id;
    private Produto produto;
    private int quantidade;
    private double valor;

    public ItemVenda(int id, Produto produto, int quantidade, double valor) {
        this.setId(id);
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.setValor(valor);
    }

    public ItemVenda(Produto produto, int quantidade, double valor) {
        this(-1,produto,quantidade,valor);
    }

    public double getSubTotal(){
        return quantidade*valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return produto.getNome()+"-"+quantidade;
    }



    
}
