package ifpr.pgua.eic.projetovendas.models;

public class Pessoa {
    private int id;
    private String nome;
    private String email;
    private String telefone;

    public Pessoa(int id,String nome, String email, String telefone){
        this.setId(id);
        this.nome = nome;
        this.setEmail(email);
        this.setTelefone(telefone);
    }
    
    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Pessoa(String nome, String email, String telefone){
        this(-1, nome, email, telefone);
    }



    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return this.nome;
    }

}
