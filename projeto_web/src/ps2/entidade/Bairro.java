package ps2.entidade;

public class Bairro {

    private long id;
    private long id_cid;
    private String nome;
    
    public Bairro () {}

    public Bairro(long id, long id_cid, String nome) {
        this.id = id;
        this.id_cid = id_cid;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public long getid_cid() {
        return id_cid;
    }

    public String getNome() {
        return nome;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setid_cid(long id_cid) {
        this.id_cid = id_cid;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}