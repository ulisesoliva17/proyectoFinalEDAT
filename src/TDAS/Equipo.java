package TDAS;

/**
 *
 * @author ulise
 */
public class Equipo implements Comparable {
    private String nombre;
    private String nombreDT;
    private char grupo;
    private int puntosGanados;
    private int golesAFavor;
    private int golesEnContra;
    
    public Equipo(String nom,String n,char g,int pg,int gf,int gc){
        nombre=nom;
        nombreDT=n;
        grupo=g;
        puntosGanados=pg;
        golesAFavor=gf;
        golesEnContra=gc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreDT() {
        return nombreDT;
    }

    public char getGrupo() {
        return grupo;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public int getGolesEnContra() {
        return golesEnContra;
    }
    
    //Setters

    public void setNombreDT(String nombreDT) {
        this.nombreDT = nombreDT;
    }

    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }

    public void setPuntosGanados(int puntosGanados) {
        this.puntosGanados = puntosGanados;
    }

    public void setGolesAFavor(int golesAFavor) {
        this.golesAFavor = golesAFavor;
    }

    public void setGolesEnContra(int golesEnContra) {
        this.golesEnContra = golesEnContra;
    }

    @Override
    public String toString() {
        return "Equipo: " + nombre;
    }
    public String toStringCompleto() {
        return "Equipo: " + nombre+" - Nombre DT:"+nombreDT+" - Grupo: "+grupo+" - Puntos Ganados: "
                +puntosGanados+" - Goles a Favor: "+golesAFavor+" - Goles en Contra: "+golesEnContra;
    }
    public boolean equals(Object obj){
        Equipo otroEquipo= (Equipo) obj;
        return nombre.equalsIgnoreCase(otroEquipo.getNombre().toUpperCase());
    }
    
    public int compareTo(Object obj) {
        Equipo otroEquipo = (Equipo) obj;
        // Comparación basada en el nombre del equipo (orden lexicográfico)
        return this.nombre.compareTo(otroEquipo.getNombre().toUpperCase());
    }
    

}
