package Hash;

/**
 *
 * @author ulise
 */
public class TablaHash {

    //Atributos
    private NodoHash[] hash;
    private int cant;
    private static final int tamanio = 10;

    //Metodos
    public TablaHash() {
        hash = new NodoHash[tamanio];
        cant = 0;
    }

   /* public boolean insertar(Object elemNuevo) {
        //Le hago que ingreso un Object para que funcione con cualquier objeto
        //Pero en la implementacion, le entrara el Comparable, que se toma como Object
        boolean rta = false;
        //Importante: hashCode() se redefine en la clase del objeto que se guardara
        int pos = elemNuevo.hashCode() % tamanio;
        NodoHash aux = arreglo[pos];
        NodoHash anterior = null;
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            if (aux.getElem().equals(elemNuevo)) {
                encontrado = true;
            } else {
                anterior = aux;
                aux = aux.getEnlace();
            }
        }
        if (!encontrado) {
            NodoHash nuevo = new NodoHash(elemNuevo, null);
            anterior.setEnlace(nuevo);
            rta = true;
            cant++;
        } else {
            rta = false;
        }
        return rta;
    }*/
    public boolean insertar(Object nuevoElem){
        //Primero verifica si el elemento ya esta cargado
        //Si no lo encuentra, lo pone adelante del resto.
        int pos= nuevoElem.hashCode() % tamanio;
        NodoHash aux= hash[pos];
        boolean encontrado=false;
        while(!encontrado && aux!=null){
            encontrado= aux.getElem().equals(nuevoElem);
            aux=aux.getEnlace();
        }
        if(!encontrado){
            //Inserta siempre en la primer posicion, y setea el nuevo elemento con la vieja cabecera.
            hash[pos]= new NodoHash(nuevoElem,hash[pos]);
            cant++;
        }
        return !encontrado;
    }

    public boolean eliminar(Object elem) {
        boolean rta = false;
        //Importante: hashCode() se redefine en la clase del objeto que se guardara
        int pos = elem.hashCode() % tamanio;
        NodoHash aux = hash[pos];
        NodoHash anterior = null;
        boolean encontrado = false;
        if (aux.getElem().equals(elem)) {
            hash[pos] = aux.getEnlace();
            rta = true;
        } else {
            if (aux.getEnlace() != null) {
                //Por si entra en el new pointer exepcion
                anterior = aux;
                aux = aux.getEnlace();

                while (!encontrado && aux != null) {
                    if (aux.getElem().equals(elem)) {
                        encontrado = true;
                    } else {
                        anterior = aux;
                        aux = aux.getEnlace();
                    }
                }
                if (encontrado) {
                    anterior.setEnlace(aux.getEnlace());
                    rta = true;
                }
            }
        }
        return rta;
    }
    
    public boolean pertenece(Object elem){
        int pos = elem.hashCode() % tamanio;
        NodoHash aux = hash[pos];
        boolean rta = false;
        while(!rta && aux!=null){
            if(aux.getElem().equals(elem)){
                rta=true;
            }else{
                aux=aux.getEnlace();
            }
        }
        return rta;
    }
}
