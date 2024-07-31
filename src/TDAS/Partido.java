package TDAS;

/**
 *
 * @author ulise
 */
public class Partido {
    private String instancia;
    private String ciudad;
    private String estadio;
    private int golesEq1;
    private int golesEq2;
   
    
    public Partido(String insta,String ciu,String esta,int g1, int g2){
        instancia=insta;
        ciudad=ciu;
        estadio=esta;
        golesEq1=g1;
        golesEq2=g2;
    }

    public String getInstancia() {
        return instancia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public int getGolesEq1() {
        return golesEq1;
    }

    public int getGolesEq2() {
        return golesEq2;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public void setGolesEq1(int golesEq1) {
        this.golesEq1 = golesEq1;
    }

    public void setGolesEq2(int golesEq2) {
        this.golesEq2 = golesEq2;
    }
    
    public String toString(){
        String msj= " instancia: "+instancia+" - Ciudad: "+ciudad+" - Estadio: "+estadio+" - G1: "+golesEq1+" - G2: "+golesEq2+"\n";
        return msj;
    }
    //Como el nodo hash ClavePartido tendra como hijos a partidos
    //Para saber el partido exacto que estoy buscando, lo hare mediante la instancia
    //para recorrer los partidos que se disputaron entre arg-chile por ejemplo
    public boolean equals(Object obj){
        Partido otroPartido= (Partido)obj;
        return instancia.equals(otroPartido.getInstancia());
    }

}
