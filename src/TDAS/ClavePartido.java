package TDAS;

import java.util.Objects;

/**
 *
 * @author ulise
 */
public class ClavePartido {
    private String equipo1;
    private String equipo2;
    
   public ClavePartido(String eq1, String eq2) {
       /*Hacemos esto porque el enunciado nos pide que:
        donde siempre se guardará como equipo1 el de
        nombre menor alfabéticamente que equipo2 */
        if (eq1.compareToIgnoreCase(eq2) < 0) {
            equipo1 = eq1;
            equipo2 = eq2;
        } else {
            equipo1 = eq2;
            equipo2 = eq1;
        }
    }
    
    public Object getEquipo1(){
        return equipo1;
    }
    
    public Object getEquipo2(){
        return equipo2;
    }
    
    public String toString(){
        return "Clave Partido con Eq1:  "+equipo1+ " , y Eq2:  "+equipo2;
    }
    
    public boolean equals(ClavePartido otraClave){
        return equipo1.equalsIgnoreCase(otraClave.equipo1) && equipo2.equalsIgnoreCase(otraClave.equipo2);
    }

    @Override
public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.equipo1);
    hash = 23 * hash + Objects.hashCode(this.equipo2);
    return Math.abs(hash % 100);
}

    


}
