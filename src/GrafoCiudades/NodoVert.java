package GrafoCiudades;

/**
 *
 * @author ulise
 */
public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    //Se crea primero el nodo Vertice sin arcos, es decir, sin nodos adyacentes
    public NodoVert(Object elemen,NodoVert sig){
        elem=elemen;
        sigVertice=sig;
        primerAdy=null;
    }
    public Object getElem(){
        return elem;
    }
    public void setElem(Object elemen){
        elem=elemen;
    }
    
    public NodoVert getSigVertice(){
        return sigVertice;
    }
    public void setSigVertice(NodoVert sig){
        sigVertice= sig;
    }
    public NodoAdy getPrimerAdy(){
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primer){
        primerAdy=primer;
    }
    
}
