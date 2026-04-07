/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NavarroLuis_04;

import java.util.Scanner;

/**
 *
 * @author isard
 */
public class Cuatroenraya {

    private static int matriz[][] = new int[6][7];
    private static Scanner teclado = new Scanner(System.in);
    private static double aleatorio = Math.random(); // Al ser aleatorio como variable publica y estatica al iniciar un nuevo juego siempre empieza el mismo jugador
    private static int contador = 0;
    private static int jugador = (int) ((aleatorio * 2) + 1);
    private static int jugador_aleatorio_1 = 0;
    private static int jugador_aleatorio_2 = 0;

    /**
     * Funcion para iniciar el juego donde se hace el llamamiento a todas las
     * funciones
     */
    public static void juego() {

        // Es necesario limpiar el tablero,el contador  al iniciar una nueva partida
        matriz = new int[6][7];
        contador = 0;
        // Es necesario llamar nuevamente a aleatorio y a jugador para que al empezar una partida se vuelva a hacer aleatoriamente la eleccion de quien empieza
        double aleatorio = Math.random();
        jugador = (int) ((aleatorio * 2) + 1);

        boolean victoria = false; // booleano para saber si ha habido una victoria por parte de algun jugador

        System.out.println("Introduce el nombre del primer jugador: ");
        String nombre_1 = teclado.next();
        System.out.println("Introduce el nombre del segundo jugador: ");
        String nombre_2 = teclado.next();
        if (jugador == 1) {
            System.out.println("Empieza jugando " + nombre_1);
            jugador_aleatorio_1 = 1; // Para saber quien empieza jugando
            jugador_aleatorio_2 = 2;
        } else {
            System.out.println("Empieza jugando " + nombre_2);
            jugador_aleatorio_1 = 2; // Para saber quien empieza jugando
            jugador_aleatorio_2 = 1;
        }

        while (contador < 42) { // Si el contador llega a 42 se ha llenado el tablero 

            mostarTablero();
            if (introducirFicha(nombre_1, nombre_2)) {
                contador++;
                // Solo comprobamos si se ha puesto la ficha correctamente.
                if (comprobarVertical()) {
                    anunciarVictoria(nombre_1, nombre_2, "vertical");
                    victoria = true;
                    break;
                } else if (comprobarHorizontal()) {
                    anunciarVictoria(nombre_1, nombre_2, "horizontal");
                    victoria = true;
                    break;
                } else if (comprobarDiagonal()) {
                    anunciarVictoria(nombre_1, nombre_2, "diagonal");
                    victoria = true;
                    break;
                }

                if (jugador == jugador_aleatorio_1) { //Cambio de jugador en cada turno, solamente si se ha introducido correctamente la ficha
                    jugador = jugador_aleatorio_2;
                } else {
                    jugador = jugador_aleatorio_1;
                }
            }

        }

        if (contador == 42 && !victoria) { // La variable victoria solo se usa en este caso para en el caso de haber un jugador que gane con la ultima ficha que no muestre un mensaje de victoria y luego el de empate
            System.out.println("Se ha completado el tablero (Empate) ");
            System.out.println("");

        }
        System.out.println("El minijuego ha finalizado");
        System.out.println("");

    }

    /**
     * Funcion para mostrar el tablero con un for each
     */
    private static void mostarTablero() {
        for (int i = 0; i <= matriz.length; i++) { // //Para mostrar los numeros de las columnas arriba del tablero para que sea más visual , si tambien mostramos filas habia que añadir un \t al principio
            System.out.print(i + " ");
        }
        System.out.println("\n");
        
        for (int[] matriz1 : matriz) {
            //System.out.print(filas + "\t"); Para mostrar las filas para que sea más visual para introducir las fichas, pero al solo tener que selecionar las columnas no es necesario
            for (int columnas = 0; columnas < matriz1.length; columnas++) {
                switch (matriz1[columnas]) {
                    case 1 -> // El numero 1 para el jugador A, que es el jugador 1
                        System.out.print("A ");
                    case 2 -> // El numero 2 para el jugador B, que es el jugador 2
                        System.out.print("B ");
                    default -> //En caso de no haber ninguna ficha en ese hueco se dibuja un O 
                        System.out.print("O ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Funcion para Intrdoucir una ficha de forma vertical segun el jugador que
     * le toque
     *
     * @return
     */
    private static boolean introducirFicha(String nombre_1, String nombre_2) {

        if (jugador == 1) { // Mostrar de quien es el turno segun el jugador y que ficha le corresponde
            System.out.println("Turno de " + nombre_1 + " (A)");
        } else if (jugador == 2) {
            System.out.println("Turno de " + nombre_2 + " (B)");
        }

        int columna_ficha = -1;
        while (columna_ficha < 0 || columna_ficha > 6) { // Pedir la ficha hasta que este entre 0 y 6 para que no pete
            System.out.println("Introduce la columna en la cual quieres hacer caer la ficha (del 0 al 6)");
            columna_ficha = teclado.nextInt();
            if (columna_ficha < 0 || columna_ficha > 6) {
                System.err.println("Has introducido un numero invalido, vuelvelo a intentar ");
            }
        }
        for (int filas = 5; filas >= 0; filas--) { // Empezando desde abajo vamos subiendo hasta completar la columna
            if (matriz[filas][columna_ficha] == 0) {
                matriz[filas][columna_ficha] = jugador;
                return true;
            } else if (filas == 0 && (matriz[0][columna_ficha] == 1 || matriz[0][columna_ficha] == 2)) {
                System.out.println("Esta columna ya esta completa");
                System.out.println("");
                return false;
            }

        }
        return false;

    }

    /**
     * Funcion para comprobar la horizontal segun el jugador
     *
     * @return
     */
    private static boolean comprobarHorizontal() {

        for (int fila = 0; fila < 6; fila++) { // comprobar cuatro en raya horizontal segun el jugador
            int contador_cuatro = 0;
            for (int columna = 0; columna < 7; columna++) {
                if (matriz[fila][columna] == jugador) {
                    contador_cuatro++;
                    if (contador_cuatro == 4) {
                        return true;
                    }
                } else {
                    contador_cuatro = 0;
                }
            }

        }
        return false;

    }

    /**
     * Funcion para comprobar la vertical segun el jugador
     *
     * @return
     */
    private static boolean comprobarVertical() {

        for (int columna = 0; columna < 7; columna++) { // comprobar cuatro en raya horizontal segun el jugador
            int contador_cuatro = 0;
            for (int fila = 0; fila < 6; fila++) {
                if (matriz[fila][columna] == jugador) {
                    contador_cuatro++;
                    if (contador_cuatro == 4) {
                        return true;
                    }
                } else {
                    contador_cuatro = 0;
                }
            }

        }
        return false;

    }

    /**
     * Funcion para comprobar todos los 4 en raya posibles en diagonal
     *
     * NOTA : en principio habia creado una funcion mucha más dificil a esta ,
     * la cual cumplia su funcion pero eran 150 lineas dificiles de testear su
     * funcioniamineto al 100 %, asi que cuando lo estaba testeando decidi
     * retomar la funcion desde 0 y sacar la manera más facil de comprobar
     * diagonales, ya que un buen codigo tiene que ser eficaz pero tambien
     * entendible y a poder ser acortarlo, basicamente lo que he hecho.
     *
     * @return
     */
    private static boolean comprobarDiagonal() {
        // Compruebo las diagonales descendentes, de arriba izquierda a abajo derecha
        // empezamos en las filas 0-2 y columnas 0-3 para que quepan 4 fichas, es decir comprobamos el 4 en raya en las posiciones en las posiciones iniciales que podria empezar un 4 en raya diagonal
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 4; col++) {
                if (matriz[fila][col] == jugador
                        && matriz[fila + 1][col + 1] == jugador
                        && matriz[fila + 2][col + 2] == jugador
                        && matriz[fila + 3][col + 3] == jugador) {
                    return true;
                }
            }
        }

        // Compruebo todas las diagonales ascendentes, de abajo izquierda a arriba derecha
        // empezamos en las filas 3-5 y columnas 0-3 , es decir comprobamos el 4 en raya en las posiciones iniciales que podria empezar un 4 en raya diagonal
        for (int fila = 3; fila < 6; fila++) {
            for (int col = 0; col < 4; col++) {
                if (matriz[fila][col] == jugador
                        && matriz[fila - 1][col + 1] == jugador
                        && matriz[fila - 2][col + 2] == jugador
                        && matriz[fila - 3][col + 3] == jugador) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Funcion para anunciar la victoria del jugador que toque segun el tipo de
     * cuatro en raya que hayan hecho Esta funcion la he creado para reducir
     * codigo y no repetir tantos system.out.println
     *
     * @param nombre1
     * @param nombre2
     * @param tipo
     */
    private static void anunciarVictoria(String nombre1, String nombre2, String tipo) {
        if (jugador == 1) {
            System.out.println(nombre1 + " ha hecho cuatro en raya en " + tipo);
        } else {
            System.out.println(nombre2 + " ha hecho cuatro en raya en " + tipo);
        }
        System.out.println("");
        mostarTablero();
    }
}
