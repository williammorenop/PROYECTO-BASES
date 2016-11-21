/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author johanmurillo
 */
public class mainmenuretorno {
    
    
            private String nombre;
            private int debito;

    public mainmenuretorno(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDebito() {
        return debito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDebito(int debito) {
        this.debito = debito;
    }
            
}
