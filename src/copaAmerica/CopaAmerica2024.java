
package copaAmerica;
import GrafoCiudades.Grafo;
import HashPartidos.HashMapeoAMuchos;
import TDAS.Ciudad;
import TDAS.ClavePartido;
import TDAS.Partido;
import TDAS.Equipo;
import arbolAVL.ArbolAVL;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

 // @author Ulises
public class CopaAmerica2024 {


    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        ArbolAVL equipos= new ArbolAVL();
        HashMapeoAMuchos partidos= new HashMapeoAMuchos();
        Grafo ciudades= new Grafo();
        
        String datos = "/Users/ulise/Desktop/BASE-DE-DATOS.txt";
        int eleccion = 0;
        
        System.out.println("Presione '1' para realizar la carga inicial");
        eleccion = sc.nextInt();
         if(eleccion == 1){
            cargarDatos(datos, equipos, partidos, ciudades);
            String txt1 = "Se ha realizado la carga de datos correctamente. \n";
            System.out.println(txt1);
            txt1 += "ESTADO INICIAL DE LA BASE DE DATOS: \n";
            txt1 += mostrarSistemaAux(equipos, partidos, ciudades);
            registrarMovimiento(txt1);
            ejecutaMenu(equipos, partidos, ciudades);
        }
        
         String txt2 = "ESTADO FINAL DE LA BDD: \n";
        txt2 += mostrarSistemaAux(equipos, partidos, ciudades);
        registrarMovimiento(txt2);
        
        System.out.println("FIN.");
    }
    //Carga de Datos
     public static void cargarDatos(String datos, ArbolAVL equipos, HashMapeoAMuchos partidos,Grafo ciudades){ 
        try(BufferedReader bufferLectura = new BufferedReader(new FileReader(datos))){
            String linea;
            //while encargado de cada linea de lectura del txt
            while((linea = bufferLectura.readLine()) != null){
                //guardamos en un arreglos de String la linea completa, separando cada " ; " 
                //es decir, en la pos 0 guardara la letra, luego en  pos1 lo que venga despues de ","
                //y asi sucesivamente hasta que termine la linea de codigo y salte a la siguiente
               String[] datosLinea = linea.split(";");
                if (datosLinea[0].equals("C")) {
                    //Si es una ciudad esa linea, al metodo cargarCiudad le debo pasar la linea (guardada en el arreglo) y el grafo
                    cargarCiudad(datosLinea, ciudades);
                }
                if (datosLinea[0].equals("R")) {
                    cargarRuta(datosLinea, ciudades);
                }
                if (datosLinea[0].equals("E")) {
                    cargarEquipo(datosLinea, equipos);
                }
                if (datosLinea[0].equals("P")) {
                    cargarPartido(datosLinea, partidos);
                }
            
            }
        
        } catch (IOException ex) {
            //En el caso que no se lea o no escriba correcto, salta el catch.
                System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }
     //CARGA DE DATOS DESDE EL TXT
     private static void cargarCiudad(String[] datosCiudad, Grafo ciudades){
        String nombre = datosCiudad[1];
        boolean alojamiento = ("TRUE").equals(datosCiudad[2]);
        boolean esSede = ("TRUE").equals(datosCiudad[3]);
        Ciudad unaCiudad = new Ciudad(nombre, alojamiento, esSede);
        ciudades.insertarVertice(unaCiudad);
    }
      private static void cargarRuta(String[] datosRuta, Grafo ciudades){
        String origen = datosRuta[1];
        String destino = datosRuta[2];
        double distancia = Double.parseDouble(datosRuta[3]);
        //No inserto origen y vertices al grafo porque se supone que ya se debe de haber insertado ambos elementos.
//        ciudades.insertarVertice(origen);
//        ciudades.insertarVertice(destino);
        ciudades.insertarArco(origen, destino, distancia);
    }
      private static void cargarEquipo(String[] datosEquipo,ArbolAVL equipos){
          String nombre = datosEquipo[1];
          String nombreDT = datosEquipo[2];
          char grupo = datosEquipo[3].charAt(0);
          int puntosGanados = Integer.parseInt(datosEquipo[4]);
          int golesAFavor = Integer.parseInt(datosEquipo[5]);
          int golesEnContra = Integer.parseInt(datosEquipo[6]);
          Equipo eq1= new Equipo(nombre, nombreDT, grupo, puntosGanados, golesAFavor, golesEnContra);
          equipos.insertar(nombre, eq1);
      }
       private static void cargarPartido(String[] datosPartido, HashMapeoAMuchos partidos){
        String eq1 = datosPartido[1];
        String eq2 = datosPartido[2];
        String instancia = datosPartido[3];
        String ciudad = datosPartido[4];
        String estadio = datosPartido[5];
        int G1 = Integer.parseInt(datosPartido[6]);
        int G2 = Integer.parseInt(datosPartido[4]);
        
        ClavePartido unaClave = new ClavePartido(eq1, eq2);
        Partido unPartido= new Partido(instancia, ciudad, estadio, G1, G2);
        
        partidos.insertar(unaClave, unPartido);
    }
       //MENU
        public static void ejecutaMenu(ArbolAVL equipos,HashMapeoAMuchos partidos,Grafo ciudades){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        
        registrarMovimiento("MODIFICACIONES EN EL TRANSCURSO DE LA EJECUCION: ");
        
        do{
            imprimeMenu();
            eleccion = sc.nextInt();
            
            switch(eleccion){
                
                case 0:
                    break;
                case 1:
                    abmCiudades(ciudades);                           
                    break;
                case 2:
                    abmRutas(ciudades);
                    break;
                case 3:
                    abmEquipos(equipos);
                    break;
                case 4:
                    abmPartidos(partidos);
                    break;
                case 5:
                    //consultarCliente(clientes);
                    break;
                case 6:
                    //menuConsultarCiudades(ciudades);
                    break;
                case 7:
                    //consultarViajes(rutas);
                    break;
                case 8:
                    //verificarViaje(solicitudes, rutas);
                    break;
                case 9:
                    //mostrarSistema(equipos, partidos, ciudades);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
    
    }
         public static void imprimeMenu(){
        
        System.out.println("---MENU---");
        
        System.out.println("0. SALIR.");
        
        System.out.println("1. ALTAS-BAJAS-MODIFICACIONES de CIUDADES.");
        
        System.out.println("2. ALTAS-BAJAS-MODIFICACIONES de RED AEREA DE RUTAS.");
        
        System.out.println("3. ALTAS-BAJAS-MODIFICACIONES de EQUIPOS.");
        
        System.out.println("4. ALTAS-BAJAS-MODIFICACIONES de PARTIDOS.");
        
        System.out.println("5. Consulta sobre Ciudades.");
        
        System.out.println("6. Consulta sobre Rutas Aereas entre Ciudades.");
        
        System.out.println("7. Consulta sobre Partidos.");
        
        System.out.println("7. Consulta sobre Equipos.");
        
        System.out.println("9. Mostrar Sistema.");
    
    }   
         //1 - ABM CIUDADES
    
    public static void abmCiudades(Grafo ciudades){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMCiudades();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarCiudad(ciudades);
                    break;              
                case 2:
                    eliminarCiudad(ciudades);
                    break;
                case 3:
                    //modificarCiudad(ciudades);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
    
    }
    private static void menuABMCiudades(){
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ciudad.");  
        System.out.println("2. Eliminar una ciudad.");
        System.out.println("3. Modificar una ciudad.");
    }
    
     private static void agregarCiudad(Grafo ciudades){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombre;
        boolean aloDispo,esSede;
        System.out.println("ALTA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el nombre de la ciudad.");
            nombre = sc.next();
            System.out.println("Ingrese si tiene alojamiento disponible.");
            aloDispo = sc.nextBoolean();
            System.out.println("Ingrese si es Sede o no");
            esSede = sc.nextBoolean();

            Ciudad unaCiudad = new Ciudad(nombre, aloDispo, esSede);

            boolean exito = ciudades.eliminarVertice(unaCiudad);
            
            if(exito){
                
                txt = ("Se agrego la ciudad: " +unaCiudad.toString()+ " correctamente.");
                System.out.println(txt);
                registrarMovimiento(txt);       

            }else{

                System.out.println("ERROR. No ha sido posible agregar la ciudad.");

            }
        }
    }
      private static void eliminarCiudad(Grafo ciudades){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        String nombre;
        int  eleccion;
        System.out.println("BAJA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el nombre de la ciudad que desea dar de BAJA.");

            nombre = sc.next();
            Ciudad ciu= new Ciudad(nombre, true, true);

            boolean exito = ciudades.eliminarVertice(ciu);
            
            if(exito){
                
                txt = "Ciudad: " +nombre+", eliminada correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);
                
            }else{
                
                System.out.println("ERROR. No ha sido posible eliminar la ciudad.");
                
            }
        }        
    }
      //2 - ABM RUTAS
    public static void abmRutas(Grafo ciudades){
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMRutas();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarRuta(ciudades);
                    break;              
                case 2:
                    eliminarRuta(ciudades);
                    break;
                case 3:
                    modificarRuta(ciudades);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        }while(eleccion != 0);
        
        
    }
    
    public static void menuABMRutas(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ruta.");  
        System.out.println("2. Eliminar una ruta.");
        System.out.println("3. Modificar una ruta.");
        
    }
    public static void agregarRuta(Grafo ciudades){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        String origen, destino;
        int  eleccion;
        double distancia;
        System.out.println("ALTA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el nombre de la ciudad origen");
            origen = sc.next();

            System.out.println("Ingrese el nombre de la ciudad destino");
            destino = sc.next();

            System.out.println("Ingrese la distancia entre las ciudades");
            distancia = sc.nextDouble();
            
            if(!ciudades.existeArco(origen, destino)){
                
                if(ciudades.insertarArco(origen, destino, distancia)){

                    txt = "Se agrego la ruta desde: "+origen+ " a: " +destino+ " correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);
                    
                }else{
                    System.out.println("ERROR. No se pudo agregar la ruta.");
                }
            }else{
                System.out.println("ERROR. Ya existe la ruta.");
            }
        }    
    }
    //BAJAS
    public static void eliminarRuta(Grafo ciudades){
        
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String origen, destino;
        String txt = "";
        System.out.println("BAJA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el nombre de la ciudad origen");
            origen = sc.next();

            System.out.println("Ingrese el nombre de la ciudad destino");
            destino = sc.next();

            boolean exito = ciudades.eliminarArco(origen, destino);

            if(exito){
                
                txt = "Se borro el tramo de ruta entre las ciudades: " +origen+ " y: " +destino+ " correctamente."; 
                System.out.println(txt);
                registrarMovimiento(txt);
                
            }else{
                System.out.println("ERROR. No se pudo eliminar la ruta.");
            }
        } 
    }
    //MODIFICACIONES
    public static void modificarRuta(Grafo ciudades){
        
        Scanner sc = new Scanner(System.in);
        String origen, destino;
        int  eleccion;
        double distancia;
        String txt = "";
        
        System.out.println("MODIFICACION DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            
            System.out.println("De la ruta puede modificarse la distancia.");

            System.out.println("Ingrese el nombre de la ciudad origen");
            origen = sc.next();

            System.out.println("Ingrese el C.P de la ciudad destino");
            destino = sc.next();

            boolean exito = ciudades.eliminarArco(origen, destino);

            if(exito){

                System.out.println("Ingrese la distancia MODIFICADA.");
                distancia = sc.nextDouble();
                ciudades.insertarArco(origen, destino, distancia);
                
                txt = "Se modifico la distancia de la ruta " +origen+ " a " +destino+ " y ahora es: " +distancia+ "km.";
                System.out.println(txt);
                registrarMovimiento(txt);

            }else{

                System.out.println("ERROR. No se pudo modificar la ruta.");

            }
        }    
    }
     //3 - ABM CLIENTES
    
    public static void abmEquipos(ArbolAVL equipos){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMEquipos();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarEquipo(equipos);
                    break;              
                case 2:
                    eliminarEquipo(equipos);
                    break;
                case 3:
                    modificarEquipo(equipos);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuABMEquipos(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar un Equipo.");  
        System.out.println("2. Eliminar un Equipo.");
        System.out.println("3. Modificar un Equipo.");
        
    
    }
    
    //ALTAS 
    private static void agregarEquipo(ArbolAVL equipos){
        Scanner sc = new Scanner(System.in);
        int nroID, eleccion;
        String nombre, nombreDT;
        char grupo;
        int puntosGanados,GAF,GEC;
        String txt = "";
        
        System.out.println("ALTA DE UN EQUIPO");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el Nombre.");
            nombre = sc.next();
            System.out.println("Ingrese el Nombre del Director Tecnico.");
            nombreDT = sc.next();
            System.out.println("Ingrese al Grupo que pertenece.");
            grupo = sc.next().charAt(0);
            System.out.println("Ingrese el total de puntos ganados.");
            puntosGanados = sc.nextInt();
            System.out.println("Ingrese el total de Goles a Favor.");
            GAF = sc.nextInt();
            System.out.println("Ingrese el total de Goles en Contra");
            GEC = sc.nextInt();

            Equipo eq1= new Equipo(nombre, nombreDT, grupo,puntosGanados, GAF, GEC);

            boolean exito = equipos.insertar(nombre, eq1);

            if(exito){
                
                txt = "Se agrego el equipo: " +eq1.toString()+ " correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            }else{
                System.out.println("ERROR. No ha sido posible agregar un Equipo.");

            }
        }    
    }
    private static void eliminarEquipo(ArbolAVL equipos){
        
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombre;
        System.out.println("BAJA DE UN EQUIPO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el nombre del Equipo que desea dar de BAJA.");

            nombre = sc.next();

            boolean exito = equipos.eliminar(nombre);
            
            if(exito){
                
                txt = "Equipo: " +nombre+", eliminado correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);   
            }else{
                
                System.out.println("ERROR. No ha sido posible eliminar el Equipo.");
                
            }
        }        
    }
        //MODIFICACIONES
    private static void modificarEquipo(ArbolAVL equipos){
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombreVerificar;
        String nomDT;
        char gru;
        int PG,GAF,GEC;
        System.out.println("MODIFICACION DE UN EQUIPO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println("Ingrese el nombre del Equipo que desea modificar");
            nombreVerificar = sc.next();
            //Equipo comparar= new Equipo(nombreVerificar, "", '-',0, 0, 0);
            Equipo unEquipo = equipos.obtenerDato(nombreVerificar);

            if(unEquipo != null){

                System.out.println("El equipo que va a modificar es: \n" +unEquipo.toString()); 
                System.out.println("1. CONTINUAR.");
                System.out.println("2. CANCELAR.");
                eleccion = sc.nextInt();

                if(eleccion == 1){
                    System.out.println("Ingrese el nombre del DT a modificar");
                    nomDT= sc.next();
                    System.out.println("---");
                    System.out.println("Ingrese el Grupo.");
                    gru = sc.next().charAt(0);
                    System.out.println("Ingrese el total de puntos ganados actualizados.");
                    PG = sc.nextInt();
                    System.out.println("Ingrese el total de Goles a Favor actualizados.");
                    GAF = sc.nextInt();
                    System.out.println("Ingrese el total de Goles en Contra actualizados");
                    GEC = sc.nextInt();

                    unEquipo.setNombreDT(nomDT);
                    unEquipo.setGrupo(gru);
                    unEquipo.setPuntosGanados(PG);
                    unEquipo.setGolesAFavor(GAF);
                    unEquipo.setGolesEnContra(GEC);
                    txt = "Los datos del equipo: " +unEquipo.toString()+ " han sido modificados.";
                    System.out.println(txt);
                    registrarMovimiento(txt);
                }else{
                    System.out.println("OPERACION CANCELADA.");
                }
            }else{
                System.out.println("El nombre es INCORRECTO.");
            }
        }
    } 
    
    //HashMapeoAMuchos partidos
     public static void abmPartidos(HashMapeoAMuchos partidos){
        
    Scanner sc = new Scanner(System.in);
        int eleccion;
        
        do{
            
            menuABMPartidos();
            eleccion = sc.nextInt();
            switch(eleccion){
                case 0:
                    break;
                case 1:
                    agregarPartido(partidos);
                    break;              
                case 2:
                    eliminarPartido(partidos);
                    break;
                case 3:
                    modificarPartido(partidos);
                    break;  
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        }while(eleccion != 0);
    
    }
    
    private static void menuABMPartidos(){
        
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar un Partido.");  
        System.out.println("2. Eliminar un Partido.");
        System.out.println("3. Modificar un Partido.");
        
    
    }
    
    
    //ALTAS 
    public static void agregarPartido(HashMapeoAMuchos partidos){
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion,G1,G2;
        String eq1,eq2,insta,ciu,esta;
        
        System.out.println("ALTA DE UN PARTIDO.");
        
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq2=sc.next();
            
            System.out.println("Ingrese la instancia en la que se jugo el Partido.");
            insta=sc.next();
            
            System.out.println("Ingrese la Ciudad en la que se jugo el Partido.");
            ciu=sc.next();
            
            System.out.println("Ingrese el Estadio en el que se jugo el Partido.");
            esta=sc.next();
            
            System.out.println("Ingrese los Goles del Equipo 1.");
            G1=sc.nextInt();
            
            System.out.println("Ingrese los Goles del Equipo 2.");
            G2=sc.nextInt();
            
            ClavePartido clave= new ClavePartido(eq1, eq2);
            Partido parti= new Partido(insta, ciu, esta, G1, G2);
            boolean exito= partidos.insertar(clave, parti);
            if(exito){
                
                txt = "Partido entre: " +clave.toString()+" con los datos: "+parti.toString()+", insertado correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);   
            }else{
                System.out.println("ERROR. No ha sido posible insertar el partido.");
            }
        }
    }
     //BAJAS
    public static void eliminarPartido(HashMapeoAMuchos partidos){
        Scanner sc = new Scanner(System.in);
        int origen, destino, nroSolicitud, eleccion;
        String txt = "";
        String eq1,eq2,insta;
        
        System.out.println("BAJA DE UN PARTIDO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            eq2=sc.next();

            ClavePartido unaClave = new ClavePartido(eq1, eq2);

            String msg = partidos.toStringConClave(unaClave);

            if(msg.equals("ERROR.")){

                System.out.println("La clave ingresada no existe.");

            }else{

                System.out.println("Seleccione la instancia del Partido que desea eliminar.");
                System.out.println(msg);

                insta = sc.next();
                Partido aBorrar = new Partido(insta, "", "", 0, 0);

                boolean res = partidos.eliminar(unaClave, aBorrar);


                if(res){
                    txt = "Partido entre: " +eq1+ " y destino: " +eq2+ " en la instancia: "+insta+" ELIMINADO correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                }else{

                    System.out.println("ERROR. El Partido no existe.");

                }
            }
        }
    }
     
    //MODIFICACIONES   
    public static void modificarPartido(HashMapeoAMuchos partidos){
        Scanner sc = new Scanner(System.in);
         int eleccion,G1,G2;
        String eq1,eq2,insta,ciu,esta;
        String txt = "";
        
        System.out.println("MODIFICACION DE UNA SOLICITUD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
        
            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            eq2 = sc.next();

            ClavePartido unaClave = new ClavePartido(eq1, eq2);

            String msg = partidos.toStringConClave(unaClave);

            if(msg.equals("ERROR.")){

                System.out.println("La clave ingresada no existe.");

            }else{

                System.out.println("Seleccione la instancia del Partido que desea modificar");
                System.out.println(msg);
                insta = sc.next();
                Partido partidito= new Partido(insta, "", "", 0, 0);

                Partido partidoAModificar = partidos.obtenerDato(unaClave, partidito);

                if(partidoAModificar != null){

                    System.out.println("-Datos de la solicitud a modificar: \n" +partidoAModificar.toString());
                    System.out.println("1. CONTINUAR.");
                    System.out.println("2. CANCELAR.");
                    eleccion = sc.nextInt();

                    if(eleccion == 1){

                        System.out.println("Ingreso de datos para modificar la solicitud:");

                        System.out.println("Ingrese la ciudad.");
                        ciu = sc.next();

                        System.out.println("Ingrese el estadio.");
                        esta = sc.next();

                        System.out.println("Ingrese los goles del Equipo 1.");
                        G1 = sc.nextInt();
                        
                        System.out.println("Ingrese los goles del Equipo 2.");
                        G2 = sc.nextInt();

                        partidoAModificar.setCiudad(ciu);
                        partidoAModificar.setEstadio(esta);
                        partidoAModificar.setGolesEq1(G1);
                        partidoAModificar.setGolesEq2(G2);
                        
                        txt = "Partido con: " +eq1+ " y  " +eq2+ " MODIFICADO correctamente.";
                        System.out.println(txt);
                        registrarMovimiento(txt);
                    }else{
                        System.out.println("OPERACION CANCELADA.");
                    }    
                }else{
                    System.out.println("ERROR. No se pudo modificar el Partido.");
                }  
            }
        }
    }
    // 9 - MOSTRAR SISTEMA
    public static void mostrarSistema(ArbolAVL equipos, HashMapeoAMuchos partidos,Grafo ciudades){
        Scanner sc = new Scanner(System.in);
        int eleccion;
        
        System.out.println("MOSTRAR EL SISTEMA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        
        if(eleccion == 1){
            System.out.println(mostrarSistemaAux(equipos, partidos,ciudades));
        }
    }
    
    private static String mostrarSistemaAux(ArbolAVL equipos, HashMapeoAMuchos partidos,Grafo ciudades){
        String txt = "AVL DE EQUIPOS: \n\n" +
                equipos.toString() + "\n\n" +
                "HASH MAPEO A MUCHOS DE PARTIDOS: \n\n" +
                partidos.toString() + "\n\n" +
                "GRAFO ETIQUETADO DE CIUDADES: \n\n" +
                ciudades.toString();
        return txt;

    }
    
    //ESCRIBE EN ARCHIVO DE SALIDA
    public static void registrarMovimiento(String datosLinea) {
        try {
            FileWriter archivoRegistros = new FileWriter("SELECCIONE RUTA", true);
            try (BufferedWriter bufEscritura = new BufferedWriter(archivoRegistros)) {
                bufEscritura.write(datosLinea);
                bufEscritura.newLine();
            }
        } catch (IOException e) {
            System.out.println("El registro no ha podido hacerse");
        }
    }
    
    

}
