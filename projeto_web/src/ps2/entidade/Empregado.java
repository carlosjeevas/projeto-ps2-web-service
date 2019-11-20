package ps2.entidade;

public class Empregado {

    private long id;
    private long id_emp;
    private String nome;

    public Empregado() {
    }

    public Empregado(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Empregado(long id, long id_emp, String nome) {
        this.id = id;
        this.id_emp = id_emp;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public long getId_emp() {
        return id_emp;
    }

    public String getNome() {
        return nome;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId_emp(long id_emp) {
        this.id_emp = id_emp;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
