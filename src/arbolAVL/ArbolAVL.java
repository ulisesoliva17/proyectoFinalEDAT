package arbolAVL;
import Lineales.Lista;

/**
 *
 * @author ulise
 */
public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        raiz = null;
    }
    //------------------------------------------------------------------------------------------
    public void vaciar(){
        raiz=null;
    }
    private NodoAVL obtenerNodoAux(NodoAVL aux, Comparable buscado) {
        NodoAVL retorno = null;
        if (aux != null) {

            if (aux.getElem().compareTo(buscado) == 0) {
                retorno = aux;
            } else {
                retorno = obtenerNodoAux(aux.getIzquierdo(), buscado);
                if (retorno == null) {
                    retorno = obtenerNodoAux(aux.getDerecho(), buscado);
                }
            }
        }
        return retorno;
    }

    public Comparable obtenerNodo(Comparable buscado) {
        NodoAVL nodo = obtenerNodoAux(raiz, buscado);
        Comparable elemento;
        if (nodo.getElem() != null) {
            elemento = nodo.getElem();
        } else {
            elemento = null;
        }
        return elemento;
    }
//------------------------------------------------------------------------------------------

    public boolean pertenece(Comparable elem) {
        return perteneceAux(raiz, elem);
    }

    private boolean perteneceAux(NodoAVL aux, Comparable elem) {
        boolean rta = false;
        if (aux != null) {
            if ((elem.compareTo(aux.getElem())) == 0) {
                rta = true;
            } else if ((elem.compareTo(aux.getElem())) < 0) {
                if (aux.getIzquierdo() != null) {
                    rta = perteneceAux(aux.getIzquierdo(), elem);
                } else {
                    rta = false;
                }
            } else {
                if (aux.getDerecho() != null) {
                    rta = perteneceAux(aux.getDerecho(), elem);
                } else {
                    rta = false;
                }
            }
        }
        return rta;
    }

    //------------------------------------------------------------------------------------------
  public int obtenerAltura(Comparable elem){
        int alt = -1;
        if(this.raiz!=null){
            alt = alturaAux(this.raiz, elem);
        }
        return alt;
    }

   private int alturaAux(NodoAVL n, Comparable elem){
        int a = -1;
        if(n!=null){
            if(elem.compareTo(n.getElem()) == 0){
                a = n.getAltura();
            }else{
                if(elem.compareTo(n.getElem())<0){
                    a = alturaAux(n.getIzquierdo(), elem);
                }else{
                    a = alturaAux(n.getDerecho(), elem);            
                }
            }
        }
        return a;
    }
    //------------------------------------------------------------------------------------------------

//    public boolean insertar(Comparable elem) {
//        boolean exito = true;
//        if (raiz == null) {
//            raiz = new NodoAVL(elem,null, null);
//        } else {
//            System.out.println("se interta el elemento: "+ elem);
//            exito = insertarAux(raiz, elem);
//            System.out.println(this.toStringAux(raiz));
//        }
//        return exito;
//    }

//    private boolean insertarAux(NodoAVL aux, Comparable elem) {
//        boolean rta = true;
//        if ((elem.compareTo(aux.getElem())) == 0) {
//            rta = false;
//        } else if ((elem.compareTo(aux.getElem())) < 0) {
//            if (aux.getIzquierdo() != null) {
//                rta = insertarAux(aux.getIzquierdo(), elem);
//                //aca debe de rotar
//            } else {
//                aux.setIzquierdo(new NodoAVL(elem, null, null));
//            }
//        } else {
//            if (aux.getDerecho() != null) {
//                rta = insertarAux(aux.getDerecho(), elem);
//                //Aca debe de rotar
//            } else {
//                aux.setDerecho(new NodoAVL(elem, null, null));
//            }
//        }
//        if (rta) {
//            //Como aux tiene un nuevo hijo, debo de recalcularle la altura
//            aux.recalcularAltura();
//            
//            //Este caso solo sucede cuando el elemento se inserto, y en la vuelta recursiva
//            //Contenplamos el caso de su la raiz esta desbalanceada, la balanceamos
//            if (aux.getElem().compareTo(raiz.getElem()) == 0) {
//                //en el caso que no se necesite balancear, inserta el nodo que ya estaba como raiz
//                //pero si la raiz esta desbalanceada, entonces si cambia la nuevaRaiz luego del balanceo.
//                NodoAVL raizNueva = balancearAux(aux);
//                raiz = raizNueva;
//            }
//
//            /*Esto funciona a la vuelta recursiva, pregunta si los hijos del 
//            nodo que esta desapilandose recursivamente, estan desbalanceados, entonces los balancea*/           
//            aux.setIzquierdo(balancearAux(aux.getIzquierdo()));
//            aux.setDerecho(balancearAux(aux.getDerecho()));
//
//        }
//        return rta;
//    }
       public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (raiz == null) {
            raiz = new NodoAVL(elem,null, null);
        } else {
            //System.out.println("se interta el elemento: "+ elem);
            exito = insertarAux(raiz, elem);
            //System.out.println(this.toStringAux(raiz));
            
            raiz=balancearAux(raiz);
        }
        return exito;
    }
    private boolean insertarAux(NodoAVL aux, Comparable elem) {
        boolean rta = true;
        if ((elem.compareTo(aux.getElem())) == 0) {
            rta = false;
        } else if ((elem.compareTo(aux.getElem())) < 0) {
            if (aux.getIzquierdo() != null) {
                rta = insertarAux(aux.getIzquierdo(), elem);
                aux.recalcularAltura();
                aux.setIzquierdo(balancearAux(aux.getIzquierdo()));
            } else {
                aux.setIzquierdo(new NodoAVL(elem, null, null));
                aux.recalcularAltura();
            }
        } else {
            if (aux.getDerecho() != null) {
                rta = insertarAux(aux.getDerecho(), elem);
                aux.recalcularAltura();
                aux.setDerecho(balancearAux(aux.getDerecho()));
            } else {
                aux.setDerecho(new NodoAVL(elem, null, null));
                aux.recalcularAltura();
            }
        }
        return rta;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------



//    private boolean eliminarAux(NodoAVL hijo, NodoAVL padre, Comparable elemento) {
//        boolean exito = false;
//        /*System.out.println("entra el eliminarAux con hijo: "+ hijo.getElem()+ " buscando al "
//        +"elemento "+elemento.toString());*/
//        if (hijo != null && !exito) {
//            if ((elemento.compareTo(hijo.getElem())) == 0 && !exito) {
//
//                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
//                    //son Hojas
//                    eliminarHoja(padre, elemento);
//                    exito = true;
//
//                } else if ((hijo.getIzquierdo() != null && hijo.getDerecho() == null) || (hijo.getIzquierdo() == null && hijo.getDerecho() != null)) {
//                    //si n tiene UN hijo
//                    tieneUnHijo(hijo, padre, elemento);
//                    exito = true;
//
//                } else {
//                    // si n tiene ambos hijos
//                    //caso donde tiene dos hijos, pero su hijo izquierdo tiene hijo derecho el cual ese es el candidato
//                    if (hijo.getIzquierdo().getDerecho() != null) {
//                        //Le paso el hijo, el padre que en principio es null si hijo es la raiz, y le paso de nuevo el hijo
//                        tieneDosHijos(hijo.getIzquierdo(), null, hijo);
//                    } else {
//                        //caso donde tiene dos hijos, pero su hijo izquierdo solo tiene tambien hijo izquierdo
//                        tienedosHijosCandidatoNulo(hijo);
//                    }
//                    exito = true;
//                }
//
//            } else {
//                //Llamo con el hijo izquierdo, y si es el que busco, entonces el padre es el mismo nodo en el que ahora estoy parado
//                if (elemento.compareTo(hijo.getElem()) < 0) {
//                    exito=eliminarAux(hijo.getIzquierdo(), hijo, elemento);
//                }
//                //LLamo con el derecho y sucede lo mismo, si es el hijo derecho el que busco, entonces el padre es el nodo en el que ahora estoy parado.
//                if (elemento.compareTo(hijo.getElem()) > 0) {
//                    exito=eliminarAux(hijo.getDerecho(), hijo, elemento);
//                }
//            }
//            if (exito) {
//                //Hacer esto aca, implica que:
//                /*cuando en la vuelta recursiva, este volviendo, se vayan balanceando los nodos.
//                Entonces quedan balanceados cuando llegan al primero que se apilo en la pila de recursion*/
//                //balanceo
//                hijo.setIzquierdo(balancearAux(hijo.getIzquierdo()));
//                hijo.setDerecho(balancearAux(hijo.getDerecho()));
//
//                //Este caso solo sucede cuando el elemento se inserto, y en la vuelta recursiva
//                //Contenplamos el caso de su la raiz esta desbalanceada, la balanceamos
//                if (hijo.getElem().compareTo(raiz.getElem()) == 0) {
//                    //en el caso que no se necesite balancear, inserta el nodo que ya estaba como raiz
//                    //pero si la raiz esta desbalanceada, entonces si cambia la nuevaRaiz luego del balanceo.
//                    NodoAVL raizNueva = balancearAux(hijo);
//                    raiz = raizNueva;
//                }
//
//                //actualizacion de altura
//                hijo.recalcularAltura();
//            }
//        }
//        return exito;
//    }
        public boolean eliminar(Comparable elemento) {
        boolean resultado = true;
        if (raiz != null) {
            //Arranco con raiz y padre de raiz que es null, luego el metodo lo buscara al nodo que quiera eliminar si no es la raiz
            resultado = eliminarAux(raiz, null, elemento);
            raiz=balancearAux(raiz);
        } else {
            resultado = false;
        }
        return resultado;
    }
       private boolean eliminarAux(NodoAVL hijo, NodoAVL padre, Comparable elemento) {
        boolean exito = false;
        /*System.out.println("entra el eliminarAux con hijo: "+ hijo.getElem()+ " buscando al "
        +"elemento "+elemento.toString());*/
        if (hijo != null && !exito) {
            if ((elemento.compareTo(hijo.getElem())) == 0 && !exito) {

                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                    //son Hojas
                    eliminarHoja(padre, elemento);
                    exito = true;

                } else if ((hijo.getIzquierdo() != null && hijo.getDerecho() == null) || (hijo.getIzquierdo() == null && hijo.getDerecho() != null)) {
                    //si n tiene UN hijo
                    tieneUnHijo(hijo, padre, elemento);
                    exito = true;

                } else {
                    // si n tiene ambos hijos
                    //caso donde tiene dos hijos, pero su hijo izquierdo tiene hijo derecho el cual ese es el candidato
                    if (hijo.getIzquierdo().getDerecho() != null) {
                        //Le paso el hijo, el padre que en principio es null si hijo es la raiz, y le paso de nuevo el hijo
                        tieneDosHijos(hijo.getIzquierdo(), null, hijo);
                    } else {
                        //caso donde tiene dos hijos, pero su hijo izquierdo solo tiene tambien hijo izquierdo
                        tienedosHijosCandidatoNulo(hijo);
                    }
                    exito = true;
                }

            } else {
                //Llamo con el hijo izquierdo, y si es el que busco, entonces el padre es el mismo nodo en el que ahora estoy parado
                 //Actualizacion commit 31-07: Balanceo solo donde entro.
                if (elemento.compareTo(hijo.getElem()) < 0) {
                    exito=eliminarAux(hijo.getIzquierdo(), hijo, elemento);
                    hijo.recalcularAltura();
                    hijo.setIzquierdo(balancearAux(hijo.getIzquierdo()));
                }
                //LLamo con el derecho y sucede lo mismo, si es el hijo derecho el que busco, entonces el padre es el nodo en el que ahora estoy parado.
                //Actualizacion commit 31-07: Balanceo solo donde entro.
                if (elemento.compareTo(hijo.getElem()) > 0) {
                    exito=eliminarAux(hijo.getDerecho(), hijo, elemento);
                    hijo.recalcularAltura();
                    hijo.setDerecho(balancearAux(hijo.getDerecho()));
                }
            }
        }
        return exito;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    //Eliminar: Caso 1, donde el nodo es Hoja

    private void eliminarHoja(NodoAVL padre, Comparable elem){
        boolean rta=false;
        //si no tiene hijos
        if(padre == null){
            //caso especial si el padre es nulo (raiz)
            this.raiz = null;
        }else{
            if(padre.getIzquierdo()!=null){
                if(padre.getIzquierdo().getElem().compareTo(elem) == 0){
                    //si n es el HI de padre
                    rta=true;
                    padre.setIzquierdo(null);
                }
            }
            if(padre.getDerecho()!=null && !rta){
                if(padre.getDerecho().getElem().compareTo(elem) == 0){
                    //si n es el HI de padre
                    padre.setDerecho(null);
                }
            } 
        }
    }

    //Eliminar: Caso 2, donde el nodo tiene un solo hijo
    private void tieneUnHijo(NodoAVL hijo, NodoAVL padre, Comparable buscado) {
        if (padre != null) {
            if (padre.getIzquierdo().getElem().compareTo(buscado) == 0 && hijo.getIzquierdo() != null && hijo.getDerecho() == null) {
                padre.setIzquierdo(padre.getIzquierdo().getIzquierdo());
            }
            if (padre.getIzquierdo().getElem().compareTo(buscado) == 0 && hijo.getIzquierdo() == null && hijo.getDerecho() != null) {
                padre.setIzquierdo(padre.getIzquierdo().getDerecho());
            }
            if (padre.getDerecho().getElem().compareTo(buscado) == 0 && hijo.getIzquierdo() != null && hijo.getDerecho() == null) {
                padre.setDerecho(padre.getDerecho().getIzquierdo());
            }
            if (padre.getDerecho().getElem().compareTo(buscado) == 0 && hijo.getIzquierdo() == null && hijo.getDerecho() != null) {
                padre.setDerecho(padre.getDerecho().getDerecho());
            }
        } else {
            if (hijo.getIzquierdo() != null && hijo.getDerecho() == null) {
                raiz = hijo.getIzquierdo();
            }
            if (hijo.getIzquierdo() == null && hijo.getDerecho() != null) {
                raiz = hijo.getDerecho();
            }
        }
    }

    private void tieneDosHijos(NodoAVL nodoCandidato, NodoAVL padreCandidato, NodoAVL raiz) {
        if (nodoCandidato.getDerecho() != null) {
            tieneDosHijos(nodoCandidato.getDerecho(), nodoCandidato, raiz);
        } else {
            /*Si el hijo derecho de nodo es null, significa que ya estoy parado en el candidato
            por ende, seteo el elemento de la "raiz" que es el nodo que quiero eliminar,
            y luego al padre del candidato le seteo su hijo derecho con el hijo izquierdo 
            del nodo candidato*/
            raiz.setElemento(nodoCandidato.getElem());
            padreCandidato.setDerecho(nodoCandidato.getIzquierdo());
        }
        //Hacer esto aca, implica que:
        /*cuando en la vuelta recursiva, este volviendo, se vayan balanceando los nodos.
        Entonces quedan balanceados cuando llegan al primero que se apilo en la pila de recursion*/
        nodoCandidato.setIzquierdo(balancearAux(nodoCandidato.getIzquierdo()));
        nodoCandidato.setDerecho(balancearAux(nodoCandidato.getDerecho()));

        //actualizacion de altura
        nodoCandidato.recalcularAltura();
    }
    
    private void tienedosHijosCandidatoNulo(NodoAVL n){
        n.setElemento(n.getIzquierdo().getElem());
        n.setElemento(n.getIzquierdo().getElem());        
        n.setIzquierdo(n.getIzquierdo().getIzquierdo());
    }

    //-----------------------------------------------------------------------------------------------
    //ROTACIONES SIMPLES
    private NodoAVL rotarIzquierda(NodoAVL r) {
        NodoAVL h = r.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        //RETORNA LA NUEVA RAIZ DEL SUBARBOL.
        return h;
    }

    private NodoAVL rotarDerecha(NodoAVL r) {
        NodoAVL h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        //RETORNA LA NUEVA RAIZ DEL SUBARBOL.
        return h;
    }
    //ROTACIONES DOBLES.

    private NodoAVL rotarDerIzq(NodoAVL r) {
        //Rota el hijo derecho de r, para que se desbalance con el mismo signo
        r.setDerecho(rotarDerecha(r.getDerecho()));
        //Luego rota a izquierda para que quede el nodo balanceado.
        return rotarIzquierda(r);
    }

    private NodoAVL rotarIzqDer(NodoAVL r) {
        //Rota el hijo izquierdo de r, para que se desbalance con el mismo signo
        r.setIzquierdo(rotarIzquierda(r.getIzquierdo()));
        //Luego rota a derecha para que quede el nodo balanceado.
        return rotarDerecha(r);
    }

   private NodoAVL balancearAux(NodoAVL n) {
        NodoAVL aux = n;
        if (n != null) {
            if (balance(n) == 2) {
                if (balance(n.getIzquierdo()) >= 0) {
                    //System.out.println("rota a derecha simple con pivote: "+n.getElem());
                    aux = rotarDerecha(n);
                } else {
                    //System.out.println("rota a izquierda derecha con pivote: "+n.getElem());
                    aux = rotarIzqDer(n);
                }

            }
            if (balance(n) == -2) {
                if (balance(n.getDerecho()) <= 0) {
                    //System.out.println("rota a izquierda simple con pivote: "+n.getElem());
                    aux = rotarIzquierda(n);
                } else {
                    //System.out.println("rota a derecha izquierda con pivote: "+n.getElem());
                    aux = rotarDerIzq(n);
                }
            }
        }
        return aux;
    }

    /*el insertar no necesita revisar para arrriba, ya que siempre se insertar como hoja el nuevo elemento*/
 /*cada vez que insertas se balancean los hijos del nuevo nodo, entonces no necesito ir balanceando para arriba en el insertar, solo me preocupo por la raiz como caso especial, 
    y los hijos del nuevo nodo insertado*/
    private int balance(NodoAVL nodo) {
        return alturaActual(nodo.getIzquierdo()) - alturaActual(nodo.getDerecho());
    }
    
    private int alturaActual(NodoAVL n){
        int alt;
        if(n == null){
            alt = -1;
        }else{
            alt = 1 + Math.max(alturaActual(n.getIzquierdo()), alturaActual(n.getDerecho()));
        }
        return alt;
    }
     public String toString() {
        String mensaje = " ";
        if (raiz == null) {
            mensaje = "Arbol Vacio";
        } else {
            mensaje = toStringAux(this.raiz);
        }
        return mensaje;
    }

    private String toStringAux(NodoAVL nodo) {
        String mensaje = "";
        if (nodo != null) {
            mensaje =  "~Nodo(ALT = "+nodo.getAltura()+"): "+ " ; " +nodo.getElem().toString();
            if (nodo.getIzquierdo() != null) {
                mensaje += ("\n  H.I(ALT = "+nodo.getIzquierdo().getAltura()+") "+ " ; " +nodo.getIzquierdo().getElem().toString())+"   || ";
            } else {
                mensaje += "\n H.I: - " ;
            }
            if (nodo.getDerecho() != null) {
                mensaje += ("  H.D(ALT = "+nodo.getDerecho().getAltura()+") "+" ; " +nodo.getDerecho().getElem().toString()+"\n\n");
            } else {
                mensaje += " H.D: - \n\n";
            }
        }
        if(nodo.getIzquierdo()!=null){
            mensaje+= toStringAux(nodo.getIzquierdo());
        }
        if(nodo.getDerecho()!=null){
            mensaje+= toStringAux(nodo.getDerecho());
        }

        return mensaje;
    }
     //OBTENER DATO
    public Object obtenerDato(Comparable elem){
        Object res = null;
        if(this.raiz!=null){
            res = obtenerDatoAux(this.raiz, elem);
        }
        return res;
    }
    
    private Object obtenerDatoAux(NodoAVL n, Comparable elem){
        Object encontrada = null;
        if(n!=null){
            if(elem.compareTo(n.getElem()) == 0){
                 encontrada = n.getElem();
            }else{
                if(elem.compareTo(n.getElem()) < 0){
                    encontrada = obtenerDatoAux(n.getIzquierdo(),elem);
                
                }else{
                    encontrada = obtenerDatoAux(n.getDerecho(),elem);
                }
            }       
        }   
        return encontrada;
    }
        public Lista listarRango(Comparable min, Comparable max) {
            //Le ingresaran 2 ciudades y se encargara de devolver una lista en el rango entre las ciudades.
        Lista lis = new Lista();
        listarRangoAux(raiz, min, max, lis);
        return lis;
    }

    private void listarRangoAux(NodoAVL aux, Comparable min, Comparable max, Lista lis) {
        if (aux != null) {
            /*Al ingresar dos TDAS ciudades, reescribira y ocupara el compareTo de la Ciudad como tal
            y NO el de la clase Comparable*/

            //Si el minimo es menor que el elemento donde estoy parado, entonces bajo a izquierda
            if ((min.compareTo(aux.getElem()) < 0)) {
                listarRangoAux(aux.getIzquierdo(), min, max, lis);
            }

            //Si el elemento esta en el rango entre el min y el max, lo inserto en la lista
            if ((min.compareTo(aux.getElem()) <= 0) && (max.compareTo(aux.getElem()) >= 0)) {
                lis.insertar(lis.longitud() + 1, aux.getElem());
            }

            //Si el maximo es mayor que el elemento donde estoy parado, entonces bajo a derecha.
            if ((max.compareTo(aux.getElem()) > 0)) {
                listarRangoAux(aux.getDerecho(), min, max, lis);
            }

        }
    }
    public Lista listarInOrden() {
        Lista equipos = new Lista();
        listarInOrdenAux(raiz, equipos);
        return equipos;
    }

    private void listarInOrdenAux(NodoAVL nodo, Lista equipos) {
        if (nodo != null) {
            listarInOrdenAux(nodo.getIzquierdo(), equipos);
            equipos.insertar(1, nodo.getElem());
            listarInOrdenAux(nodo.getDerecho(), equipos);
        }
    }
}
