/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package NavarroLuis_04;

import java.util.Scanner;

/**
 *
 * @author isard
 */
public class NavarroLuis_04 {

    public static Scanner teclado = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Programa para jugar a minijuegos (introduce el numero entero de la opcion que quieres elegir) ");
        System.out.println("");
        double respuesta = 0;
        int respuesta_int = 0;
        while (respuesta_int != 5) {
            System.out.println("1. Cuatro en raya");
            System.out.println("2. Busca minas");
            System.out.println("3. Hundir la flota");
            System.out.println("4. Ver puntuaciones");
            System.out.println("5. Salir");
            
            respuesta = teclado.nextDouble(); 
            respuesta_int = (int)respuesta; // Hago esto para que no pete el codigo con ningun double, en caso de que el usuario meta decimales se truncara el numero
            
            switch (respuesta_int) {
                case 1 -> {
                    Cuatroenraya.juego();
                }
                case 2 -> {
                    Buscaminas.juego();
                }
                case 3 -> {
                    Hundirlaflota.juego();
                }
                case 4 -> {
                    System.out.println("El numero de victorias en el juego Buscaminas es: " + Buscaminas.partidas_ganadas);
                    System.out.println("El numero de victorias en el juego Hundir la flota es: " + Hundirlaflota.partidas_ganadas);
                    System.out.println("");
                }
                case 5 -> {
                    break;
                }
                default -> {
                        System.out.println("Debes introducir un numero entre las opciones");
                        System.out.println("");
                }
            }
        }
    }

}
