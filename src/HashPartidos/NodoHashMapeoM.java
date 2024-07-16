package HashPartidos;
import ListaPartidos.Lista;
import TDAS.ClavePartido;

/**
 *
 * @author ulise
 */
public class NodoHashMapeoM {
    private Object elem;
    private Lista partidos;
    private NodoHashMapeoM enlace;
    
    public NodoHashMapeoM(Object cla, NodoHashMapeoM enla){
        elem= cla;
        //Creo la lista vacia ya que se supone que se insertaran partidos a medida que avance la copa.
        partidos = new Lista();
        enlace=enla;
    }

    public Object getElem() {
        return elem;
    }

    public Lista getPartidos() {
        return partidos;
    }

    public NodoHashMapeoM getEnlace() {
        return enlace;
    }
    
    public void setPartidos(Lista parti) {
        partidos = parti;
    }

    public void setEnlace(NodoHashMapeoM enla) {
        enlace = enla;
    }
    
    
    
    

}
