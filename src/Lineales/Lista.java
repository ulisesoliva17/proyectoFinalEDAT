
package Lineales;

/**
 *
 * @author ulise
 */
public class Lista {
    private Nodo cabecera;
    
    public Lista(){
        cabecera=null;
    }
    
    public boolean esVacia(){
        return cabecera==null;
    }
    
    public int longitud(){
        int longi=0;
        Nodo aux=cabecera;
        while(aux!=null){
            longi=longi+1;
            aux=aux.getEnlace();
        }
        return longi;
    }
    
    
    public boolean insertar(int pos, Object nuevoElem){
        boolean exito=true;
        int longi=longitud();
        
        if(pos<1 || pos>longi+1){
            exito=false;
        }else{
            if(pos==1){
                Nodo nuevo= new Nodo(nuevoElem,null);
                nuevo.setEnlace(cabecera);
                cabecera=nuevo;
            }else{
                Nodo aux=cabecera;
                int reco=1;
                while(reco<pos-1){
                    aux=aux.getEnlace();
                    reco++;
                }
                Nodo nuevo= new Nodo(nuevoElem,null);
                nuevo.setEnlace(aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        
        return exito;
    }
    
    public boolean eliminar (int pos){
        boolean exito=false;
         int longi=longitud();
        if(pos>=1 && pos<=longi){
            if(pos==1){
                //CASO DONDE ELIMINA EL PRIMER ELEMENTO
                cabecera= cabecera.getEnlace();
                exito=true;
            }else{
                //CASO DONDE ELIMINA GENERAL.
                Nodo aux= cabecera;
                int reco=1;
                while(reco!= pos-1){
                    reco= reco+1;
                    aux= aux.getEnlace();
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                exito=true;
            }
        }
        
        return exito;
    }
    
    public void vaciar(){
        cabecera=null;
    }
    
    public Object recuperar(int pos){
        Nodo aux=cabecera;
        int reco=1;
        Object retorno=null;
        int longi=longitud();
        
        if(pos<1 || pos>longi){
            retorno=null;
        }else{
            while(reco!=pos){
                aux=aux.getEnlace();
                reco=reco+1;
            }
            retorno= aux.getElem();
        }
        return retorno;
    }
 
    
    public int localizar(Object elem){
        Nodo aux=cabecera;
        int contador=1;
        while(aux!=null && (!(elem.equals(aux.getElem())))){
            aux=aux.getEnlace();
            contador++;
        }
        if(aux==null){
            contador=-1;
        }
        return contador;
    }
    
    public String toString(){
        String retorno = "Lista Vacia";
        //evaluamos que la lista no este vacia
        if(this.cabecera != null){
            retorno = "[";
            Nodo aux = this.cabecera;
            
            while(aux != null){
                retorno = retorno + aux.getElem().toString();
                if(aux.getEnlace() != null){
                    retorno += ",";
                }
                aux = aux.getEnlace();
            }
            
            retorno += "]";
        }
        return retorno;
    }
    public Lista obtenerMultiplos(int num) {
                Lista retorno = new Lista();
                Nodo aux = cabecera;
                Nodo aux2 = null;
                int contador = 1;
                if (cabecera != null) {
                    while (aux != null) {
                        if (contador % num == 0) {
                            Nodo nuevo = new Nodo(aux.getElem(), null);
                            if (retorno.cabecera == null) {
                                retorno.cabecera = nuevo;
                                //aux2 recorre la lista retorno.
                                aux2 = retorno.cabecera;
                            } else {
                                aux2.setEnlace(nuevo);
                                //Este aux2 le asigno el siguiente enlace, para que en la siguiente vuelta
                                //pueda seguir enganchando los nuevos nodos que vienen
                                //Es decir, aux2 siempre queda referenciado al "nuevo" nodo que se acaba de enganchar arriba en "aux2.setEnlace(nuevo);"
                                aux2 = aux2.getEnlace();
                            }
                        }
                        aux = aux.getEnlace();
                        contador++;
                    }
                }
                return retorno;
            }
    public void eliminarApariciones(Object x){
        Nodo aux1=cabecera;
        Nodo aux2=null;
        while(aux1!=null && aux1.getElem().equals(x)){
                cabecera=cabecera.getEnlace();
                aux1=cabecera;
         }
        
        //Este lo hago para poder ir avanzando con el aux1 y tener al aux2 antes para setear enlaces.
        aux2=aux1;
        
        while(aux1!=null){
            if(aux1!=null && aux1.getElem().equals(x)){
                aux2.setEnlace(aux1.getEnlace());
            }
         //Este lo hago para poder ir avanzando con el aux1 y tener al aux2 antes para setear enlaces a medida que avanzo.Por eso cada iteracion lo igualo porque lo necesito tener inmediatamente atras.
            aux2=aux1;
            aux1=aux1.getEnlace();
            
        }
    }
    public void agregarElem(Object x,int pos){
        Nodo aux1=cabecera;
        int contador=0;
        if(cabecera!=null){
            Nodo nuevo = new Nodo(x,null);
            nuevo.setEnlace(cabecera);
            cabecera=nuevo;
            aux1=cabecera;
        }
        //Este while lo uso para recorrer toda la lista con el aux1.
        while(aux1!=null){
            
                //Este while lo uso para siempre quedar en la posicion donde debo insertar.
                while(aux1!=null && contador!=pos){
                    aux1=aux1.getEnlace();
                    contador++;
                }
                //Este if es necesario ya que al salir del while, ese while consideraba que aux1!=null
                //Pero nosotros no lo estabamos contemplando, por ende ahora no agarra el pointer exception
                if(aux1!=null){
                    contador=0;
                    Nodo nuevo = new Nodo(x,null);
                    nuevo.setEnlace(aux1.getEnlace());
                    aux1.setEnlace(nuevo);
                    aux1=aux1.getEnlace();
                }
        }
    }
    public void agregarProducto(int x){
        Nodo aux1= cabecera;
        Nodo aux2= null;
        int acum1=0;
        int acum2=1;
        int contador=0;
        while(aux1!=null){
            while(aux1!=null && contador!=x){
                acum1= (int)aux1.getElem();
                acum2= acum2*acum1;
                contador++;
                //Uso aux2=aux1 para que aux2 quede una posicion anterior al aux1 y asi poder setear.
                aux2=aux1;
                aux1=aux1.getEnlace();
            }
            
            if(aux1!=null && aux2!=null){
                Nodo nuevo= new Nodo(acum2,null);
                nuevo.setEnlace(aux1);
                aux2.setEnlace(nuevo);
                acum1=0;
                acum2=1;
                contador=0;
            }
            
        }
    }
    public void cambiarPosiciones(int pos1, int pos2){
        Nodo aux1=cabecera;
        Nodo aux2=cabecera;
        Nodo nuevo=new Nodo(null,null);
        int contador1=1;
        int contador2=1;
        if(pos1==1 && pos2>1){
            while(contador2!=pos2 && aux2!=null){
                aux2=aux2.getEnlace();
                contador2++;
            }
            nuevo.setElem(cabecera.getElem());
            cabecera= cabecera.getEnlace();
            nuevo.setEnlace(aux2.getEnlace());
            aux2.setEnlace(nuevo);
            
        }
        if(pos1>1 && pos2>1 && pos2>pos1){
            while(contador1!=(pos1-1) && aux1!=null){
                aux1=aux1.getEnlace();
                contador1++;
            }
            while(contador2!=pos2 && aux2!=null){
                aux2=aux2.getEnlace();
                contador2++;
            }
            nuevo.setElem(aux1.getEnlace().getElem());
            aux1.setEnlace(aux1.getEnlace().getEnlace());
            nuevo.setEnlace(aux2.getEnlace());
            aux2.setEnlace(nuevo);
        }
        if(pos1>pos2 && pos2>1){
            
            while(contador1!=(pos1-1) && aux1!=null){
                aux1=aux1.getEnlace();
                contador1++;
            }
            while(contador2!=(pos2-1) && aux2!=null){
                aux2=aux2.getEnlace();
                contador2++;
            }
            nuevo.setElem(aux1.getEnlace().getElem());
            aux1.setEnlace(aux1.getEnlace().getEnlace());
            nuevo.setEnlace(aux2.getEnlace());
            aux2.setEnlace(nuevo);
        }
        if(pos1>pos2 && pos2==1){
            while(contador1!=(pos1-1) && aux1!=null){
                aux1=aux1.getEnlace();
                contador1++;
            }
            nuevo.setElem(aux1.getEnlace().getElem());
            aux1.setEnlace(aux1.getEnlace().getEnlace());
            nuevo.setEnlace(cabecera);
            cabecera=nuevo;
        }
    }
    public void eliminarRepetidos(){
        Nodo aux1=cabecera;
        Nodo aux2=cabecera;
        Nodo aux3=aux2;
        while(aux1!=null){
            while(aux2!=null){
                aux3=aux2;
                aux2=aux2.getEnlace();
                if(aux1!=null && aux2!=null && aux3!=null && aux1.getElem().equals(aux2.getElem())){
                    aux3.setEnlace(aux2.getEnlace());
                }
            }
            aux1=aux1.getEnlace();
            aux2=aux1;
            aux3=aux2;
        }
    }
    public void eliminarImpares(){
        Nodo aux1= cabecera;
        Nodo aux2= aux1;
        while(aux1!=null && (int)aux1.getElem()%2!=0 ){
            cabecera= cabecera.getEnlace();
            aux1=cabecera;
        }
        while(aux1!=null){
            if( (int)aux1.getElem()%2!=0 ){
                aux2.setEnlace(aux1.getEnlace());
            }
            aux2=aux1;
            aux1=aux1.getEnlace();
        }
    }
    public boolean esCapicua(){
        Nodo aux1= cabecera;
        Nodo aux2= new Nodo(null,null);
        int longi= this.longitud();
        boolean rta=true;
        int i=0;
        int contador=1;
        while(aux1!=null && aux2!=null && rta && aux1!=aux2){
            aux2=cabecera;
            while(contador!=(longi-i)&& aux1!=aux2){
                aux2=aux2.getEnlace();
                contador++;
            }
            if(aux1!= null && aux2!=null &&  aux1.getElem().equals(aux2.getElem())&& aux1!=aux2){
                rta=true;
                aux1=aux1.getEnlace();
                i++;
                contador=1;
            }else{
                rta=false;
            }
        }
        if(rta && aux1==aux2){
            rta=true;
        }else{
            rta=false;
        }
        return rta;
    }

}
