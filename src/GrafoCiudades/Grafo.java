package Grafo;

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

    private NodoAdy ubicarAdyacenteAnterior(NodoVert aux, Object buscado) {
        //este metodo devolvera el nodoAdyacente anterior al que se desea eliminar
        //es decir, al "arco" que se busca eliminar
        NodoAdy ady = aux.getPrimerAdy();
        NodoAdy anterior = null;
        boolean encontrado = false;
        //verificar caso que sea el primero
        while (!encontrado && ady != null) {
            if (ady.getVertice().getElem().equals(buscado)) {
                encontrado = true;
            } else {
                anterior = ady;
                ady = ady.getSigAdyacente();
            }
        }
        return anterior;
    }

    public boolean eliminarArco(Object ori, Object des) {
        //Nota: REVISAR EL CASO DONDE ES EL PRIMER NODO ADYACENTE
        boolean rta = false;
        NodoVert origen = ubicarVertice(ori);
        //Pregunto si encontro el nodo(da igual cual, si tengo de un nodo luego puedo ir al otro
        if (origen != null) {
            NodoAdy anterior = ubicarAdyacenteAnterior(origen, des);
            if (anterior == null) {
                //tengo dos opciones si el anterior es null, o bien, no hay arco o es el primero.
                NodoAdy aBorrar = origen.getPrimerAdy();

                //Si se cumple, significa que era el primero en la lista
                if (aBorrar != null && aBorrar.getVertice().getElem().equals(des)) {

                    //Obtengo un auxiliar para tener el nodoVertice al cual tambien tengo que borrarle el enlace
                    NodoVert destino = aBorrar.getVertice();

                    //Borro el arco que va de origen a destino
                    origen.setPrimerAdy(aBorrar.getSigAdyacente());

                    //obtengo el nodoAdyacente anterior al que va de destino a origen
                    NodoAdy anteriorDestino = ubicarAdyacenteAnterior(destino, ori);

                    /*Sucede lo mismo, solo que no hace falta que pregunte si esta el arco
                    ya que se que tiene que estar, solo me encargo de preguntar si es el primero
                    en la lista o no, si el anteriorDestino es null, significa que el que tengo que borrar
                    esta en la cabecera*/
                    if (anteriorDestino == null) {
                        destino.setPrimerAdy(destino.getPrimerAdy().getSigAdyacente());
                    } else {
                        /*Si no esta en la cabecera, borro el que le sigue al anterior, que es el que tengo 
                        que eliminar de la lista de arcos*/
                        anteriorDestino.setSigAdyacente(anteriorDestino.getSigAdyacente().getSigAdyacente());
                    }
                    rta = true;
                }
            } else {
                /*Si entra en este else, signigica que no es el primer elemento, pero puede ser que
                NO exista tal arco, debemos de verificar eso*/
                NodoAdy aBorrar = anterior.getSigAdyacente();
                if (aBorrar != null && aBorrar.getVertice().getElem().equals(des)) {
                    //Si entro en este if, significa que encontro el anterior y que si existe arco de origen a destino

                    //Elimino de la lista de adyacentes al arco aBorrar
                    anterior.setSigAdyacente(aBorrar.getSigAdyacente());
                    //Obtengo un auxiliar para tener el nodoVertice al cual tambien tengo que borrarle el enlace
                    NodoVert destino = aBorrar.getVertice();

                    // Borrar el nodo adyacente en el destino
                    NodoAdy anteriorDestino = ubicarAdyacenteAnterior(destino, ori);
                    if (anteriorDestino == null) {
                        //Aca no hace falta preguntar si es el que estoy buscando o no, ya que 
                        //tiene que estar si o si por que el origen si lo encontro como arco.
                        destino.setPrimerAdy(aBorrar.getSigAdyacente());
                    } else {
                        anteriorDestino.setSigAdyacente(anteriorDestino.getSigAdyacente().getSigAdyacente());
                    }
                    rta = true;
                }
            }
        }
        return rta;
    }

    public boolean existeArco(Object ori, Object des) {
        boolean rta = false;
        NodoVert origen = ubicarVertice(ori);

        if (origen != null) {
            NodoAdy adyacente = origen.getPrimerAdy();

            // Verificar si el primer adyacente es el que buscamos
            if (adyacente != null && adyacente.getVertice().getElem().equals(des)) {
                rta = true;
            } else {
                // Buscar el anterior al adyacente deseado
                NodoAdy anterior = ubicarAdyacenteAnterior(origen, des);

                // Si el anterior no es null, entonces hay un adyacente que cumple
                if (anterior != null && anterior.getSigAdyacente() != null
                        && anterior.getSigAdyacente().getVertice().getElem().equals(des)) {
                    rta = true;
                }
            }
        }

        return rta;
    }

    public boolean eliminarVertice(Object ori) {
        boolean esCabecera = false, encontrado = false;
        boolean rta = false;
        //Primer parte del Metodo eliminarVertice: Ubico el Nodo
        /*No puedo usar el ubicarVertice, ya que tmb debo de eliminar el nodo de la lista
        de nodosVertices, por ende, tengo que hacer aca adentro*/
        NodoVert aux = inicio;
        NodoVert anterior = null;
        if (aux.getElem().equals(ori)) {
            esCabecera = true;
            encontrado = true;
        } else {
            anterior = aux;
            aux = aux.getSigVertice();
            while (!encontrado && aux != null) {
                if (aux.getElem().equals(ori)) {
                    encontrado = true;
                } else {
                    anterior = aux;
                    aux = aux.getSigVertice();
                }
            }
        }
        //Segunda parte: Elimino todos los arcos del nodo, tanto de ida como de vuelta
        if (encontrado && aux != null) {
            NodoAdy adyVerticeOri = aux.getPrimerAdy();
            while (adyVerticeOri != null) {
                //Obtengo el nodoVertice que esta referenciado por el arco adyancente de Origen
                NodoVert destino = adyVerticeOri.getVertice();
                //Actualizo al siguiente NodoAdyacente de Origen
                adyVerticeOri = adyVerticeOri.getSigAdyacente();
                //Elimino el nodoAdyacente inmediato del nodo que quiero eliminar
                aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
                
                //Ubico el nodoAdyacente anterior del nodoVertice destino del que quiero eliminar
                NodoAdy anteriorDes = ubicarAdyacenteAnterior(destino, ori);
                //Suponemos que el que se debe borrar es el primero en la lista
                NodoAdy aBorrar = destino.getPrimerAdy();
                if (anteriorDes == null) {
                    /*Si es nulo, significa que esta en la primer posicion en que se quiere borrar
                    o bien, no existe. Debe de existe si o si, pero hago la consulta por las dudas
                    que se haya de insertado mal el arco o cualquier otro problema*/
                    if (aBorrar != null && aBorrar.getVertice().getElem().equals(ori)) {
                        //Si existe tal arco, entonces lo elimino, y sabemos que esta en la primer posicion
                        destino.setPrimerAdy(aBorrar.getSigAdyacente());
                    }
                } else {
                    /*Si entra a este else, no esta en la primer posicion el arco que deseo borrar
                    pero nada me garantiza que este, aunque deberia, puede ser que se haya insertado
                    mal o cualquier otro error*/
                    aBorrar = anteriorDes.getSigAdyacente();
                    if (aBorrar != null && aBorrar.getVertice().getElem().equals(ori)) {
                        //Si lo encontramos, por ende lo eliminamos de la lista de hermanos adyacentes
                        anteriorDes.setSigAdyacente(aBorrar.getSigAdyacente());
                    }
                }
            }
            //Tercer parte: Elimino de la lista de nodos Vertices, el nodo que se desea eliminar
            if(esCabecera){
                inicio= inicio.getSigVertice();
            }else{
                anterior.setSigVertice(aux.getSigVertice());
            }
            rta=true;
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
                msjAdyacentes += "-Destino: " + " | " + conex.getVertice().getElem().toString() + " | " + ";" + " -Distancia: " + conex.getEtiqueta();
                conex = conex.getSigAdyacente();
            }
            msj = msj + msjAdyacentes + '\n';
            msj += '\n' + toStringAux(nodo.getSigVertice());
        }
        return msj;
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
