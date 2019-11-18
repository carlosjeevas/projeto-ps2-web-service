/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps2.entidade;

import java.util.List;

/**
 *
 * @author likin
 */
public class Campeonato {
    private long id;
    private String nome;
    
    public Campeonato () {}
    
    public Campeonato (long id, String n) {
        this.id = id;
        nome = n;
    }
    
    public long getId() { return id; }
    public String getNome() {return nome;}
    
    public void setId(long id) {this.id = id;}
    public void setNome(String n) {nome = n;}
    

    }

