package TDAS;

/**
 *
 * @author ulise
 */
public class Partido {
    private int golesEq1;
    private int golesEq2;
    private String instancia;
    
    public Partido(int g1, int g2,  String insta){
        golesEq1=g1;
        golesEq2=g2;
        instancia=insta;
    }

    public int getGolesEq1() {
        return golesEq1;
    }

    public int getGolesEq2() {
        return golesEq2;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setGolesEq1(int golesEq1) {
        this.golesEq1 = golesEq1;
    }

    public void setGolesEq2(int golesEq2) {
        this.golesEq2 = golesEq2;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }
    public String toString(){
        String msj= " instancia: "+instancia+"- G1: "+golesEq1+"- G2: "+golesEq2+"\n";
        return msj;
    }
    //Como el nodo hash ClavePartido tendra como hijos a partidos
    //Para saber el partido exacto que estoy buscando, lo hare mediante la instancia
    //para recorrer los partidos que se disputaron entre arg-chile por ejemplo
    public boolean equals(Partido otroPartido){
        return instancia.equalsIgnoreCase(otroPartido.getInstancia());
    }

}
