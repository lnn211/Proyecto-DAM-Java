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
public class Hundirlaflota {

    /*
    Leyenda de los codigos internos
    5 = portaviones
    6 = Submarino 1
    7 = Submarino 2
    8 = Lanchas (Al solo tener una posición puedo usar el mismo codigo para todas)
     */
    private static Scanner teclado = new Scanner(System.in);
    private static int matriz[][] = new int[10][10]; // Una matriz para donde estan los barcos del usuario
    private static int matriz_cpu[][] = new int[10][10]; // Una matriz para donde estan los barcos de la cpu
    private static int portaaviones_jugador[][] = new int[4][3]; // Matrices para almacenar datos de posicion y de hundimiento
    private static int submarino_1_jugador[][] = new int[3][3];
    private static int submarino_2_jugador[][] = new int[3][3];
    private static int portaaviones_cpu[][] = new int[4][3];
    private static int submarino_1_cpu[][] = new int[3][3];
    private static int submarino_2_cpu[][] = new int[3][3];
    private static int contador_barcos_hundidos_del_jugador = 0;
    private static int contador_barcos_hundidos_de_la_cpu = 0;
    private static int fila = -1;
    private static int columna = -1;

    public static int partidas_ganadas = 0;

    public static void juego() {

        tablero();
        portaaviones();
        colocarCpu(5);
        tablero();
        for (int i = 6; i < 8; i++) { // 6 es el submarino 1 y 7 es el submarino 2
            submarino(i);
            colocarCpu(i);
            tablero();
        }
        for (int i = 0; i < 3; i++) {
            lanchas();
            colocarCpu(8);
            tablero();
        }
        while (true) {
            //Disparo del jugador
            contador_barcos_hundidos_de_la_cpu = disparo(true, matriz_cpu, portaaviones_cpu, submarino_1_cpu, submarino_2_cpu, contador_barcos_hundidos_de_la_cpu);

            //Disparo de la cpu
            contador_barcos_hundidos_del_jugador = disparo(false, matriz, portaaviones_jugador, submarino_1_jugador, submarino_2_jugador, contador_barcos_hundidos_del_jugador);

            tablero();
            if (contador_barcos_hundidos_del_jugador == 6) {
                System.out.println("Has perdido, tu flota ha sido hundida.");
                break;
            }
            if (contador_barcos_hundidos_de_la_cpu == 6) {
                System.out.println("Flota del enemigo destruida, has ganado. ");
                partidas_ganadas++;
                break;
            }
        }
    }

    /**
     * Funcion para que la cpu coloque según el tipo de barco
     *
     * @param tipo
     */
    private static void colocarCpu(int tipo) {
        int distancia = 0;
        if (tipo == 5) {
            distancia = 3; //Pongo 3 para el portaaviones porque es la distancia que tiene que haber de la casilla inicial a la fina, aunque en realidad tiene 4 casillas
        } else if (tipo == 6 || tipo == 7) {
            distancia = 2; //Pongo 2 para el submarino porque es la distancia que tiene que haber de la casilla inicial a la fina, aunque en realidad tiene 3 casillas
        }
        boolean colocar = false;
        while (!colocar) {
            double random_1 = Math.random();
            int fila_aleatoria = (int) (random_1 * 10);
            double random_2 = Math.random();
            int columna_aleatoria = (int) (random_2 * 10);
            if (tipo == 8) {
                if (matriz_cpu[fila_aleatoria][columna_aleatoria] == 0) {
                    matriz_cpu[fila_aleatoria][columna_aleatoria] = 8;
                    colocar = true; // Marcamos como colocado para salir del while
                    break; // Rompemos este ciclo de comprobación
                } else {
                    continue; // Si está ocupado ese hueco del tablero, saltamos a la sigueinte iteración para que genere nuevos randoms
                }
            }
            double random_3 = Math.random();
            int horizontal_vertical = (int) (random_3 * 2);
            if (horizontal_vertical == 0) { // Si es 0 el barco se colocar en vertical, es decir las filas son iguales
                int fila_inicial = fila_aleatoria;
                int fila_final = fila_aleatoria;
                int columna_inicial = columna_aleatoria;
                int columna_final = columna_aleatoria + distancia;
                if (comprobarEspacioBarco(fila_inicial, columna_inicial, fila_final, columna_final, tipo)) {
                    colocar = colocarBarcos(fila_inicial, columna_inicial, fila_final, columna_final, tipo, matriz_cpu);
                }
            } else {  // Si es 1 el barco se colocar en horizontal, es decir las columnas son iguales
                int fila_inicial = fila_aleatoria;
                int fila_final = fila_aleatoria + distancia;
                int columna_inicial = columna_aleatoria;
                int columna_final = columna_aleatoria;

                if (comprobarEspacioBarco(fila_inicial, columna_inicial, fila_final, columna_final, tipo)) {
                    colocar = colocarBarcos(fila_inicial, columna_inicial, fila_final, columna_final, tipo, matriz_cpu);
                }
            }
        }
    }

    /**
     * Funcion para colocar el Portaviones
     */
    private static boolean portaaviones() {
        boolean colocar = false;
        while (!colocar) { // pedir las posicion de inicio y final del portaviones
            System.out.println("Introduce la posicion inical donde quieres colocar el portaaviones");
            System.out.println();
            System.out.println("Introduce la fila");
            int fila_inicial = teclado.nextInt();
            System.out.println("Introduce la columna");
            int columna_inicial = teclado.nextInt();
            System.out.println("Introduce la posicion final donde quieres colocar el portaaviones");
            System.out.println();
            System.out.println("Introduce la fila");
            int fila_final = teclado.nextInt();
            System.out.println("Introduce la columna");
            int columna_final = teclado.nextInt();
            if (comprobarEspacioBarco(fila_inicial, columna_inicial, fila_final, columna_final, 5)) { // el portaviones encaja

                colocar = colocarBarcos(fila_inicial, columna_inicial, fila_final, columna_final, 5, matriz);
                if (colocar == false) {
                    System.err.println("No hay espacio disponible en las posiciones marcadas porque hay barcos ya, cambia la posicion");
                } else {
                    System.out.println("Has introducido correctamente el portaaviones: ");
                }
            } else {
                System.err.println("No has introducido correctamente el portaaviones, tiene que estar dentro del tablero (10x10) y con una distancia de 3 ( 4 casillas ), en horizontal o vertical");
            }

        }
        return colocar;

    }

    /**
     * Funcion para colocar el submarino
     */
    private static void submarino(int tipo) {
        boolean colocar = false;
        while (!colocar) { // pedir las posicion de inicio y final del submarino
            System.out.println("Introduce la posicion inical donde quieres colocar el submarino");
            System.out.println();
            System.out.println("Introduce la fila");
            int fila_inicial = teclado.nextInt();
            System.out.println("Introduce la columna");
            int columna_inicial = teclado.nextInt();
            System.out.println("Introduce la posicion final donde quieres colocar el submarino");
            System.out.println();
            System.out.println("Introduce la fila");
            int fila_final = teclado.nextInt();
            System.out.println("Introduce la columna");
            int columna_final = teclado.nextInt();
            if (comprobarEspacioBarco(fila_inicial, columna_inicial, fila_final, columna_final, tipo)) { // El submarino encaja si es true

                colocar = colocarBarcos(fila_inicial, columna_inicial, fila_final, columna_final, tipo, matriz);
                if (colocar == false) {
                    System.err.println("No hay espacio disponible en las posiciones marcadas porque hay barcos ya, cambia la posicion");
                } else {
                    System.out.println("Has introducido correctamente el submarino: ");
                }

            } else {
                System.err.println("No has introducido correctamente el submarino, tiene que estar dentro del tablero (10x10) y con una distancia de 2 ( 3 casillas ), en horizontal o vertical");
            }

        }

    }

    /**
     * Funcion para colocar las lanchas
     */
    private static void lanchas() {
        boolean colocada = false;

        while (!colocada) {
            System.out.println("Introduce la posicion donde quieres colocar la lancha");
            System.out.println("Introduce la fila");
            int fila = teclado.nextInt();
            System.out.println("Introduce la columna");
            int columna = teclado.nextInt();

            if (fila < 0 || columna < 0 || fila > 9 || columna > 9) {
                System.err.println("Debe de meter una posición valida en la matriz entre 0 y 9");
            } else if (matriz[fila][columna] != 0) {
                System.err.println("No hay espacio disponible en las posiciones marcadas porque hay barcos ya, cambia la posicion");
            } else {
                System.out.println("Has introducido correctamente la lancha: ");
                matriz[fila][columna] = 8;
                colocada = true; // Solo salimos del bucle si se coloca con éxito
            }
        }

    }

    /**
     * Funcion para meter internamente en el tablero los barcos segun una
     * posicion inicial y una final Devuelve con un boolean si hay espacio
     * disponible para colocar el barco o no
     *
     * @param fila_inicio
     * @param columna_inicio
     * @param fila_final
     * @param columna_final
     * @param tipo
     * @param matriz[][]
     * @return
     */
    private static boolean colocarBarcos(int fila_inicio, int columna_inicio, int fila_final, int columna_final, int tipo, int matriz[][]) {
        // Confirmo de que fila_inicio < fila_final y columna_inicio < columna_final , y si no las invierto para facilitar la logica y reducir el codigo
        if (fila_inicio > fila_final) {
            int temp = fila_inicio;
            fila_inicio = fila_final;
            fila_final = temp;
        }

        if (columna_inicio > columna_final) {
            int temp = columna_inicio;
            columna_inicio = columna_final;
            columna_final = temp;
        }

        // Compruebo que todas las casillas donde coloco el barco estan libres( 0 ).
        for (int fila = fila_inicio; fila <= fila_final; fila++) {
            for (int columna = columna_inicio; columna <= columna_final; columna++) {
                if (matriz[fila][columna] != 0) {
                    return false; // Si hay alguna casilla ocupada se devuleve false, no se puede colocar
                }
            }
        }

        // Al estar todas las casillas libres coloco el barco
        for (int fila = fila_inicio; fila <= fila_final; fila++) {
            for (int columna = columna_inicio; columna <= columna_final; columna++) {
                matriz[fila][columna] = tipo; // Coloco el barco segun el tipo
            }
        }

        return true; // Devuelvo verdadero si se ha colocado correctamente
    }

    /**
     * Funcion para mostrar el tablero tanto el del jugador como el de la cpu en
     * un mismo espacio
     */
    private static void tablero() {
        System.out.println();
        System.out.print("\t");
        System.out.println("Tablero Jugador \t Tablero Cpu");
        System.out.println("");
        System.out.print("\t");
        for (int i = 0; i < matriz.length; i++) {  // Para mostrar los numeros de las columnas arriba del tablero para que sea más visual 
            System.out.print(i + " ");
        }
        System.out.print("\t");
        for (int i = 0; i < matriz_cpu.length; i++) {  // Para mostrar los numeros de las columnas arriba del tablero de la cpu para que sea más visual 
            System.out.print(i + " ");
        }
        System.out.println("\n");
        for (int filas = 0; filas < matriz.length; filas++) {
            System.out.print(filas + "\t");  // Para mostrar los numeros de las filas a la izquierda para que sea más visual
            for (int columnas = 0; columnas < matriz[filas].length; columnas++) {
                switch (matriz[filas][columnas]) {
                    case 1 -> // El numero 1 para posiciones en las que hay un barco y ha sido tocado
                        System.out.print("X ");
                    case 2 -> // El numero 2 para posiciones en las que hay un barco y ha sido hundido
                        System.out.print("H ");
                    case 3 -> // El numero 3 es para posiciones en las cuales la IA ha bombardeado de forma aleatoria
                        System.out.print("O ");
                    case 5 -> // El numero 5 es para decir donde el jugador ha puesto un portaaviones
                        System.out.print("# ");
                    case 6 -> // El numero 6 es para decir donde el jugador ha puesto un barco
                        System.out.print("# ");
                    case 7 -> // El numero 5 es para decir donde el jugador ha puesto un barco
                        System.out.print("# ");
                    case 8 -> // El numero 5 es para decir donde el jugador ha puesto un barco
                        System.out.print("# ");
                    default -> //En caso de no haber ninguna dato se trata del mar
                        System.out.print(". ");
                }
            }
            System.out.print(" |  ");

            for (int columnas = 0; columnas < matriz_cpu.length; columnas++) {
                switch (matriz_cpu[filas][columnas]) {
                    case 1 -> // El numero 1 para posiciones en las que hay un barco y ha sido tocado
                        System.out.print("X ");
                    case 2 -> // El numero 2 para posiciones en las que hay un barco y ha sido hundido
                        System.out.print("H ");
                    case 3 -> // El numero 3 es para posiciones en las cuales las hemos bombardeado ya
                        System.out.print("O ");
                    default -> //En caso de no haber ninguna dato se trata del mar
                        System.out.print(". ");
                }

            }
            System.out.print("\t" + filas);  // Para mostrar los numeros de las filas a la derecha para que sea más visual

            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Funcion para disparar en el tablero según el jugador o cpu, y devuelve el
     * contador de barcos hundidos, si es que se ha hundido alguno
     *
     * @param esTurnoJugador
     * @param matriz_objetivo
     * @param portaaviones
     * @param submarino_1
     * @param submarino_2
     * @param contador_barcos_hundidos
     * @return
     */
    private static int disparo(boolean esTurnoJugador, int[][] matriz_objetivo, int[][] portaaviones, int[][] submarino_1, int[][] submarino_2, int contador_barcos_hundidos) {

        boolean sitio_disparado = true;
        while (sitio_disparado) {
            if (esTurnoJugador) {
                introducirFilaColumna();
            } else {
                sitio_disparado = true;
                double random_1 = Math.random();
                fila = (int) (random_1 * 10);
                double random_2 = Math.random();
                columna = (int) (random_2 * 10);
            }

            sitio_disparado = false;
            switch (matriz_objetivo[fila][columna]) {
                case 1 -> {
                    if (esTurnoJugador) {
                        System.out.println("En esta posicion ya has disparado y habia un barco que aun sigue en pie, pon otra posicion");

                        introducirFilaColumna();
                    }
                    sitio_disparado = true;
                }
                case 2 -> {
                    if (esTurnoJugador) {
                        System.out.println("En esta posicion ya has disparado y habia un barco que ya ha sido hundido, pon otra posicion");
                        introducirFilaColumna();
                    }
                    sitio_disparado = true;

                }
                case 3 -> {
                    if (esTurnoJugador) {
                        System.out.println("En esta posicion ya has disparado y no habia ningun barco, pon otra posicion");
                        introducirFilaColumna();
                    }
                    sitio_disparado = true;

                }
                case 5 -> {
                    matriz_objetivo[fila][columna] = 1; //Pongo la posición del barco como tocada
                    portaaviones[0][2]++; //Sumo uno a la posicion [0][2] porque se ha tocado el barco
                    switch (portaaviones[0][2]) { //En la posición [0][2] se almacena el numero de posiciones tocadas del barco
                        case 1 -> {
                            portaaviones[0][0] = fila;
                            portaaviones[0][1] = columna;
                        }
                        case 2 -> {
                            portaaviones[1][0] = fila;
                            portaaviones[1][1] = columna;
                        }
                        case 3 -> {
                            portaaviones[2][0] = fila;
                            portaaviones[2][1] = columna;
                        }
                        case 4 -> {
                            portaaviones[3][0] = fila;
                            portaaviones[3][1] = columna;
                            //Procedo a cambiar las posiciones guardadas que han sido tocadas a hundidas, porque se ha llegado a la longitud del barco
                            matriz_objetivo[portaaviones[0][0]][portaaviones[0][1]] = 2;
                            matriz_objetivo[portaaviones[1][0]][portaaviones[1][1]] = 2;
                            matriz_objetivo[portaaviones[2][0]][portaaviones[2][1]] = 2;
                            matriz_objetivo[portaaviones[3][0]][portaaviones[3][1]] = 2;
                            contador_barcos_hundidos++;
                        }
                    }

                }

                case 6 -> {
                    matriz_objetivo[fila][columna] = 1; //Ponga la posición del barco como tocada
                    submarino_1[0][2]++; //Sumo uno  a la posicion [0][2] porque se ha tocado el barco
                    switch (submarino_1[0][2]) { //En la posición [0][2] se almacena el numero de posiciones tocadas del barco
                        case 1 -> {
                            submarino_1[0][0] = fila;
                            submarino_1[0][1] = columna;
                        }
                        case 2 -> {
                            submarino_1[1][0] = fila;
                            submarino_1[1][1] = columna;
                        }
                        case 3 -> {
                            submarino_1[2][0] = fila;
                            submarino_1[2][1] = columna;
                            //Procedo a cambiar las posiciones guardadas que han sido tocadas a hundidas, porque se ha llegado a la longitud del barco
                            matriz_objetivo[submarino_1[0][0]][submarino_1[0][1]] = 2;
                            matriz_objetivo[submarino_1[1][0]][submarino_1[1][1]] = 2;
                            matriz_objetivo[submarino_1[2][0]][submarino_1[2][1]] = 2;

                            contador_barcos_hundidos++;
                        }

                    }

                }

                case 7 -> {
                    matriz_objetivo[fila][columna] = 1; //Ponga la posición del barco como tocada
                    submarino_2[0][2]++; //Sumo uno a la posicion [0][2] porque se ha tocado el barco
                    switch (submarino_2[0][2]) { //En la posición [0][2] se almacena el numero de posiciones tocadas del barco
                        case 1 -> {
                            submarino_2[0][0] = fila;
                            submarino_2[0][1] = columna;
                        }
                        case 2 -> {
                            submarino_2[1][0] = fila;
                            submarino_2[1][1] = columna;
                        }
                        case 3 -> {
                            submarino_2[2][0] = fila;
                            submarino_2[2][1] = columna;
                            //Procedo a cambiar las posiciones guardadas que han sido tocadas a hundidas, porque se ha llegado a la longitud del barco
                            matriz_objetivo[submarino_2[0][0]][submarino_2[0][1]] = 2;
                            matriz_objetivo[submarino_2[1][0]][submarino_2[1][1]] = 2;
                            matriz_objetivo[submarino_2[2][0]][submarino_2[2][1]] = 2;

                            contador_barcos_hundidos++;
                        }

                    }

                }

                case 8 -> {
                    matriz_objetivo[fila][columna] = 2; //Al ser una lancha de solo una casilla directamente se hundira
                    contador_barcos_hundidos++;
                }
                case 0 ->
                    matriz_objetivo[fila][columna] = 3; //Si no hay un barco se marca esa posición como disparada ya

            }
        }
        return contador_barcos_hundidos; //Devuelvo el valor del contador ya que java la trata como variable local de la funcion y no se me actualiza si no
    }

    /**
     * Funcion para comprobar si un barco encaja y es valido su colocación según
     * su tipo
     *
     * @param tipo
     * @return
     */
    private static boolean comprobarEspacioBarco(int fila_inicial, int columna_inicial, int fila_final, int columna_final, int tipo) {
        int distancia = 0;
        if (tipo == 5) {
            distancia = 3; //Pongo 3 para el portaaviones porque es la distancia que tiene que haber de la casilla inicial a la fina, aunque en realidad tiene 4 casillas
        } else if (tipo == 6 || tipo == 7) {
            distancia = 2; //Pongo 2 para el submarino porque es la distancia que tiene que haber de la casilla inicial a la fina, aunque en realidad tiene 3 casillas
        }
        boolean filas_validas = fila_inicial >= 0 && fila_inicial < 10 && fila_final >= 0 && fila_final < 10; // Comprobamos que estan dentro de la matriz las filas
        boolean columnas_validas = columna_inicial >= 0 && columna_inicial < 10 && columna_final >= 0 && columna_final < 10; // Comprobamos que estan dentro de la matriz las columnas
        boolean distancia_4 = fila_final - fila_inicial == distancia || fila_inicial - fila_final == distancia || columna_final - columna_inicial == distancia || columna_inicial - columna_final == distancia; // si la distancia es exacta encaja
        boolean paralelo = fila_inicial == fila_final || columna_inicial == columna_final; //Comprueba si posiciona inicial y final forman una linea horizontal o vertical

        return filas_validas && columnas_validas && distancia_4 && paralelo; // Si el barco puede encajar sera true si no false
    }

    /**
     * Funcion para disparara a una posicion valida
     */
    private static void introducirFilaColumna() {
        fila = -1;
        columna = -1; // Reinicio el valor de las variables 
        while (fila < 0 || columna < 0 || fila > 9 || columna > 9) {

            System.out.println("Indica la fila en la cual quieres disparar");
            fila = teclado.nextInt();
            System.out.println("Indica la columna en la cual quieres dispara");
            columna = teclado.nextInt();
            if (fila < 0 || columna < 0 || fila > 9 || columna > 9) {
                System.out.println("Debe de meter una posicion valida en la matriz entre 0 y 9");
            }

        }
    }
}
