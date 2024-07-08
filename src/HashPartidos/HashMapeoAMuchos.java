package HashPartidos;

import TDAS.ClavePartido;
import TDAS.Partido;

/**
 *
 * @author ulise
 */
public class HashMapeoAMuchos {
            //Atributos
    private NodoHashMapeoM[] hash;
    private int cant;
    private static final int tamanio = 100;

    //Metodos
    public HashMapeoAMuchos() {
        hash = new NodoHashMapeoM[tamanio];
        cant = 0;
    }

    public NodoHashMapeoM[] getHash() {
        return hash;
    }

    public int getCant() {
        return cant;
    }

    public static int getTamanio() {
        return tamanio;
    }
    public boolean insertar(ClavePartido cl, Partido datosPartido){
        boolean rta=false;
        /*Primero verifiquemos si la clavePartido esta en el hash*/
        int pos = cl.hashCode();
        boolean encontrado=false;
        NodoHashMapeoM aux= hash[pos];
        while(!encontrado && aux!=null){
            if(aux.getClave().equals(cl)){
                encontrado=true;
            }else{
                aux=aux.getEnlace();
            }
        }
        //Si no se encontro esa clave, significa que no hay un arg-bra por ejemplo,asique lo debemos de crear.
        if(!encontrado){
            //lo pongo en la primera posicion, y su enlace se engancha a la vieja cabecera.
            hash[pos]= new NodoHashMapeoM(cl, hash[pos]);
            hash[pos].getPartidos().insertar(1, datosPartido);
            //aumento en uno la cantidad de elementos que hay en el hash
            cant++;
            rta=true;
        }else{
            //Si ya existia tal clave,ej: un arg-bra, solo le inserto los datos de ese partido arg-bra a la lista de partidos.
            //La lista de partidos entre arg-bra, se diferenciaran por la instancia en la que se jugo ese partido.
            aux.getPartidos().insertar(1,datosPartido);
            rta=true;
        }
        return rta;
    }
       
}
