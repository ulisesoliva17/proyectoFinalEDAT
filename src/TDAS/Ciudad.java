package TDAS;

/**
 *
 * @author ulise
 */
public class Ciudad implements Comparable {
    private String nombre;
    private boolean almaceDispo;
    private boolean esSedeCiu;
    
    
    public Ciudad(String nom, boolean al, boolean es){
        nombre=nom;
        almaceDispo=al;
        esSedeCiu=es;
    }
    @Override
    /*Un valor negativo si la cadena que llama al método es lexicográficamente menor que la cadena pasada como argumento.
    Cero si ambas cadenas son iguales.
    Un valor positivo si la cadena que llama al método es lexicográficamente mayor que la cadena pasada como argumento.*/
    public int compareTo(Object obj) {
        Ciudad otra= (Ciudad) obj;
        return this.nombre.compareTo(otra.nombre);
    }
    
    public boolean equals(Object obj){
        Ciudad otra= (Ciudad) obj;
        return nombre.equals(otra.nombre);
    }
    public String getNombre(){
        return nombre;
    }
    //No debe de existe serNombre ya que es campo clave
    public boolean almaceDispo(){
        return almaceDispo;
    }
    public boolean esSede(){
        return esSedeCiu;
    }
    public String toString(){
        return nombre;
    }
   

}
