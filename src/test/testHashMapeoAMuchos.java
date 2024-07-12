
package test;
import HashPartidos.HashMapeoAMuchos;
import TDAS.ClavePartido;
import TDAS.Partido;


 // @author ulise
public class testHashMapeoAMuchos {


    public static void main(String[] args) {
        // 
        HashMapeoAMuchos tablita = new HashMapeoAMuchos();
        ClavePartido cl1= new ClavePartido("Argentina","Brasil");
        Partido partidoArgBra= new Partido("FG", "", "", 0, 0);
        Partido partidoArgBra2= new Partido("CUARTOS", "", "", 0, 0);
        Partido partidoArgBra3= new Partido("SEMI", "", "", 0, 0);
        tablita.insertar(cl1, partidoArgBra);
        tablita.insertar(cl1, partidoArgBra2);
        tablita.insertar(cl1, partidoArgBra3);
        
        ClavePartido cl2= new ClavePartido("Canada","Mexico");
        Partido canme= new Partido("FG", "AAA", "", 0, 0);
        Partido canme2= new Partido("SEMI", "BBB", "", 0, 0);
        Partido canme3= new Partido("FINAL", "CCC", "", 0, 0);
        tablita.insertar(cl2, canme);
        tablita.insertar(cl2, canme2);
        tablita.insertar(cl2, canme3);
        
        ClavePartido cl3= new ClavePartido("Uruguay","Colombia");
        Partido urucol= new Partido("FG", "", "", 0, 0);
        Partido urucol2= new Partido("SEMI", "", "", 0, 0);
        Partido urucol3= new Partido("FINAL", "", "", 0, 0);
        tablita.insertar(cl3, urucol);
        tablita.insertar(cl3, urucol2);
        tablita.insertar(cl3, urucol3);
        System.out.println(tablita.obtenerDato(cl3, urucol2));
    }

}
