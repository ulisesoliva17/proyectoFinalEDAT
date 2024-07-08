package Hash;

/**
 *
 * @author ulise
 */
public class NodoHash {
    private Object elem;
    private NodoHash enlace;
    
    public NodoHash(Object elemento, NodoHash enla){
        elem=elemento;
        enlace=enla;
    }
    
    public NodoHash getEnlace(){
        return enlace;
    }
    public Object getElem(){
        return elem;
    }
    public void setEnlace(NodoHash enla){
        enlace=enla;
    }
    public void setElem(Object elemento){
        elem=elemento;
    }
}
