
package ListaPartidos;
import TDAS.Partido;
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
    
    
    public boolean insertar(int pos, Partido nuevoElem){
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
    
    public int localizar(Partido elem){
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
    
//    public String toString(){
//        String retorno = "Lista Vacia";
//        //evaluamos que la lista no este vacia
//        if(this.cabecera != null){
//            retorno = "[";
//            Nodo aux = this.cabecera;
//            
//            while(aux != null){
//                retorno = retorno + aux.getElem().toString();
//                if(aux.getEnlace() != null){
//                    retorno += ",";
//                }
//                aux = aux.getEnlace();
//            }
//            
//            retorno += "]";
//        }
//        return retorno;
//    }
      public String toString(){
        String msg = "";
        Nodo aux = this.cabecera;
        int i = 1; 
        while(aux != null){
            msg += "Partido " +i+ ":\n"; 
            msg += aux.getElem().toString() + "\n";
            msg += "\n";
            aux = aux.getEnlace();
            i++;
        }
        return msg;
    }
}
