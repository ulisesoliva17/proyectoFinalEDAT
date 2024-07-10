/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolAVL;
import TDAS.Equipo;

/**
 *
 * @author ulise
 */
public class NodoAVL {
    private Comparable elem;
    private Equipo equipo;
    private int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;
    
    public NodoAVL (Comparable elemen,Equipo eq,NodoAVL izq,NodoAVL der){
        elem=elemen;
        equipo=eq;
        altura=0;
        izquierdo=izq;
        derecho=der;
    }
    public Comparable getElem(){
        return elem;
    }
    public Equipo getEquipo(){
        return equipo;
    }
    public void setEquipo(Equipo otro){
        equipo=otro;
    }
    public void setElemento(Comparable elemen){
        elem=elemen;
    }
    public int getAltura(){
        return altura;
    }
    public void setAltura(int alt){
        altura=alt;
    }
    public NodoAVL getIzquierdo(){
        return izquierdo;
    }
    
     public void setIzquierdo(NodoAVL izq){
        izquierdo= izq;
    }
     
    public NodoAVL getDerecho(){
        return derecho;
    }
   
    public void setDerecho(NodoAVL der){
        derecho= der;
    }
    
    
     public void recalcularAltura() {
         //Este metodo recalcula la altura de cada uno AVL.
         
         //Si es izquierdo es nulo la altura del hijo estara dada por el derecho mas 1.
        if (this.getIzquierdo() == null && this.getDerecho() != null) {
            this.setAltura(this.getDerecho().getAltura() + 1);
        
          //Si es derecho es nulo, la altura del hijo estara dada por el izquierdo mas 1.
        } else if (this.getIzquierdo() != null && this.getDerecho() == null) {
            this.setAltura(this.getIzquierdo().getAltura() + 1);
            
        } else {
            //Si los dos hijos son distintos de nulo, entonces la altura se determinara por el de mayor altura mas 1
            int nuevaAlt = Math.max(alturaActual(this.getIzquierdo()), alturaActual(this.getDerecho()));
            this.setAltura(nuevaAlt + 1);
        }
    }

    private int alturaActual(NodoAVL n) {
        int alt;
        if (n == null) {
            alt = -1;
        } else {
            alt = 1 + Math.max(alturaActual(n.getIzquierdo()), alturaActual(n.getDerecho()));
        }
        return alt;
    }
}
