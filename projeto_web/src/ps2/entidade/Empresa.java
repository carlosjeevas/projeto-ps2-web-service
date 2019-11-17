package ps2.entidade;

public class Empresa {

    private long id_emp;
    private String nome;

    public Empresa() {
    }

    public Empresa(long id, String n) {
        this.id_emp = id;
        nome = n;
    }

    public long getId() {
        return id_emp;
    }

    public String getNome() {
        return nome;
    }

    public void setId(long id) {
        this.id_emp = id;
    }

    public void setNome(String n) {
        nome = n;
    }
}
