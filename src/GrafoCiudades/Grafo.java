package GrafoCiudades;

import TDAS.Ciudad;
import Lineales.Lista;
import java.util.HashMap;
/**
 *
 * @author ulise
 */
public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        //Si aux es null, significa que no existe ese vertice.
        if (aux == null) {
            //Se inserta siempre en el inicio, y setea el enlace del nuevo inicio con el inicio anterior
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean existeVertice(Object buscado) {
        return ubicarVertice(buscado) != null;
    }

    public boolean insertarArco(Object ori, Object des, double eti) {
        // actualizacion testeo: Anda perfecto el insertar Arco
        NodoVert origen = inicio;
        NodoVert destino = inicio;
        //Podria usar el ubicarVertice, pero quiero hacerlo en un solo while
        boolean encontrado1 = false, encontrado2 = false, rta = false;
        //Lo hago con un || porque si fuese un && corta cuando encuentra 1 de los dos
        //y la idea del while es que encuentre a los dos de un solo recorrido.
        while ((!encontrado1 || !encontrado2) && (origen != null || destino != null)) {
            if (origen != null && !encontrado1) {
                if (origen.getElem().equals(ori)) {
                    encontrado1 = true;
                } else {
                    origen = origen.getSigVertice();
                }
            }
            if (destino != null && !encontrado2) {
                if (destino.getElem().equals(des)) {
                    encontrado2 = true;
                } else {
                    destino = destino.getSigVertice();
                }
            }
        }

        if (encontrado1 && encontrado2 && origen != null && destino != null) {
            //adyOrigen sera el adyacente de origen, por ende, tiene que tener la referencia al destino
            NodoAdy adyOrigen = new NodoAdy(destino, eti);
            //Seteamos el primer "hermano" derecho del adyOrigen como el primer nodo adyacente que tiene el nodo origen 
            adyOrigen.setSigAdyacente(origen.getPrimerAdy());
            //seteamos el primer nodo adyacente de origen por el adyOrigen, ya que se coloco como cabecera
            origen.setPrimerAdy(adyOrigen);

            //adyDestino sera el adyacente de destino, por ende, tiene que tener la referencia al origen
            NodoAdy adyDestino = new NodoAdy(origen, eti);
            //Seteamos el primer "hermano" derecho del adyDestino como el primer nodo adyacente que tiene el nodo destino 
            adyDestino.setSigAdyacente(destino.getPrimerAdy());
            //seteamos el primer nodo adyacente de destino por el adyDestino, ya que se coloco como cabecera
            destino.setPrimerAdy(adyDestino);
            rta = true;
        }
        return rta;
    }

//    private NodoAdy ubicarAdyacenteAnterior(NodoVert aux, Object buscado) {
//        //este metodo devolvera el nodoAdyacente anterior al que se desea eliminar
//        //es decir, al "arco" que se busca eliminar
//        NodoAdy ady = aux.getPrimerAdy();
//        NodoAdy anterior = null;
//        boolean encontrado = false;
//        //verificar caso que sea el primero
//        while (!encontrado && ady != null) {
//            if (ady.getVertice().getElem().equals(buscado)) {
//                encontrado = true;
//            } else {
//                anterior = ady;
//                ady = ady.getSigAdyacente();
//            }
//        }
//        return anterior;
//    }
    private boolean eliminarAdyacente(NodoVert aux, Object buscado) {
        NodoAdy ady = aux.getPrimerAdy();
        NodoAdy anterior = null;
        boolean borrado = false;
        //verificar caso que sea el primero
        if (ady.getVertice().getElem().equals(buscado)) {
            aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
            borrado = true;
        } else {
            anterior = ady;
            ady = ady.getSigAdyacente();
            while (!borrado && ady != null) {
                if (ady.getVertice().getElem().equals(buscado)) {
                    anterior.setSigAdyacente(ady.getSigAdyacente());
                    borrado = true;
                } else {
                    anterior = ady;
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return borrado;
    }
    public boolean eliminarArco(Object ori, Object des){
        boolean rta=false;
        
        NodoVert origen = this.ubicarVertice(ori);
        NodoVert destino = this.ubicarVertice(des);
        boolean rta2= this.eliminarAdyacente(origen, des);
        boolean rta3= this.eliminarAdyacente(destino, ori);
        if(rta2 && rta3){
            rta=true;
        }
        return rta;
    }
    public boolean modificarArco(Object ori, Object des,double distancia){
        boolean rta=false;
        
        NodoVert origen = this.ubicarVertice(ori);
        NodoVert destino = this.ubicarVertice(des);
        rta=modificarArcoAux(origen,destino,distancia);
        return rta;
    }
     private boolean modificarArcoAux(NodoVert origen,NodoVert destino, double distancia) {
         boolean rta=false;
         boolean encontrado1=false;
         boolean encontrado2=false;
         NodoAdy adyOri = origen.getPrimerAdy();
         NodoAdy adyDes = destino.getPrimerAdy();
         while ((!encontrado1 || !encontrado2) && (adyOri != null || adyDes != null)) {
            if (adyOri != null && !encontrado1) {
                if (adyOri.getVertice().equals(destino)) {
                    encontrado1 = true;
                    adyOri.setEtiqueta(distancia);
                } else {
                    adyOri = adyOri.getSigAdyacente();
                }
            }
            if (adyDes != null && !encontrado2) {
                if (adyDes.getVertice().equals(origen)) {
                    encontrado2 = true;
                    adyDes.setEtiqueta(distancia);
                } else {
                    adyDes = adyDes.getSigAdyacente();
                }
            }
        }
         if(encontrado1 && encontrado2){
             rta=true;
         }
         return rta;
    }
//    public boolean eliminarArco(Object ori, Object des) {
//        boolean rta = false;
//        NodoVert origen = ubicarVertice(ori);
//        //Pregunto si encontro el nodo(da igual cual, si tengo de un nodo luego puedo ir al otro
//        if (origen != null) {
//            NodoAdy anterior = ubicarAdyacenteAnterior(origen, des);
//            if (anterior == null) {
//                //tengo dos opciones si el anterior es null, o bien, no hay arco o es el primero.
//                NodoAdy aBorrar = origen.getPrimerAdy();
//
//                //Si se cumple, significa que era el primero en la lista
//                if (aBorrar != null && aBorrar.getVertice().getElem().equals(des)) {
//
//                    //Obtengo un auxiliar para tener el nodoVertice al cual tambien tengo que borrarle el enlace
//                    NodoVert destino = aBorrar.getVertice();
//
//                    //Borro el arco que va de origen a destino
//                    origen.setPrimerAdy(aBorrar.getSigAdyacente());
//
//                    //obtengo el nodoAdyacente anterior al que va de destino a origen
//                    NodoAdy anteriorDestino = ubicarAdyacenteAnterior(destino, ori);
//
//                    /*Sucede lo mismo, solo que no hace falta que pregunte si esta el arco
//                    ya que se que tiene que estar, solo me encargo de preguntar si es el primero
//                    en la lista o no, si el anteriorDestino es null, significa que el que tengo que borrar
//                    esta en la cabecera*/
//                    if (anteriorDestino == null) {
//                        destino.setPrimerAdy(destino.getPrimerAdy().getSigAdyacente());
//                    } else {
//                        /*Si no esta en la cabecera, borro el que le sigue al anterior, que es el que tengo 
//                        que eliminar de la lista de arcos*/
//                        anteriorDestino.setSigAdyacente(anteriorDestino.getSigAdyacente().getSigAdyacente());
//                    }
//                    rta = true;
//                }
//            } else {
//                /*Si entra en este else, signigica que no es el primer elemento, pero puede ser que
//                NO exista tal arco, debemos de verificar eso*/
//                NodoAdy aBorrar = anterior.getSigAdyacente();
//                if (aBorrar != null && aBorrar.getVertice().getElem().equals(des)) {
//                    //Si entro en este if, significa que encontro el anterior y que si existe arco de origen a destino
//
//                    //Elimino de la lista de adyacentes al arco aBorrar
//                    anterior.setSigAdyacente(aBorrar.getSigAdyacente());
//                    //Obtengo un auxiliar para tener el nodoVertice al cual tambien tengo que borrarle el enlace
//                    NodoVert destino = aBorrar.getVertice();
//
//                    // Borrar el nodo adyacente en el destino
//                    NodoAdy anteriorDestino = ubicarAdyacenteAnterior(destino, ori);
//                    if (anteriorDestino == null) {
//                        //Aca no hace falta preguntar si es el que estoy buscando o no, ya que 
//                        //tiene que estar si o si por que el origen si lo encontro como arco.
//                        destino.setPrimerAdy(aBorrar.getSigAdyacente());
//                    } else {
//                        anteriorDestino.setSigAdyacente(anteriorDestino.getSigAdyacente().getSigAdyacente());
//                    }
//                    rta = true;
//                }
//            }
//        }
//        return rta;
//    }
      private NodoAdy ubicarAdyacente(NodoVert aux, Object buscado) {
        //este metodo devolvera el nodoAdyacente anterior al que se desea eliminar
        //es decir, al "arco" que se busca eliminar
        NodoAdy ady = aux.getPrimerAdy();
        boolean encontrado = false;
        //verificar caso que sea el primero
        while (!encontrado && ady != null) {
            if (ady.getVertice().getElem().equals(buscado)) {
                encontrado = true;
            } else {
                ady = ady.getSigAdyacente();
            }
        }
        return ady;
    }
    public boolean existeArco(Object ori, Object des) {
        NodoVert origen = ubicarVertice(ori);
        NodoAdy adyacente=null;

        if (origen != null) {
             adyacente=this.ubicarAdyacente(origen, des);
        }

        return adyacente!=null;
    }
    
    public Ciudad obtenerDato(Ciudad otraCiudad){
        boolean rta = false;
        NodoVert aux = inicio;
        Ciudad retorno=null;
        if (aux.getElem().equals(otraCiudad)) {
            rta=true;
            retorno=(Ciudad) aux.getElem();
        } else {
            aux = aux.getSigVertice();
            while (!rta && aux != null) {
                if (aux.getElem().equals(otraCiudad)) {
                    rta=true;
                    retorno=(Ciudad)aux.getElem();
                } else {
                    aux = aux.getSigVertice();
                }
            }
        }
        return retorno;
    }

//    public boolean eliminarVertice(Object ori) {
//        boolean esCabecera = false, encontrado = false;
//        boolean rta = false;
//        //Primer parte del Metodo eliminarVertice: Ubico el Nodo
//        /*No puedo usar el ubicarVertice, ya que tmb debo de eliminar el nodo de la lista
//        de nodosVertices, por ende, tengo que hacer aca adentro*/
//        NodoVert aux = inicio;
//        NodoVert anterior = null;
//        if (aux.getElem().equals(ori)) {
//            esCabecera = true;
//            encontrado = true;
//        } else {
//            anterior = aux;
//            aux = aux.getSigVertice();
//            while (!encontrado && aux != null) {
//                if (aux.getElem().equals(ori)) {
//                    encontrado = true;
//                } else {
//                    anterior = aux;
//                    aux = aux.getSigVertice();
//                }
//            }
//        }
//        //Segunda parte: Elimino todos los arcos del nodo, tanto de ida como de vuelta
//        if (encontrado && aux != null) {
//            NodoAdy adyVerticeOri = aux.getPrimerAdy();
//            while (adyVerticeOri != null) {
//                //Obtengo el nodoVertice que esta referenciado por el arco adyancente de Origen
//                NodoVert destino = adyVerticeOri.getVertice();
//                //Actualizo al siguiente NodoAdyacente de Origen
//                adyVerticeOri = adyVerticeOri.getSigAdyacente();
//                //Elimino el nodoAdyacente inmediato del nodo que quiero eliminar
//                aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
//                
//                //Ubico el nodoAdyacente anterior del nodoVertice destino del que quiero eliminar
//                NodoAdy anteriorDes = ubicarAdyacenteAnterior(destino, ori);
//                //Suponemos que el que se debe borrar es el primero en la lista
//                NodoAdy aBorrar = destino.getPrimerAdy();
//                if (anteriorDes == null) {
//                    /*Si es nulo, significa que esta en la primer posicion en que se quiere borrar
//                    o bien, no existe. Debe de existe si o si, pero hago la consulta por las dudas
//                    que se haya de insertado mal el arco o cualquier otro problema*/
//                    if (aBorrar != null && aBorrar.getVertice().getElem().equals(ori)) {
//                        //Si existe tal arco, entonces lo elimino, y sabemos que esta en la primer posicion
//                        destino.setPrimerAdy(aBorrar.getSigAdyacente());
//                    }
//                } else {
//                    /*Si entra a este else, no esta en la primer posicion el arco que deseo borrar
//                    pero nada me garantiza que este, aunque deberia, puede ser que se haya insertado
//                    mal o cualquier otro error*/
//                    aBorrar = anteriorDes.getSigAdyacente();
//                    if (aBorrar != null && aBorrar.getVertice().getElem().equals(ori)) {
//                        //Si lo encontramos, por ende lo eliminamos de la lista de hermanos adyacentes
//                        anteriorDes.setSigAdyacente(aBorrar.getSigAdyacente());
//                    }
//                }
//            }
//            //Tercer parte: Elimino de la lista de nodos Vertices, el nodo que se desea eliminar
//            if(esCabecera){
//                inicio= inicio.getSigVertice();
//            }else{
//                anterior.setSigVertice(aux.getSigVertice());
//            }
//            rta=true;
//        }
//
//        return rta;
//    }
    public boolean eliminarVertice(Object ori) {
        boolean encontrado = false;
        boolean rta = false;
        //Primer parte del Metodo eliminarVertice: Elimino el Nodo
        /*No puedo usar el ubicarVertice, ya que tmb debo de eliminar el nodo de la lista
        de nodosVertices, por ende, tengo que hacerlo aca adentro*/
        NodoVert aux = inicio;
        NodoVert anterior = null;
        if (aux.getElem().equals(ori)) {
            encontrado = true;
            inicio = inicio.getSigVertice();
        } else {
            anterior = aux;
            aux = aux.getSigVertice();
            while (!encontrado && aux != null) {
                if (aux.getElem().equals(ori)) {
                    encontrado = true;
                    anterior.setSigVertice(aux.getSigVertice());
                } else {
                    anterior = aux;
                    aux = aux.getSigVertice();
                }
            }
        }
        //Como ya se elimino el nodo, debo de eliminar los arcos que le llegaban a el, asique debo de recorrer su lista de nodos de adyacencia
        if (encontrado && aux != null) {
            NodoAdy adyVerticeOri = aux.getPrimerAdy();
            while (adyVerticeOri != null) {
                //Obtengo el nodoVertice que esta referenciado por el arco adyancente de Origen
                NodoVert destino = adyVerticeOri.getVertice();
                
                eliminarAdyacente(destino, ori);
                
                //Actualizo al siguiente NodoAdyacente de Origen
                adyVerticeOri = adyVerticeOri.getSigAdyacente();
            }
            if(adyVerticeOri==null && encontrado){
                rta=true;
            }
        }

        return rta;
    }

    @Override
    public String toString() {
        String msj = "El grafo se encuentra vacio";
        if (inicio != null) {
            msj = toStringAux(inicio);
        }
        return msj;
    }

    private String toStringAux(NodoVert nodo) {
        String msj = "";
        if (nodo != null) {
            msj = "Origen de la ruta: " + nodo.getElem().toString() + '\n';
            NodoAdy conex = nodo.getPrimerAdy();
            String msjAdyacentes = "";
            while (conex != null) {
                msjAdyacentes += "-Destino: " + " | " + conex.getVertice().getElem().toString() + " | " + ";" + " -Distancia: " + conex.getEtiqueta()+" \n\n";
                conex = conex.getSigAdyacente();
            }
            msj = msj + msjAdyacentes + '\n';
            msj += '\n' + toStringAux(nodo.getSigVertice());
        }
        return msj;
    }
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }
        if (auxO != null && auxD != null) {
        // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(vis.longitud() + 1,n.getElem());
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    
//    public Lista caminoMasCorto(Object origen, Object destino) {
//        
//        Lista masCorto = new Lista();
//        Lista visitados = new Lista();    
//        NodoVert nodoOrigen = ubicarVertice(origen);
//        if (nodoOrigen != null) {
//            visitados.insertar(1,origen);
//            masCorto = caminoMasCortoAux(nodoOrigen, destino, visitados, masCorto);
//
//        }
//        return masCorto;
//        
//    }
//
//    private Lista caminoMasCortoAux(NodoVert n, Object destino, Lista visitados, Lista masCorto) {
//
//        if (n != null) {
//            
//            //Si el nodo actual es el destino, se guarda una lista con los visitados que son un camino de llegada
//            if (n.getElem().equals(destino)) {
//                
//                masCorto = visitados.clone();
//                
//            } else {
//                    
//                NodoAdy ady = n.getPrimerAdy();
//                while (ady != null) {
//
//                    //si el adyacente aun no fue visitado entonces entra
//                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
//                        
//                        if (!masCorto.esVacia()) {
//                        //si ya encontro un camino(masCorto no es vacia), entonces 
//                        //verifica que los nodos visitados no sean mas que los del camino encontrado
//                                                    
//                            if (visitados.longitud() < masCorto.longitud()) {
//                                visitados.insertar(visitados.longitud()+1,ady.getVertice().getElem());
//                                masCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, masCorto);
//                                //elimina al ultimo visitado para comparar con otros caminos a la vuelta
//                                visitados.eliminar(visitados.longitud());
//                            }
//
//                        } else { //Si la lista es vacia, es la primera iteracion, busca el primer camino a destino
//                            
//                            //inserta el nodo adyacente visitado y llama recursivamente con el mismo
//                            visitados.insertar(visitados.longitud()+1,ady.getVertice().getElem());
//                            masCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, masCorto);
//                            //elimina al ultimo visitado para comparar con otros caminos a la vuelta
//                            visitados.eliminar(visitados.longitud());
//                            
//                        }
//                    }
//
//                    ady = ady.getSigAdyacente();
//                }
//                
//            }
//        }
//        return masCorto;
//    }
    public Lista caminoMasCorto(Object origen, Object destino) {
    Lista masCorto = new Lista();
    Lista visitados = new Lista();   
    NodoVert nodoOrigen = ubicarVertice(origen);
    if (nodoOrigen != null) {
        visitados.insertar(1, origen);
        masCorto = caminoMasCortoAux(nodoOrigen, destino, visitados, masCorto);
    }
    return masCorto;
}

private Lista caminoMasCortoAux(NodoVert n, Object destino, Lista visitados, Lista masCorto) {
    if (n != null) {
        // Si el nodo actual es el destino, se guarda una lista con los visitados que son un camino de llegada
        if (n.getElem().equals(destino)) {
            masCorto = visitados.clone();
        } else {
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                // Si el adyacente aún no fue visitado entonces entra
                if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                    // Inserta el nodo adyacente visitado
                    visitados.insertar(visitados.longitud() + 1, ady.getVertice().getElem());
                    
                    // Si masCorto es vacía, busca el primer camino a destino
                    // O si la longitud de visitados es menor que la longitud de masCorto
                    if (masCorto.esVacia() || visitados.longitud() < masCorto.longitud()) {
                        masCorto = caminoMasCortoAux(ady.getVertice(), destino, visitados, masCorto);
                    }
                    
                    // Elimina al último visitado para comparar con otros caminos a la vuelta
                    visitados.eliminar(visitados.longitud());
                }
                ady = ady.getSigAdyacente();
            }
        }
    }
    return masCorto;
}

     public HashMap caminoMasRapido(Object origen, Object destino) {
        HashMap aux = new HashMap();
        double km = 0;
        Lista visitados = new Lista();
        Lista rutaMasRapida = new Lista(); 
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        
        if (nodoOrigen != null && nodoDestino != null) {
            //Inicializa la distancia en aux con Double.MAX_VALUE para que cualquier camino encontrado sea más corto inicialmente.
            aux.put("distancia", Double.MAX_VALUE); 
            rutaMasRapida = CaminoMasRapidoAux(nodoOrigen, destino, visitados, rutaMasRapida, aux, km);
            aux.put("caminoMasRapido", rutaMasRapida);

        }
        return aux;
    }

    private Lista CaminoMasRapidoAux(NodoVert n, Object destino, Lista visitados, Lista rutaMasRapida, HashMap aux, double km) {
        
        if (n != null) {
            
            visitados.insertar(visitados.longitud()+1,n.getElem());
            
            if (n.getElem().equals(destino)) {
                //si llego a destino y los km son menores a los de        
                //la ultima ruta guardada, actualiza la ruta
                if (km < (double) aux.get("distancia")) {
                    rutaMasRapida = visitados.clone();
                    aux.put("distancia", km);
                }
                
            } else {
                
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    //suma los km de la ruta
                    km += ady.getEtiqueta();
                    //si no visito el vertice y los km son menores a los recorridos
                    if (visitados.localizar(ady.getVertice().getElem()) < 0 && km < (double) aux.get("distancia")) {
                        rutaMasRapida = CaminoMasRapidoAux(ady.getVertice(), destino, visitados, rutaMasRapida, aux, km);
                    }
                    //resta los km a la vuelta de la recursíon, 
                    //con esto suma los km de los caminos que aun no recorre a los que ya recorrio
                    km -= ady.getEtiqueta();
                    ady = ady.getSigAdyacente();
                    
                }
            }
            //elimina el ultimo visitado para comparar con otros caminos a la vuelta
            visitados.eliminar(visitados.longitud());
        }

        return rutaMasRapida;
    }
//    public boolean insertarVertice(Object nuevoVertice) {
//        NodoVert aux = inicio;
//        NodoVert anterior = null;
//        boolean encontrado = false;
//        boolean rta = false;
//        if (inicio == null) {
//            inicio = new NodoVert(nuevoVertice, null);
//            rta = true;
//        } else {
//            //Primero veamos si ese Vertice ya existe
//            while (!encontrado && aux != null) {
//                if (aux.getElem().equals(nuevoVertice)) {
//                    encontrado = true;
//                } else {
//                    anterior = aux;
//                    aux = aux.getSigVertice();
//                }
//            }
//            //Si no existe
//            if (!encontrado && aux == null) {
//                NodoVert nuevo = new NodoVert(nuevoVertice, null);
//                anterior.setSigVertice(nuevo);
//                rta = true;
//            }
//
//        }
//        return rta;
//    }
}
