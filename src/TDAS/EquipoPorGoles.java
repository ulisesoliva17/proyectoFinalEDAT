package TDAS;
import TDAS.Equipo;

/**
 *
 * @author ulise
 */
public class EquipoPorGoles implements Comparable {
    private String nombre;
    private String nombreDT;
    private char grupo;
    private int puntosGanados;
    private int golesAFavor;
    private int golesEnContra;
    
    public EquipoPorGoles(Object obj){
        Equipo otro= (Equipo)obj;
        nombre=otro.getNombre();
        nombreDT=otro.getNombreDT();
        grupo=otro.getGrupo();
        puntosGanados=otro.getPuntosGanados();
        golesAFavor=otro.getGolesAFavor();
        golesEnContra=otro.getGolesEnContra();
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
        return "Equipo: " + nombre+" - Con goles: "+golesAFavor;
    }

   public int compareTo(Object obj) {
        int resultado=0;
        EquipoPorGoles otroEquipo = (EquipoPorGoles) obj;
        if (this.golesAFavor < otroEquipo.getGolesAFavor()) {
            resultado= -1;
        } else if (this.golesAFavor == otroEquipo.getGolesAFavor()) {
            resultado= this.nombre.compareTo(otroEquipo.getNombre().toUpperCase());
        } else {
            resultado= 1;
        }
        return resultado;
    }
}
