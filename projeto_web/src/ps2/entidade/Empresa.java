package ps2.entidade;

public class Empresa {

    private long id_emp;
    private String nome;

    public Empresa(long id_emp, String nome) {
        this.id_emp = id_emp;
        this.nome = nome;
    }

    public long getId() {
        return id_emp;
    }

    public void setId(long id_emp) {
        this.id_emp = id_emp;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}