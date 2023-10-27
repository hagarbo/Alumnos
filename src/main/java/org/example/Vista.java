package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Vista {
    private static final String INPUT_FILE = "datos.txt";
    private static final int CAMPOS_ALUMNO = 3; // nombre apellidos y dni
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 10.0;
    private static final double CORTE_APROBADOS = 5.0;
    Scanner scanner;
    ArrayList<Alumno> alumnos = new ArrayList<>();
    private Util util;

    public Vista() {
        System.out.println("\n******************** Inicializando el sistema ****************************");
        System.out.println("\n******************** Cargando Alumnos         ****************************");
        this.util = new Util();
        this.alumnos = util.leerAlumnosDeFichero(INPUT_FILE, CAMPOS_ALUMNO);
        // Ponemos una nota aleatoria a cada alumno
        for (Alumno alumno : alumnos) {
            double nota = (double) (Math.random() * (NOTA_MAXIMA - NOTA_MINIMA) + NOTA_MINIMA);

            alumno.setNota(nota);
        }
        Alumno alumnoSinNota = new Alumno("Javier", "Sin Nota", "20305060J");
        alumnos.add(alumnoSinNota);
    }

    public void getStarted() {
        do {
            System.out.println("\n******************** Bienvenido a IES de Teis ****************************");
            System.out.println("\n\t1. Ver Alumnos.\t\t\t\t5. Añadir Alumno.");
            System.out.println("\n\t2. Ver Alumno Estrella.\t\t\t6. Borrar Alumno.");
            System.out.println("\n\t3. Ver Alumnos Suspensos.\t\t7. Añadir Nota.");
            System.out.println("\n\t4. Ver Nota media.\t\t\t0. Salir.");
            System.out.println("\n**************************************************************************");
            scanner = new Scanner(System.in);

            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    this.mostrarAlumnos();
                } else if (choice == 2) {
                    this.mostrarAlumnoEstrella();
                } else if (choice == 3) {
                    this.mostrarAlumnosSuspensos();
                } else if (choice == 4) {
                    this.mostrarNotaMedia();
                } else if (choice == 5) {
                    this.addAlumno();
                } else if (choice == 6) {
                    this.deleteAlumno();
                } else if (choice == 7) {
                    this.updateNota();
                } else if (choice == 0) {
                    System.out.println("Ha sido un placer trabajar contigo, que tengas un buen dia!!");
                    System.exit(0);
                } else {
                    System.err.println("[ERROR] Opcion incorrecta!! Vuelve a intentarlo!!");
                }

            } catch (InputMismatchException e) {
                System.err.println("[ERROR] Debes introducir un numero!!!");
                scanner.next();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    private void updateNota() {
        // TO DO
    }

    private void addAlumno() {
        try {
            Alumno alumno = this.util.leerDatosAlumno();
            this.alumnos.add(alumno);
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    private void deleteAlumno() {
        try {
            String dni = this.util.leerDni();
            Alumno alumno = findAlumno(dni);
            if (alumno != null) {
                this.alumnos.remove(alumno);
                System.out.println("\n******************* ALUMNO BORRADO CORRECTAMENTE ***********************\n");
            } else
                System.err.println("[ERROR] Alumno no encontrado!!!");
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
    }

    private Alumno findAlumno(String validDni) {
        Alumno alumno = this.alumnos.stream()
                .filter(a -> a.getDni().equals(validDni))
                .findAny().orElse(null);
        return alumno;
    }

    private void mostrarAlumnos() {
        System.out.println("\n******************** Listado de alumnos matriculados ****************************\n");
        alumnos.stream()
                .sorted((a1, a2) -> a1.getApellidos().compareTo(a2.getApellidos()))
                .forEach(alumno -> this.mostrarAlumno(alumno));
        System.out.println("\n***********************************************************************************");
    }

    private void mostrarAlumnoEstrella() {
        Alumno alumnoEstrella = alumnos.stream().max(Comparator.comparing(Alumno::getNota)).get();
        System.out.println("\n******************** Este es el ORGULLO del IES de Teis ***************************\n");
        this.mostrarAlumno(alumnoEstrella);
        System.out.println("\n***********************************************************************************");
    }

    private void mostrarAlumnosSuspensos() {
        System.out.println("\n******************** Est@s chic@s necesitan mejorar ***************************\n");
        alumnos.stream()
                .filter(alumno -> alumno.getNota() < CORTE_APROBADOS && alumno.getNota() >= NOTA_MINIMA)
                .forEach(alumno -> this.mostrarAlumno(alumno));
        System.out.println("\n***********************************************************************************");
    }

    private void mostrarAlumno(Alumno alumno) {
        String calificacion = (alumno.getNota() < NOTA_MINIMA) ? "Sin Calificar"
                : "" + String.format("%.2f", alumno.getNota());
        System.out.println("> " + alumno.getApellidos() +
                ", " + alumno.getNombre()
                + "\t\t > DNI: " + alumno.getDni()
                + "\t > Nota: " + calificacion);
    }

    private void mostrarNotaMedia() {
        System.out.println("\n******************** Estos chic@s necesitan mejorar ***************************\n");
        System.out.println("> Nota media del IES de Teis: " + String.format("%.2f", calcularNotaMedia()));
        System.out.println("\n***********************************************************************************");
    }

    private double calcularNotaMedia() {
        return alumnos.stream()
                .filter(alumno -> alumno.getNota() > NOTA_MINIMA)
                .mapToDouble(Alumno::getNota).average().orElse(Double.NaN);
    }

}
