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
     //obtener dato
    
    public Partido obtenerDato(ClavePartido cl, Partido parti){
        
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.hash[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            
            if(aux.getClave().equals(cl)){
                encontrado = true;
            }else{    
                aux = aux.getEnlace();
            }
        }
        int posPartido;
        Partido buscada = null;
        
        if(encontrado){
            posPartido = aux.getPartidos().localizar(parti);
            buscada= aux.getPartidos().recuperar(posPartido);
        }

        return buscada;
    }

    public int localizar(ClavePartido cl, Partido parti) {
        boolean rta = false;
        int posPartido = -1;
        /*Primero verifiquemos si la clavePartido esta en el hash*/
        int pos = cl.hashCode();
        boolean encontrado = false;
        NodoHashMapeoM aux = hash[pos];
        while (!encontrado && aux != null) {
            if (aux.getClave().equals(cl)) {
                encontrado = true;
            } else {
                aux = aux.getEnlace();
            }
        }

        if (encontrado && aux != null) {
            posPartido = aux.getPartidos().localizar(parti);
        }
        return posPartido;
    }

    public boolean insertar(ClavePartido cl, Partido datosPartido) {
        boolean rta = false;
        /*Primero verifiquemos si la clavePartido esta en el hash*/
        int pos = cl.hashCode();
        boolean encontrado = false;
        NodoHashMapeoM aux = hash[pos];
        while (!encontrado && aux != null) {
            if (aux.getClave().equals(cl)) {
                encontrado = true;
            } else {
                aux = aux.getEnlace();
            }
        }
        //Si no se encontro esa clave, significa que no hay un arg-bra por ejemplo,asique lo debemos de crear.
        if (!encontrado) {
            //lo pongo en la primera posicion, y su enlace se engancha a la vieja cabecera.
            hash[pos] = new NodoHashMapeoM(cl, hash[pos]);
            hash[pos].getPartidos().insertar(1, datosPartido);
            //aumento en uno la cantidad de elementos que hay en el hash
            cant++;
            rta = true;
        } else {
            if (aux != null) {
                //Si ya existia tal clave,ej: un arg-bra, solo le inserto los datos de ese partido arg-bra a la lista de partidos.
                //La lista de partidos entre arg-bra, se diferenciaran por la instancia en la que se jugo ese partido.
                aux.getPartidos().insertar(1, datosPartido);
                rta = true;
            }
        }
        return rta;
    }

    public boolean eliminar(ClavePartido cl, Partido parti) {
        //Le ingresa la clave y la instancia que se debe de eliminar.
        boolean rta = false;
        int pos = cl.hashCode();
        boolean encontrado = false;
        NodoHashMapeoM aux = hash[pos];
        while (!encontrado && aux != null) {
            if (aux.getClave().equals(cl)) {
                encontrado = true;
            } else {
                aux = aux.getEnlace();
            }
        }
        if (encontrado) {
            //le paso el TDA Partido completo, entonces el localizar se encargara
            //usando el equals del TDA Partido, buscando mediante la instancia
            //si ese partido esta.
            int posPartido = aux.getPartidos().localizar(parti);
            rta = aux.getPartidos().eliminar(posPartido);
            //si no hay instancias de partidos, entonces eliminamos la clavePartido
            if (aux.getPartidos().esVacia()) {
                eliminarClavePartido(cl);
            }

        }
        return rta;
    }

    private void eliminarClavePartido(ClavePartido cl) {
        int pos = cl.hashCode();
        boolean encontrado = false;
        NodoHashMapeoM aux = hash[pos];
        NodoHashMapeoM anterior = null;
        if (aux != null) {
            while (!encontrado && aux != null) {
                if (aux.getClave().equals(cl)) {
                    encontrado = true;
                } else {
                    anterior = aux;
                    aux = aux.getEnlace();
                }
            }

            if (anterior == null && aux.getClave().equals(cl)) {
                //Significa que esta en la posicion 1
                hash[pos] = hash[pos].getEnlace();
                cant--;
            } else if (anterior != null && aux.getClave().equals(cl)) {
                anterior.setEnlace(aux.getEnlace());
                cant--;
            }
        }
    }

    public String toString() {
        //devuelve todas las solicitudes cargadas
        String msg = "";
        int i = 0;
        NodoHashMapeoM aux;
        while (i < this.hash.length) {
            aux = this.hash[i];
            while (aux != null) {
                msg += "-" + aux.getClave().toString() + "\n";
                msg += aux.getPartidos().toString() + "\n";
                System.out.println("");
                aux = aux.getEnlace();
            }
            i++;
        }
        return msg;
    }

    public String toStringConClave(ClavePartido cl) {

        //devuelve las solicitudes con una clave especifica
        String msj = "";
        int pos = cl.hashCode();
        NodoHashMapeoM aux = this.hash[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            if (aux.getClave().equals(cl)) {
                encontrado = true;
            } else {
                aux = aux.getEnlace();
            }

        }

        if (encontrado) {
            msj = aux.getPartidos().toString();
        } else {
            msj = "ERROR.";
        }
        return msj;
    }

}
