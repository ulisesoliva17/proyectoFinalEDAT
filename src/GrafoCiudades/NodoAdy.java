package Grafo;

/**
 *
 * @author ulise
 */
public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private double etiqueta;
    
    public NodoAdy(NodoVert ver,double eti){
        vertice=ver;
        sigAdyacente=null;
        etiqueta= eti;
    }
    public NodoVert getVertice(){
        return vertice;
    }
    public void setVertice(NodoVert ver){
        vertice=ver;
    }
    public NodoAdy getSigAdyacente(){
        return sigAdyacente;
    }
    public void setSigAdyacente(NodoAdy sig){
        sigAdyacente= sig;
    }
    public double getEtiqueta(){
        return etiqueta;
    }
    public void setEtiqueta(double eti){
        etiqueta=eti;
    }
    
}
