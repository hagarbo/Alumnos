package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Vista {
    private static final String INPUT_FILE = "datos.txt";
    private static final int  CAMPOS_ALUMNO = 3; // nombre apellidos y dni
    Scanner scanner;
    ArrayList<Alumno> alumnos = new ArrayList<>();

    public Vista() {
        Util util = new Util();
        this.alumnos = util.leerNumerosFichero(INPUT_FILE,CAMPOS_ALUMNO);

        // Ponemos una nota aleatoria a cada alumno
        System.out.println("+++++++++++++ Inicializando el sistema +++++++++++++++++");
        System.out.println("+++++++++++++ Cargando Alumnos +++++++++++++++++");
        for (Alumno alumno : alumnos) {
            alumno.setNota(Math.round((Math.random() * 10) * 100.0) / 100.0);
            System.out.println(">> Alumno: " + alumno.getNombre() + " " + alumno.getApellidos() + " >> DNI: "
                    + alumno.getDni() + " >> Nota: " + alumno.getNota());
        }
    }

    public void getStarted() {
        do {
            System.out.println("\n******************** Bienvenido a IES de Teis ****************************");
            System.out.println("\n\t1. Ver Alumnos.\t\t\t\t\t\t\t\t5. Añadir Alumno.");
            System.out.println("\n\t2. Ver Alumno Estrella.\t\t\t\t\t\t6. Borrar Alumno.");
            System.out.println("\n\t3. Ver Alumnos Suspensos.\t\t\t\t\t7. Añadir Nota.");
            System.out.println("\n\t4. Ver Nota media.\t\t\t\t\t\t\t0. Exit.");
            System.out.println("\n**************************************************************************");
            scanner = new Scanner(System.in);

            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    System.out.println("Lista de alumnos matriculados:");
                    // TODO
                } else if (choice == 2) {
                    // TODO
                } else if (choice == 3) {
                    // TODO
                } else if (choice == 4) {
                    // TODO
                } else if (choice == 5) {
                    // TODO
                } else if (choice == 6) {
                    // TODO
                } else if (choice == 7) {
                    // TODO
                } else if (choice == 0) {
                    System.out.println("Bye!!");
                    System.exit(0);
                    ;
                } else {
                    System.err.println("[ERROR] Your option is incorrect!! Try again!!");
                }

            } catch (InputMismatchException e) {
                System.err.println("[ERROR] You must type a number!!!");
                scanner.next();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (true);
    }

}
