/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps2.entidade;

/**
 *
 * @author likin
 */
public class Time {
    private long id;
    private String nome;
    private int anoFundacao;
    private String cidade;
    private String estado;

    public Time() {}
    
    
    public Time(long id, String n, int anoF, String c, String e){
        this.id = id;
        nome = n;
        anoFundacao = anoF;
        cidade = c;
        estado = e;
    }
        public long getId() { return id; }
        public String getNome() {return nome;}
        public int getAnoFundacao() {return anoFundacao;}
        public String getCidade() {return cidade;}
        public String getEstado() {return estado;}
        
        public void setId(long id) {this.id = id;}
        public void setNome(String n) {nome = n;}
        public void setAnoFundacao(int anoF) {anoFundacao = anoF;}
        public void setCidade(String c) {cidade = c;}
        public void setEstado(String e) {estado = e;}
}
