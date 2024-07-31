
package GrafoCiudades;
import TDAS.Ciudad;
import java.util.Scanner;


 // @author ulise
public class testGrafo {


    public static void main(String[] args) {
        // 
        Grafo grafito= new Grafo();
        Scanner sc = new Scanner(System.in);
        //Testeo si elimina bien el caso donde el nodo es cabecera.
//        grafito.insertarVertice(1);
//        System.out.println(grafito.toString());
//        System.out.println("------------------------------------------------------------");
//        grafito.eliminarVertice(1);
//        System.out.println(grafito.toString());
//        System.out.println("------------------------------------------------------------");
        Ciudad uno = new Ciudad("Uno", true, true);
        Ciudad dos = new Ciudad("Dos", true, true);
        Ciudad tres = new Ciudad("Tres", true, false);
        Ciudad cuatro = new Ciudad("Cuatro", true, false);
        grafito.insertarVertice(uno);
        System.out.println("------------------Debe largar False-------------------");
        System.out.println(grafito.insertarVertice(uno));
        System.out.println("------------------------------------------------------------");
        grafito.insertarVertice(dos);
        grafito.insertarVertice(tres);
        grafito.insertarVertice(cuatro);
        grafito.insertarArco(uno, dos, 1122);
        grafito.insertarArco(dos, tres, 2233);
        grafito.insertarArco(uno, tres, 1133);
        grafito.insertarArco(tres, cuatro, 3344);
         System.out.println(grafito.toString());
        System.out.println("La eliminacion del Arco con vertices: "+uno+" y "+tres+" se realizo: "+grafito.eliminarArco(uno, tres));
         System.out.println("Eliminacion del vertice "+uno);
        System.out.println(grafito.eliminarVertice(uno));
        System.out.println("------------------------------------------------------------");
        System.out.println(grafito.toString());
        
    }

}
