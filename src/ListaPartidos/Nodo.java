package ListaPartidos;
import TDAS.Partido;

/**
 *
 * @author ulise
 */
public class Nodo {
    private Object elem;
    private Nodo enlace;
    
    public Nodo(Object elemen, Nodo enla){
        elem= elemen;
        enlace=enla;
    }
    public void setElem(Partido elemen){
        elem=elemen;
    }
    public void setEnlace(Nodo enla){
        enlace=enla;
    }
    public Object getElem(){
        return elem;
    }
    public Nodo getEnlace(){
        return enlace;
    }
}
