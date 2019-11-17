package ps2.entidade;

public class Empregado {

    private long id_empregado;
    private long id_emp;
    private String nome_empregado;

    public Empregado() {
    }

    public Empregado(long id_empregado, long id_emp, String nome_empregado) {
        this.id_empregado = id_empregado;
        this.id_emp = id_emp;
        this.nome_empregado = nome_empregado;
    }

    public long getId() {
        return id_empregado;
    }

    public long getId_Emp() {
        return id_emp;
    }

    public String getNome() {
        return nome_empregado;
    }

    public void setId(long id_empregado) {
        this.id_empregado = id_empregado;
    }

    public void setIdEmp(int id_emp) {
        this.id_emp = id_emp;
    }

    public void setNome(String n) {
        nome_empregado = n;
    }
}
