package ifpr.pgua.eic.projetovendas.models;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private int quantidadeEstoque;
    private double valor;

    public Produto(int id, String nome, String descricao, int quantidadeEstoque, double valor) {
        this.setId(id);
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setValor(valor);
    }

    public Produto(String nome, String descricao, int quantidadeEstoque, double valor) {
        this(-1, nome, descricao, quantidadeEstoque, valor);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "("+id+")"+this.nome;
    }
    
    
}   
