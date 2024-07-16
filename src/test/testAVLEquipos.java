
package test;
import TDAS.Equipo;
import arbolAVL.ArbolAVL;


 // @author ulise
public class testAVLEquipos {


    public static void main(String[] args) {
        // 
        Equipo eq1 = new Equipo("Arg", "Scanloni", 'A', 0, 0, 0);
        Equipo eq2 = new Equipo("Bra", "Scanloni", 'A', 0, 0, 0);
        Equipo eq3 = new Equipo("Ecu", "Scanloni", 'A', 0, 0, 0);
        Equipo eq4 = new Equipo("Can", "Scanloni", 'A', 0, 0, 0);
        Equipo eq5 = new Equipo("Uru", "Scanloni", 'A', 0, 0, 0);
        Equipo eq6 = new Equipo("Per", "Scanloni", 'A', 0, 0, 0);
        Equipo eq7 = new Equipo("Col", "Scanloni", 'A', 0, 0, 0);
        ArbolAVL arbolito = new ArbolAVL();
        arbolito.insertar(eq1);
        arbolito.insertar(eq2);
        System.out.println(arbolito.toString());
        System.out.println("-----------------------------------------------------------");
        arbolito.insertar(eq1);            
        System.out.println(arbolito.toString());
    }

}
