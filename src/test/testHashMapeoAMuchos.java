
package test;
import HashPartidos.HashMapeoAMuchos;
import TDAS.ClavePartido;
import TDAS.Partido;


 // @author ulise
public class testHashMapeoAMuchos {


    public static void main(String[] args) {
        // 
        ClavePartido cl= new ClavePartido("Argentina","Brasil");
        Partido partidoArgBra= new Partido(2, 0, "FG");
        Partido partidoArgBra2= new Partido(3, 1, "Semi");
        Partido partidoArgBra3= new Partido(3, 1, "Final");
        
        ClavePartido cl1= new ClavePartido("brasil","alemania");
        Partido partidoBraAle= new Partido(2, 0, "FG");
        Partido partidoBraAle2= new Partido(3, 1, "Semi");
        Partido partidoBraAle3= new Partido(3, 1, "Final");
        
        ClavePartido cl2= new ClavePartido("Francia","Inglaterra");
        Partido partidoFraIn= new Partido(2, 2, "FG");
        Partido partidoFraIn2= new Partido(3, 1, "Cuartos");
        Partido partidoFraIn3= new Partido(3, 1, "octavos");
        HashMapeoAMuchos tablita = new HashMapeoAMuchos();
        tablita.insertar(cl, partidoArgBra);
        tablita.insertar(cl, partidoArgBra2);
        tablita.insertar(cl, partidoArgBra3);
        tablita.insertar(cl1, partidoBraAle);
//        tablita.insertar(cl1, partidoBraAle2);
//        tablita.insertar(cl1, partidoBraAle3);
        tablita.insertar(cl2, partidoFraIn);
        tablita.insertar(cl2, partidoFraIn2);
        tablita.insertar(cl2, partidoFraIn3);
        System.out.println(tablita.toString());
        System.out.println(tablita.eliminar(cl1, partidoBraAle));
        System.out.println("----------------------------------------");
        System.out.println(tablita.toString());
    }

}
