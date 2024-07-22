package copaAmerica;

import GrafoCiudades.Grafo;
import HashPartidos.HashMapeoAMuchos;
import TDAS.Ciudad;
import TDAS.ClavePartido;
import TDAS.Partido;
import TDAS.Equipo;
import TDAS.EquipoPorGoles;
import arbolAVL.ArbolAVL;
import Lineales.Lista;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

// @author Ulises
public class CopaAmerica2024 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArbolAVL equipos = new ArbolAVL();
        HashMapeoAMuchos partidos = new HashMapeoAMuchos();
        Grafo ciudades = new Grafo();

        String datos = "C:/Users/ulise/OneDrive/Desktop/BASE-DE-DATOS.txt";
        int eleccion = 0;

        System.out.println("Presione '1' para realizar la carga inicial");
        eleccion = sc.nextInt();
        if (eleccion == 1) {
            cargarDatos(datos, equipos, partidos, ciudades);
            String txt1 = "Se ha realizado la carga de datos correctamente.";
            System.out.println(txt1);
            txt1 += "ESTADO INICIAL DE LA BASE DE DATOS:";
            txt1 += mostrarSistemaAux(equipos, partidos, ciudades);
            registrarMovimiento(txt1);
            ejecutaMenu(equipos, partidos, ciudades);
        }

        //Escritura de datos para el SELECCIONAR RUTA
        String txt2 = "ESTADO FINAL DE LA BDD: \n";
        txt2 += mostrarSistemaAux(equipos, partidos, ciudades);
        registrarMovimiento(txt2);

        System.out.println("FIN.");
    }

    //Carga de Datos
    public static void cargarDatos(String datos, ArbolAVL equipos, HashMapeoAMuchos partidos, Grafo ciudades) {
        try ( BufferedReader bufferLectura = new BufferedReader(new FileReader(datos))) {
            String linea;
            //while encargado de cada linea de lectura del txt, recorrera todas las lineas del txt
            while ((linea = bufferLectura.readLine()) != null) {
                //guardamos en un arreglo de String la linea completa, separando cada " ; " 
                String[] datosLinea = linea.split(";");

                if (datosLinea[0].equals("C")) {
                    cargarCiudad(datosLinea, ciudades);
                }
                if (datosLinea[0].equals("R")) {
                    cargarRuta(datosLinea, ciudades);
                }
                if (datosLinea[0].equals("E")) {
                    cargarEquipo(datosLinea, equipos);
                }
                if (datosLinea[0].equals("P")) {
                    cargarPartido(datosLinea, partidos, equipos);
                }

            }

        } catch (IOException ex) {
            //En el caso que no se lea o no escriba correcto, salta el catch.
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }
    //CARGA DE DATOS DESDE EL TXT

    private static void cargarCiudad(String[] datosCiudad, Grafo ciudades) {
        String nombre = datosCiudad[1];
        boolean alojamiento = ("TRUE").equals(datosCiudad[2].toUpperCase());
        boolean esSede = ("TRUE").equals(datosCiudad[3].toUpperCase());
        Ciudad unaCiudad = new Ciudad(nombre, alojamiento, esSede);
        ciudades.insertarVertice(unaCiudad);
    }

    private static void cargarRuta(String[] datosRuta, Grafo ciudades) {
        String ori = datosRuta[1];
        String des = datosRuta[2];
        double distancia = Double.parseDouble(datosRuta[3]);
        Ciudad orig = new Ciudad(ori, true, true);
        Ciudad dest = new Ciudad(des, true, true);
        //Uso constructor para el obtenerDato, el cual devuelve las ciudades ya construidas
        //No le puedo pasar un string, ya que recibe Object, por ende le paso el Objeto creado
        //y el obtenerDato se encargara de devolverme lo que necesito ocupando el equals de Ciudad
        Ciudad origen = (Ciudad) ciudades.obtenerDato(orig);
        Ciudad destino = (Ciudad) ciudades.obtenerDato(dest);
        ciudades.insertarArco(origen, destino, distancia);
    }

    private static void cargarEquipo(String[] datosEquipo, ArbolAVL equipos) {
        String nombre = datosEquipo[1];
        String nombreDT = datosEquipo[2];
        char grupo = datosEquipo[3].charAt(0);
        int puntosGanados = Integer.parseInt(datosEquipo[4]);
        int golesAFavor = Integer.parseInt(datosEquipo[5]);
        int golesEnContra = Integer.parseInt(datosEquipo[6]);
        Equipo eq1 = new Equipo(nombre, nombreDT, grupo, puntosGanados, golesAFavor, golesEnContra);
        equipos.insertar(eq1);
    }

    private static void cargarPartido(String[] datosPartido, HashMapeoAMuchos partidos, ArbolAVL equipos) {
        String eq1 = datosPartido[1];
        String eq2 = datosPartido[2];
        String insta = datosPartido[3];
        String ciu = datosPartido[4];
        String esta = datosPartido[5];
        int G1 = Integer.parseInt(datosPartido[6]);
        int G2 = Integer.parseInt(datosPartido[7]);

        ClavePartido unaClave = new ClavePartido(eq1, eq2);
        Partido unPartido = new Partido(insta, ciu, esta, G1, G2);
        partidos.insertar(unaClave, unPartido);

        Equipo nuevo = new Equipo(eq1, "", 'A', 0, 0, 0);
        Equipo equipo1 = (Equipo) equipos.obtenerDato(nuevo);

        Equipo nuevo2 = new Equipo(eq2, "", 'A', 0, 0, 0);
        Equipo equipo2 = (Equipo) equipos.obtenerDato(nuevo2);

        calcularPuntosYGoles(equipo1, equipo2, G1, G2, 1);
    }

    //MENU
    public static void ejecutaMenu(ArbolAVL equipos, HashMapeoAMuchos partidos, Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;

        registrarMovimiento("MODIFICACIONES EN EL TRANSCURSO DE LA EJECUCION: ");

        do {
            imprimeMenu();
            eleccion = sc.nextInt();

            switch (eleccion) {

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
                    abmPartidos(partidos, ciudades, equipos);
                    break;
                case 5:
                    consultaEquipos(equipos);
                    break;
                case 6:
                    consultaPartidos(partidos);
                    break;
                case 7:
                    consultaRutas(ciudades);
                    break;
                case 8:
                    mostrarSistema(equipos, partidos, ciudades);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        } while (eleccion != 0);

    }

    public static void imprimeMenu() {

        System.out.println("---MENU---");

        System.out.println("0. SALIR.");

        System.out.println("1. ALTAS-BAJAS-MODIFICACIONES de CIUDADES.");

        System.out.println("2. ALTAS-BAJAS-MODIFICACIONES de RED AEREA DE RUTAS.");

        System.out.println("3. ALTAS-BAJAS-MODIFICACIONES de EQUIPOS.");

        System.out.println("4. ALTAS-BAJAS-MODIFICACIONES de PARTIDOS.");

        System.out.println("5. CONSULTA SOBRE EQUIPOS.");

        System.out.println("6. CONSULTA SOBRE PARTIDOS.");

        System.out.println("7. CONSULTA SOBRE RUTAS AEREAS ENTRE CIUDADES.");

        System.out.println("8. MOSTRAR SISTEMA.");

    }
    //1 - ABM CIUDADES

    public static void abmCiudades(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuABMCiudades();
            eleccion = sc.nextInt();
            switch (eleccion) {
                case 0:
                    break;
                case 1:
                    agregarCiudad(ciudades);
                    break;
                case 2:
                    eliminarCiudad(ciudades);
                    break;
                case 3:
                    modificarCiudad(ciudades);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida.");
            }

        } while (eleccion != 0);

    }

    private static void menuABMCiudades() {
        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ciudad.");
        System.out.println("2. Eliminar una ciudad.");
        System.out.println("3. Modificar una ciudad.");
    }

    private static void agregarCiudad(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombre;
        boolean aloDispo, esSede;
        System.out.println("ALTA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre de la ciudad.");
            nombre = sc.next();
            System.out.println("Ingrese si tiene alojamiento disponible.");
            aloDispo = sc.nextBoolean();
            System.out.println("Ingrese si es Sede o no");
            esSede = sc.nextBoolean();

            Ciudad unaCiudad = new Ciudad(nombre, aloDispo, esSede);

            boolean exito = ciudades.insertarVertice(unaCiudad);

            if (exito) {

                txt = ("Se agrego la ciudad: " + unaCiudad.toString() + " correctamente.");
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {

                System.out.println("ERROR. No ha sido posible agregar la ciudad.");

            }
        }
    }

    private static void eliminarCiudad(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        String txt = "";
        String nombre;
        int eleccion;
        System.out.println("BAJA DE UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre de la ciudad que desea dar de BAJA.");
            nombre = sc.next();
            Ciudad ciu = new Ciudad(nombre, true, true);
            Ciudad nueva = (Ciudad) ciudades.obtenerDato(ciu);
            boolean exito = ciudades.eliminarVertice(nueva);

            if (exito) {

                txt = "Ciudad: " + nombre + ", eliminada correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {

                System.out.println("ERROR. No ha sido posible eliminar la ciudad.");

            }
        }
    }

    private static void modificarCiudad(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        String txt = "";
        String nombre;
        boolean alo, esSede;
        int eleccion;
        System.out.println("MODIFICA UNA CIUDAD.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre de la ciudad que desea modificar.");
            nombre = sc.next();
            System.out.println("Ingrese si la ciudad posee alojamiento disponible para modificar.");
            alo = sc.nextBoolean();
            System.out.println("Ingrese si la ciudad ahora es Sede para modificar.");
            esSede = sc.nextBoolean();
            Ciudad ciu = new Ciudad(nombre, true, true);
            Ciudad nueva = (Ciudad) ciudades.obtenerDato(ciu);
            boolean exito = false;
            if (nueva != null) {
                exito = true;
                nueva.setAlmaceDispo(alo);
                nueva.setAlmaceDispo(esSede);
            }

            if (exito) {

                txt = "Ciudad: " + nombre + ", modificada correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {

                System.out.println("ERROR. No ha sido posible modificar la ciudad.");

            }
        }
    }
    //2 - ABM RUTAS

    public static void abmRutas(Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuABMRutas();
            eleccion = sc.nextInt();
            switch (eleccion) {
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

        } while (eleccion != 0);

    }

    public static void menuABMRutas() {

        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar una ruta.");
        System.out.println("2. Eliminar una ruta.");
        System.out.println("3. Modificar una ruta.");

    }//
    //
    //x

    public static void agregarRuta(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        String txt = "";
        String ori, des;
        int eleccion;
        double distancia;
        System.out.println("ALTA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre de la ciudad origen");
            ori = sc.next();

            System.out.println("Ingrese el nombre de la ciudad destino");
            des = sc.next();

            System.out.println("Ingrese la distancia entre las ciudades");
            distancia = sc.nextDouble();
            Ciudad orig = new Ciudad(ori, true, true);
            Ciudad dest = new Ciudad(des, true, true);
            Ciudad origen = (Ciudad) ciudades.obtenerDato(orig);
            Ciudad destino = (Ciudad) ciudades.obtenerDato(dest);

            if (!ciudades.existeArco(origen, destino)) {

                if (ciudades.insertarArco(origen, destino, distancia)) {

                    txt = "Se agrego la ruta desde: " + origen + " a: " + destino + " correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                } else {
                    System.out.println("ERROR. No se pudo agregar la ruta.");
                }
            } else {
                System.out.println("ERROR. Ya existe la ruta.");
            }
        }
    }

    //BAJAS
    public static void eliminarRuta(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        int eleccion;
        String ori, des;
        String txt = "";
        System.out.println("BAJA DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre de la ciudad origen");
            ori = sc.next();

            System.out.println("Ingrese el nombre de la ciudad destino");
            des = sc.next();

            Ciudad orig = new Ciudad(ori, true, true);
            Ciudad dest = new Ciudad(des, true, true);
            Ciudad origen = (Ciudad) ciudades.obtenerDato(orig);
            Ciudad destino = (Ciudad) ciudades.obtenerDato(dest);

            boolean exito = ciudades.eliminarArco(origen, destino);
            if ((origen == null && destino != null) || (origen != null && destino == null)) {
                System.out.println("No existe ruta entre: " + origen + " y " + destino);
            }
            if (exito) {
                txt = "Se borro el tramo de ruta entre  " + origen + " y: " + destino + " correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {
                System.out.println("ERROR. No se pudo eliminar la ruta.");
            }
        }
    }

    //MODIFICACIONES
    public static void modificarRuta(Grafo ciudades) {

        Scanner sc = new Scanner(System.in);
        String ori, des;
        int eleccion;
        double distancia;
        String txt = "";

        System.out.println("MODIFICACION DE UNA RUTA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("De la ruta puede modificarse la distancia unicamente.");

            System.out.println("Ingrese el nombre de la ciudad origen");
            ori = sc.next();

            System.out.println("Ingrese el nombre de la ciudad destino");
            des = sc.next();
            Ciudad orig = new Ciudad(ori, true, true);
            Ciudad dest = new Ciudad(des, true, true);
            Ciudad origen = (Ciudad) ciudades.obtenerDato(orig);
            Ciudad destino = (Ciudad) ciudades.obtenerDato(dest);

            boolean exito = ((origen != null) && (destino != null));

            if (exito) {

                System.out.println("Ingrese la distancia MODIFICADA.");
                distancia = sc.nextDouble();

                txt = "Se modifico la distancia de la ruta " + origen + " a " + destino + " y ahora es: " + distancia + "km.";
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {

                System.out.println("ERROR. No se pudo modificar la ruta.");

            }
        }
    }
    //3 - ABM CLIENTES

    public static void abmEquipos(ArbolAVL equipos) {

        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuABMEquipos();
            eleccion = sc.nextInt();
            switch (eleccion) {
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

        } while (eleccion != 0);

    }

    private static void menuABMEquipos() {

        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar un Equipo.");
        System.out.println("2. Eliminar un Equipo.");
        System.out.println("3. Modificar un Equipo.");

    }

    //ALTAS 
    private static void agregarEquipo(ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        int nroID, eleccion;
        String nombre, nombreDT;
        char grupo;
        int puntosGanados, GAF, GEC;
        String txt = "";

        System.out.println("ALTA DE UN EQUIPO");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
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

            Equipo eq1 = new Equipo(nombre, nombreDT, grupo, puntosGanados, GAF, GEC);

            boolean exito = equipos.insertar(eq1);

            if (exito) {

                txt = "Se agrego el equipo: " + eq1.toString() + " correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);

            } else {
                System.out.println("ERROR. No ha sido posible agregar un Equipo.");

            }
        }
    }

    private static void eliminarEquipo(ArbolAVL equipos) {

        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombre;
        System.out.println("BAJA DE UN EQUIPO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre del Equipo que desea dar de BAJA.");

            nombre = sc.next();
            Equipo nuevo = new Equipo(nombre, "", 'A', 0, 0, 0);
            Equipo eq = (Equipo) equipos.obtenerDato(nuevo);
            boolean exito = equipos.eliminar(eq);

            if (exito) {

                txt = "Equipo: " + nombre + ", eliminado correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);
            } else {

                System.out.println("ERROR. No ha sido posible eliminar el Equipo.");

            }
        }
    }
    //MODIFICACIONES

    private static void modificarEquipo(ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion;
        String nombreVerificar;
        String nomDT;
        System.out.println("MODIFICACION DE UN EQUIPO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese el nombre del Equipo que desea modificar");
            nombreVerificar = sc.next();
            //Equipo comparar= new Equipo(nombreVerificar, "", '-',0, 0, 0);
            Equipo nuevo = new Equipo(nombreVerificar, "", 'A', 0, 0, 0);
            Equipo eq = (Equipo) equipos.obtenerDato(nuevo);

            if (eq != null) {

                System.out.println("El equipo que va a modificar es: \n" + eq.toString());
                System.out.println("1. CONTINUAR.");
                System.out.println("2. CANCELAR.");
                eleccion = sc.nextInt();

                if (eleccion == 1) {
                    System.out.println("Del Equipo Seleccionado solo se podra modificar el nombre de su DT");
                    System.out.println("Ingrese el nombre del DT a modificar");
                    nomDT = sc.next();

                    eq.setNombreDT(nomDT);
                    txt = "Los datos del " + eq.toString() + " han sido modificados.";
                    System.out.println(txt);
                    registrarMovimiento(txt);
                } else {
                    System.out.println("OPERACION CANCELADA.");
                }
            } else {
                System.out.println("El nombre es INCORRECTO.");
            }
        }
    }

    //HashMapeoAMuchos partidos
    public static void abmPartidos(HashMapeoAMuchos partidos, Grafo ciudades, ArbolAVL equipos) {

        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuABMPartidos();
            eleccion = sc.nextInt();
            switch (eleccion) {
                case 0:
                    break;
                case 1:
                    agregarPartido(partidos, ciudades, equipos);
                    break;
                case 2:
                    eliminarPartido(partidos, equipos);
                    break;
                case 3:
                    modificarPartido(partidos, ciudades);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        } while (eleccion != 0);

    }

    private static void menuABMPartidos() {

        System.out.println("0. ATRAS.");
        System.out.println("1. Agregar un Partido.");
        System.out.println("2. Eliminar un Partido.");
        System.out.println("3. Modificar un Partido.");

    }

    //ALTAS 
    public static void agregarPartido(HashMapeoAMuchos partidos, Grafo ciudades, ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        String txt = "";
        int eleccion, G1, G2;
        String eq1, eq2, insta, ciu, esta;

        System.out.println("ALTA DE UN PARTIDO.");

        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq2 = sc.next();

            System.out.println("Ingrese la instancia en la que se jugo el Partido.");
            insta = sc.next();

            System.out.println("Ingrese la Ciudad en la que se jugo el Partido.");
            ciu = sc.next();

            String ciuCorrecta = verificarCiudad(ciu, ciudades);

            System.out.println("Ingrese el Estadio en el que se jugo el Partido.");
            esta = sc.next();

            System.out.println("Ingrese los Goles del Equipo 1.");
            G1 = sc.nextInt();

            System.out.println("Ingrese los Goles del Equipo 2.");
            G2 = sc.nextInt();

            ClavePartido clave = new ClavePartido(eq1, eq2);

            Partido parti = new Partido(insta, ciuCorrecta, esta, G1, G2);
            boolean exito = partidos.insertar(clave, parti);
            if (exito) {
                Equipo nuevo = new Equipo(eq1, "", 'A', 0, 0, 0);
                Equipo equipo1 = (Equipo) equipos.obtenerDato(nuevo);

                Equipo nuevo2 = new Equipo(eq2, "", 'A', 0, 0, 0);
                Equipo equipo2 = (Equipo) equipos.obtenerDato(nuevo2);
                calcularPuntosYGoles(equipo1, equipo2, G1, G2, 1);

                txt = "Partido entre: " + clave.toString() + " con los datos: " + parti.toString() + ", insertado correctamente.";
                System.out.println(txt);
                registrarMovimiento(txt);
            } else {
                System.out.println("ERROR. No ha sido posible insertar el partido.");
            }
        }
    }

    public static String verificarCiudad(String city, Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        Ciudad ciudad = new Ciudad(city, true, true);
        Ciudad nueva = (Ciudad) ciudades.obtenerDato(ciudad);
        String retorno = null;
        boolean esSede = false;
        if (nueva != null) {
            esSede = nueva.esSede();
        }
        String ciu;
        boolean rta = false;
        if (nueva != null && esSede) {
            retorno = city;
        } else {
            do {
                System.out.println("La ciudad que usted ingreso es incorrecta, o bien, no es sede. ");
                System.out.println("Ingrese la Ciudad en la que se jugo el Partido.");
                ciu = sc.next();
                ciudad = new Ciudad(ciu, true, true);
                nueva = (Ciudad) ciudades.obtenerDato(ciudad);
            } while (nueva == null);
            esSede = nueva.esSede();
            if (esSede) {
                retorno = ciu;
            }
        }
        return retorno;
    }
    //BAJAS

    public static void eliminarPartido(HashMapeoAMuchos partidos, ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String txt = "";
        String eq1, eq2, insta;

        System.out.println("BAJA DE UN PARTIDO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            eq2 = sc.next();

            ClavePartido claveBusqueda = new ClavePartido(eq1, eq2);
            ClavePartido clave = (ClavePartido) partidos.buscarClave(claveBusqueda);

            String msg = partidos.toStringConClave(clave);

            if (msg.equals("ERROR.")) {

                System.out.println("La clave ingresada no existe.");

            } else {

                System.out.println("Seleccione la instancia del Partido que desea eliminar.");
                System.out.println(msg);

                insta = sc.next();
                Partido partidito = new Partido(insta, "", "", 0, 0);
                Partido partidoAEliminar = (Partido) partidos.obtenerDato(clave, partidito);

                Equipo nuevo = new Equipo(eq1, "", 'A', 0, 0, 0);
                Equipo equipo1 = (Equipo) equipos.obtenerDato(nuevo);

                Equipo nuevo2 = new Equipo(eq2, "", 'A', 0, 0, 0);
                Equipo equipo2 = (Equipo) equipos.obtenerDato(nuevo2);

                //Como se elimino un Partido, entonces elimino los puntos que ese partido habia repartido
                calcularPuntosYGoles(equipo1, equipo2, partidoAEliminar.getGolesEq1(), partidoAEliminar.getGolesEq2(), 2);

                boolean res = partidos.eliminar(clave, partidoAEliminar);
                if (res) {
                    txt = "Partido entre: " + eq1 + " y destino: " + eq2 + " en la instancia: " + insta + " ELIMINADO correctamente.";
                    System.out.println(txt);
                    registrarMovimiento(txt);
                    txt = "Ademas, se modificaron correspondientemente los datos de ambos Equipos de los cuales se elimino el Partido.";
                    System.out.println(txt);
                    registrarMovimiento(txt);

                } else {

                    System.out.println("ERROR. El Partido no existe.");

                }
            }
        }
    }

    public static void calcularPuntosYGoles(Equipo equipo1, Equipo equipo2, int G1, int G2, int restarOsumar) {
        if (restarOsumar == 1) {
            equipo1.setGolesAFavor(equipo1.getGolesAFavor() + G1);
            equipo1.setGolesEnContra(equipo1.getGolesEnContra() + G2);
            equipo2.setGolesAFavor(equipo2.getGolesAFavor() + G2);
            equipo2.setGolesEnContra(equipo2.getGolesEnContra() + G1);
            if (G1 > G2) {
                equipo1.setPuntosGanados(equipo1.getPuntosGanados() + 3);
            } else if (G1 == G2) {
                equipo1.setPuntosGanados(equipo1.getPuntosGanados() + 1);
                equipo2.setPuntosGanados(equipo2.getPuntosGanados() + 1);
            } else {
                equipo2.setPuntosGanados(equipo2.getPuntosGanados() + 3);
            }
        }
        if (restarOsumar == 2) {
            equipo1.setGolesAFavor(equipo1.getGolesAFavor() - G1);
            equipo1.setGolesEnContra(equipo1.getGolesEnContra() - G2);
            equipo2.setGolesAFavor(equipo2.getGolesAFavor() - G2);
            equipo2.setGolesEnContra(equipo2.getGolesEnContra() - G1);
            if (G1 > G2) {
                equipo1.setPuntosGanados(equipo1.getPuntosGanados() - 3);
            } else if (G1 == G2) {
                equipo1.setPuntosGanados(equipo1.getPuntosGanados() - 1);
                equipo2.setPuntosGanados(equipo2.getPuntosGanados() - 1);
            } else {
                equipo2.setPuntosGanados(equipo2.getPuntosGanados() - 3);
            }
        }
    }

    //MODIFICACIONES   
    public static void modificarPartido(HashMapeoAMuchos partidos, Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String eq1, eq2, insta, ciu, esta;
        String txt = "";

        System.out.println("MODIFICACION DE UN PARTIDO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            eq2 = sc.next();

            ClavePartido claveBusqueda = new ClavePartido(eq1, eq2);
            ClavePartido clave = (ClavePartido) partidos.buscarClave(claveBusqueda);

            String msg = partidos.toStringConClave(clave);

            if (msg.equals("ERROR.")) {

                System.out.println("La clave ingresada no existe.");

            } else {

                System.out.println("Seleccione la instancia del Partido que desea modificar");
                System.out.println(msg);
                insta = sc.next();
                Partido partidito = new Partido(insta, "", "", 0, 0);

                Partido partidoAModificar = (Partido) partidos.obtenerDato(clave, partidito);

                if (partidoAModificar != null) {

                    System.out.println("-Datos del Partido a modificar: \n" + partidoAModificar.toString());
                    System.out.println("1. CONTINUAR.");
                    System.out.println("2. CANCELAR.");
                    eleccion = sc.nextInt();

                    if (eleccion == 1) {
                        System.out.println("Solo se podra modificar la ciudad, y el estadio");
                        System.out.println("Ingreso de datos para modificar el partido:");

                        System.out.println("Ingrese la ciudad.");
                        ciu = sc.next();
                        String ciuCorrecta = verificarCiudad(ciu, ciudades);

                        System.out.println("Ingrese el estadio.");
                        esta = sc.next();

                        partidoAModificar.setCiudad(ciuCorrecta);
                        partidoAModificar.setEstadio(esta);

                        txt = "Partido con: " + eq1 + " y  " + eq2 + " MODIFICADO correctamente.";
                        System.out.println(txt);
                        registrarMovimiento(txt);
                    } else {
                        System.out.println("OPERACION CANCELADA.");
                    }
                } else {
                    System.out.println("ERROR. No se pudo modificar el Partido.");
                }
            }
        }
    }

    //5-CONSULTA SOBRE EQUIPOS
    public static void consultaEquipos(ArbolAVL equipos) {

        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuConsultaEquipo();
            eleccion = sc.nextInt();
            switch (eleccion) {
                case 0:
                    break;
                case 1:
                    mostrarEquipo(equipos);
                    break;
                case 2:
                    rangoEquipos(equipos);
                    break;
                case 3:
                    mostrarEquiposOrdenadosPorGoles(equipos);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        } while (eleccion != 0);
    }

    public static void menuConsultaEquipo() {

        System.out.println("0. ATRAS.");
        System.out.println("1. Mostrar Datos de un Equipo.");
        System.out.println("2. Mostrar todos los equipos cuyo nombre este "
                + "alfabeticamente en el rango entre 2 Equipos.");
        System.out.println("3. Mostrar todos los equipos Ordenados mediante la cantidad de Goles.");
    }

    public static void mostrarEquipo(ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String nombre;
        System.out.println("CONSULTAR DATOS DE UN EQUIPO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre del Equipo que desea consultar.");
            nombre = sc.next();

            Equipo nuevo = new Equipo(nombre, "", 'A', 0, 0, 0);
            Equipo eq = (Equipo) equipos.obtenerDato(nuevo);
            if (eq != null) {

                System.out.println("Datos del Equipo: \n" + eq.toStringCompleto());

            } else {

                System.out.println("El equipo no se encuentra cargada en el sistema.");

            }
        }
    }

    public static void rangoEquipos(ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String nombre1, nombre2;
        System.out.println("MOSTRAR RANGO ENTRE 2 EQUIPOS.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        if (eleccion == 1) {
            System.out.println("Ingrese el nombre del Equipo1.");
            nombre1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            nombre2 = sc.next();

            Equipo nuevo1 = new Equipo(nombre1, "", 'A', 0, 0, 0);
            Equipo eq1 = (Equipo) equipos.obtenerDato(nuevo1);

            Equipo nuevo2 = new Equipo(nombre2, "", 'A', 0, 0, 0);
            Equipo eq2 = (Equipo) equipos.obtenerDato(nuevo2);

            if (eq1 != null && eq2 != null) {
                Lista equiposRango = equipos.listarRango(eq1, eq2);

                String txt = "Los equipos se han listado en el rango indicado";
                System.out.println(equiposRango.toString());
                System.out.println(txt);
                registrarMovimiento(txt);
            } else {
                System.out.println("Algunos de los Equipos ingresados no existen");
            }
        }
    }

    public static void mostrarEquiposOrdenadosPorGoles(ArbolAVL equipos) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        System.out.println("MOSTRAR LOS EQUIPOS ORDENADOS POR GOLES.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();
        if (eleccion == 1) {
            ArbolAVL equiposPorGoles = new ArbolAVL();
            Lista lista = new Lista();
            lista = equipos.listarInOrden();
            while (!lista.esVacia()) {
                EquipoPorGoles nuevo = new EquipoPorGoles(lista.recuperar(1));
                equiposPorGoles.insertar(nuevo);
                lista.eliminar(1);
            }
            //System.out.println(equiposPorGoles.toString());
            lista = equiposPorGoles.listarInOrden();
            System.out.println(lista.toString());
            lista.vaciar();
            equiposPorGoles.vaciar();
        }
    }

    //6-CONSULTA SOBRE PARTIDOS
    public static void consultaPartidos(HashMapeoAMuchos partidos) {

        Scanner sc = new Scanner(System.in);
        int eleccion;

        do {

            menuConsultaPartido();
            eleccion = sc.nextInt();
            switch(eleccion) {
                case 0:
                    break;
                case 1:
                    mostrarPartido(partidos);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
            }

        } while (eleccion != 0);
    }

    public static void menuConsultaPartido() {
        System.out.println("0. ATRAS.");
        System.out.println("1. Mostrar Datos de un Partido.");
    }

    public static void mostrarPartido(HashMapeoAMuchos partidos) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String eq1, eq2;
        System.out.println("CONSULTAR DATOS DE UN PARTIDO.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {

            System.out.println("Ingrese el nombre del Equipo 1.");
            eq1 = sc.next();

            System.out.println("Ingrese el nombre del Equipo 2.");
            eq2 = sc.next();

            ClavePartido unaClave = new ClavePartido(eq1, eq2);
            System.out.println("Con la Clave Ingresada se registran los siguientes Partidos: ");
            String msg = partidos.toStringConClave(unaClave);

            if (msg.equals("ERROR.")) {

                System.out.println("La clave ingresada no existe.");

            } else {
                System.out.println(msg);

            }
        }
    }

    public static void consultaRutas(Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        do {
            menuConsultarRutas();
            eleccion = sc.nextInt();

            switch (eleccion) {

                case 0:
                    break;
                case 1:
                    caminoMenosCiudades(ciudades);
                    break;
                case 2:
                    //caminoMenosKm(ciudades);
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");

            }

        } while (eleccion != 0);

    }

    private static void menuConsultarRutas() {

        System.out.println("0. ATRAS.");
        System.out.println("1. Obtener el camino que llegue de A a B que pase por menos ciudades.");
        System.out.println("2. Obtener el camino que llegue de A a B de menor distancia en kilómetros.");

    }

    //CAMINO QUE PASA POR MENOS CIUDADES
    public static void caminoMenosCiudades(Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        String ori, des;

        System.out.println("OBTENER CAMINO QUE PASA POR MENOS CIUDADES.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println("Ingrese la ciudad de origen");
            ori = sc.next();
            System.out.println("Ingrese la ciudad de destino");
            des = sc.next();
            Ciudad orig = new Ciudad(ori, true, true);
            Ciudad dest = new Ciudad(des, true, true);
            Ciudad origen = (Ciudad) ciudades.obtenerDato(orig);
            Ciudad destino = (Ciudad) ciudades.obtenerDato(dest);

            Lista camino = ciudades.caminoMasCorto(origen, destino);

            if (!camino.esVacia()) {
                System.out.println("El camino que pasa por menos ciudades es:");
                System.out.println(camino.toString());
            } else {
                System.out.println("ERROR. No existe camino");
            }

        }
    }

    // 8 - MOSTRAR SISTEMA
    public static void mostrarSistema(ArbolAVL equipos, HashMapeoAMuchos partidos, Grafo ciudades) {
        Scanner sc = new Scanner(System.in);
        int eleccion;

        System.out.println("MOSTRAR EL SISTEMA.");
        System.out.println("1. CONTINUAR.");
        System.out.println("2. CANCELAR.");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            System.out.println(mostrarSistemaAux(equipos, partidos, ciudades));
        }
    }

    private static String mostrarSistemaAux(ArbolAVL equipos, HashMapeoAMuchos partidos, Grafo ciudades) {
        String txt = "AVL DE EQUIPOS: \n\n"
                + equipos.toString() + "\n\n"
                + "HASH MAPEO A MUCHOS DE PARTIDOS: \n\n"
                + partidos.toString() + "\n\n"
                + "GRAFO ETIQUETADO DE CIUDADES: \n\n"
                + ciudades.toString();
        return txt;

    }

    //ESCRIBE EN ARCHIVO DE SALIDA
    public static void registrarMovimiento(String datosLinea) {
        try {
            FileWriter archivoRegistros = new FileWriter("SELECCIONE RUTA", true);
            try ( BufferedWriter bufEscritura = new BufferedWriter(archivoRegistros)) {
                bufEscritura.write(datosLinea);
                bufEscritura.newLine();
            }
        } catch (IOException e) {
            System.out.println("El registro no ha podido hacerse");
        }
    }

}
