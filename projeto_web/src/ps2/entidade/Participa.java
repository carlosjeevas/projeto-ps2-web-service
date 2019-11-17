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
   
    public Participa (long it, long ic) {
        idtime = it;
        idcampeonato = ic;
    }
    
    public long getIdTime () {return idtime;}
    public long getIdCampeonato () {return idcampeonato;}
    
    public void setIdTime (long it) {idtime = it;}
    public void setIdCampeonato (long ic) {idcampeonato = ic;}
}