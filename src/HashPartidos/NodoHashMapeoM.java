package HashPartidos;
import ListaPartidos.Lista;
import TDAS.ClavePartido;

/**
 *
 * @author ulise
 */
public class NodoHashMapeoM {
    private ClavePartido clave;
    private Lista partidos;
    private NodoHashMapeoM enlace;
    
    public NodoHashMapeoM(ClavePartido cla, NodoHashMapeoM enla){
        clave= cla;
        //Creo la lista vacia ya que se supone que se insertaran partidos a medida que avance la copa.
        partidos = new Lista();
        enlace=enla;
    }

    public ClavePartido getClave() {
        return clave;
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
