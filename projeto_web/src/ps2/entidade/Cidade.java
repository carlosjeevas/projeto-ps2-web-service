package ps2.entidade;

public class Cidade {

    private long id;
    private String nome;
    private String estado;
    
    public Cidade () {}

    public Cidade(long id, String nome, String estado) {
        this.id = id;
        this.nome = nome;
        this.estado = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return nome;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}