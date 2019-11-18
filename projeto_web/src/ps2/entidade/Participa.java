/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps2.entidade;

/**
 *
 * @author 793508
 */
public class Participa {
    private long idtime;
    private long idcampeonato;
 
    public Participa() {}
   
    public Participa (long idt, long idc) {
        idtime = idt;
        idcampeonato = idc;
    }
    
    public long getIdTime () {return idtime;}
    public long getIdCampeonato () {return idcampeonato;}
    
    public void setIdTime (long idt) {idtime = idt;}
    public void setIdCampeonato (long idc) {idcampeonato = idc;}
}