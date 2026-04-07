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
public class Buscaminas {

    private static Scanner teclado = new Scanner(System.in);

    private static int[][] matriz; // Esta es la matriz "Real" en donde se encuentran las bombas
    private static int[][] matriz_tablero; // Uso una matriz secundaria en donde dibujar las banderas, la cual se muestra por terminal
    private static int bombastotales = 0;
    private static int contador_casillas_correctas;
    private static int fila = -1;
    private static int columna = -1;
    private static boolean bomba_explotada = false;
    public static int partidas_ganadas = 0;

    /**
     * Funcion para iniciar el juego donde se van llamando al resto de las
     * funciones segun convenga
     */
    public static void juego() {
        System.out.println("El buscaminas");
        System.out.println("");
        dificultad();
        bombasAleatorias();
        tablero();
        contador_casillas_correctas = 0; // reinicio el contador, para el caso que se quiera iniciar una nueva partida, una vez jugada ya una
        bomba_explotada = false; // reinicio la bomba explotada, para el caso que se quiera iniciar una nueva partida, una vez jugada ya una
        boolean victoria = (matriz.length * matriz.length - bombastotales == contador_casillas_correctas);
        while (!victoria && !bomba_explotada) { // Compruebo si ha ganado o ha explotado una bomba

            System.out.println("1. Desvelar casilla");
            System.out.println("2. Poner Bandera");
            System.out.println("3. Salir juego");
            System.out.println("");
            int respuesta = teclado.nextInt();

            if (respuesta == 1) {
                desvelarCasillas();
            } else if (respuesta == 2) {
                ponerBandera();
            } else if (respuesta == 3) {
                System.out.println("Buscaminas Finalizado");
                System.out.println();
                break;
            }else{
                System.err.println("Debes de introducir un numero de las opciones");
            }
            victoria = (matriz.length * matriz.length - bombastotales == contador_casillas_correctas); // actualizo el valor de victoria
            if (bomba_explotada) {
                mostrarTableroResuelto();
            } else if (victoria) {
                partidas_ganadas++;
                
                mostrarTableroResuelto();
                System.out.println("Has ganado, todas las casillas resueltas");
                System.out.println("");
            } else {
                tablero();
            }

        }

    }

    /**
     * Funcion para colocar banderas
     */
    private static void ponerBandera() {
        fila = -1;
        columna = -1; // Reinicio el valor de las variables 
        while (fila < 0 || fila > matriz_tablero.length || columna < 0 || columna > matriz_tablero.length) { // Comprobar que la fila y columna son la validas en la matriz, y asi no peta 
            System.out.println("Introduce la fila de la casilla que quieres poner una bandera");
            fila = teclado.nextInt();
            System.out.println("Introduce la columna de la casilla que quieres poner una bandera");
            columna = teclado.nextInt();
            if (fila < 0 || fila > matriz_tablero.length || columna < 0 || columna > matriz_tablero.length) {
                System.out.println("Tanto la fila como la columna deben de ser validas dentro de la matriz");
                System.out.println("Como minimo tiene que tener valor 0 y como maximo " + matriz_tablero.length);
                System.out.println("");
            }else if (matriz_tablero[fila][columna] == 1){ // No permito poner una bandera encima de una casilla desvelada
                System.out.println("En esta posicion no puedes poner una bandera porque ya ha sido desvelada");
                fila = -1;
                columna = -1; // Reinicio el valor de las variables para no salir del while
            }
        }

        matriz_tablero[fila][columna] = 2; // Pongo una bandera = ?
    }

    /**
     * Funcion para desvelar casillas
     */
    private static void desvelarCasillas() {
        fila = -1;
        columna = -1; // Reinicio el valor de las variables 
        while (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz.length) { // Comprobar que la fila y columna son la validas en la matriz, y asi no peta 
            System.out.println("Introduce la fila de la casilla que quieres desvelar");
            fila = teclado.nextInt();
            System.out.println("Introduce la columna de la casilla que quieres desvelar");
            columna = teclado.nextInt();
            if (fila < 0 || fila >= matriz.length || columna < 0 || columna >= matriz.length) {
                System.out.println("Tanto la fila como la columna deben de ser validas dentro de la matriz");
                System.out.println("Como minimo tiene que tener valor 0 y como maximo " + (matriz.length-1));
                System.out.println("");
            }
        }
        if (matriz[fila][columna] == 5) {
            System.out.println("En esta posicion habia una bomba !!!! ");
            System.out.println("Has perdido");
            System.out.println("");
            //MostrarTableroResuelto();
            bomba_explotada = true;
        }
        matriz_tablero[fila][columna] = 1;
        contador_casillas_correctas++;
    }

    /**
     * Funcion para pedir la dificultad y devolver un numero para gestionar la
     * dificultad en el resto de funciones
     *
     * @return
     */
    private static void dificultad() {
        int dificultad = 0;
        while (dificultad > 3 || dificultad < 1) { // Pedir la dificultad hasta que sea 1, 2 o 3 
            System.out.println("Elige la dificultad, introduciendo un numero del 1 al 3:");
            System.out.println("");
            System.out.println("1. Facil Tablero 5X5 (5 bombas)");
            System.out.println("2. Medio Tablero 10X10 (20 bombas)");
            System.out.println("3. Dificil Tablero 20X20 (100 bombas)");
            dificultad = teclado.nextInt();
            if (dificultad > 3 || dificultad < 1) {
                System.err.println("Debes elegir una dificultad valida");
            }
        }
        // Redimensionamos la matriz según la elección
        switch (dificultad) {
            case 1 -> {
                matriz = new int[5][5];
                matriz_tablero = new int[5][5];
                bombastotales = 5;
            }
            case 2 -> {
                matriz = new int[10][10];
                matriz_tablero = new int[10][10];
                bombastotales = 20;
            }
            case 3 -> {
                matriz = new int[20][20];
                matriz_tablero = new int[20][20];
                bombastotales = 100;
            }
        }

    }

    /**
     * Funcion para poner las bombas aleatoriamente en el tablero
     *
     * @param dificultad
     */
    private static void bombasAleatorias() {

        for (int bombas = bombastotales; bombas > 0; bombas--) {
            double random_1 = (matriz.length * (Math.random())); //Creo los numeros aleatorios segun la logitud de la matriz 
            int fila_aleatoria = (int) random_1;
            double random_2 = (matriz.length * (Math.random())); //Creo los numeros aleatorios segun la logitud de la matriz 
            int columna_aleatoria = (int) random_2;
            if (matriz[fila_aleatoria][columna_aleatoria] == 0) { // Para comprobar que en esa posicion esta vacia y no hay una bomba ya
                matriz[fila_aleatoria][columna_aleatoria] = 5;
            } else {
                bombas++; // Se suma uno a bombas ya que se ha descontado una y no se ha puesto la bomba realmente
            }
        }

    }

    /**
     * Funcion para mostar el tablero con un for each de manera visual al
     * usuario
     *
     *
     * @param matriz
     */
    private static void tablero() {
        System.out.print("\t");
        for (int i = 0; i < matriz_tablero.length; i++) {  // Para mostrar los numeros de las columnas arriba del tablero para que sea más visual 
            System.out.print(i + " ");             // En la dificultad dificil al haber numeros de dos cifras se descuadra un poco pero aun asi creo que suma mucho por ello lo dejo     
        }
        System.out.println("\n");

        for (int filas = 0; filas < matriz_tablero.length; filas++) {
            System.out.print(filas + "\t");  // Para mostrar los numeros de las filas a la izquierda para que sea más visual
            for (int columnas = 0; columnas < matriz_tablero[filas].length; columnas++) {
                switch (matriz_tablero[filas][columnas]) {
                    case 1 ->
                        radardeBombas(matriz, filas, columnas);  // La casilla que ha sido marcada con 1 se comprueba las bombas del arlededor y se imprime
                    case 2 ->
                        System.out.print("? ");
                    case 3 ->
                        System.out.print("* ");
                    default -> //En caso de no haber ninguna información sobre esa posición se pone # de casilla sin descubrir
                        System.out.print("# ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Funcion para segun la posicion (fila y columna) de una matriz decir
     * cuantas bombas tiene en las 8 casillas de alrededor
     *
     * @param matriz
     * @param fila
     * @param columna
     * @return
     */
    private static void radardeBombas(int matriz[][], int fila, int columna) {
        int contador_bombas = 0;
        for (int i = fila - 1; i <= fila + 1; i++) { // Con este doble for comprueba 9 posiciones, empezando una posicion superior izquierda resepcto a la posición
            for (int j = columna - 1; j <= columna + 1; j++) { // Y termina en una posicion inferior derecha respecto a la posicion, tambien mira la posicion pero si aqui hubiese bomba ya deberia de haber terminado el minijuego
                if (i >= 0 && i < matriz.length && j >= 0 && j < matriz.length) {  // Para los casos que la posición sea un borde que si no peta por tamaño de matriz
                    if (matriz[i][j] == 5) {
                        contador_bombas++;
                    }
                }
            }
        }
        // Imprimo el valor de las bombas que tiene alrededor para ese valor
        System.out.print(contador_bombas + " ");

    }

    /**
     * Funcion para mostrar el tablero resuelto si el jugador se topa con una
     * bomba
     */
    private static void mostrarTableroResuelto() {

        for (int filas = 0; filas < matriz_tablero.length; filas++) {
            for (int columnas = 0; columnas < matriz_tablero[filas].length; columnas++) {
                if (matriz_tablero[filas][columnas] == 0) {
                    matriz_tablero[filas][columnas] = 1; //Desvelo todas las casillas que estaban ocultas
                }
                if (matriz[filas][columnas] == 5) {
                    matriz_tablero[filas][columnas] = 3; //Las convierto las minas en numero 3 para que se muestren en el tablero como *
                }
            }
        }
        tablero();
    }
}
