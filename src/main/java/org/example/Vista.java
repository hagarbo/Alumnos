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

    private static final String DNI_ERROR__STRING = ":::::: [ERROR] El Dni introducido no es valido !!! :::::::::";
    private static final String DATA_ERROR_STRING = ":::::: [ERROR] El campo no puede estar vacio !!! :::::::::";
    private static final String NOTA_ERROR_STRING = ":::::: [ERROR] La nota debe estar entre 0 y 10 !!! :::::::::";
    private static final String NOTA_QUERY_STRING = "******* Introduzca una nota numerica entre 0 y 10(ej:7.55): ********";
    private static final String DNI_QUERY_STRING = "******* Introduzca un DNI valido(ej:77456765A): ********";
    private static final String NOMBRE_QUERY_STRING = "******* Introduzca el Nombre: ********";
    private static final String APELLIDOS_QUERY_STRING = "******* Introduzca los Apellidos: ********";

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
        Alumno alumnoSinNota = new Alumno("Javier", "Sin Nota", "87073567Y");
        alumnos.add(alumnoSinNota);
    }

    public void getStarted() {
        scanner = new Scanner(System.in);

        do {
            System.out.println("\n******************** Bienvenido a IES de Teis ****************************");
            System.out.println("\n\t1. Ver Alumnos.\t\t\t\t5. Añadir Alumno.");
            System.out.println("\n\t2. Ver Alumno Estrella.\t\t\t6. Borrar Alumno.");
            System.out.println("\n\t3. Ver Alumnos Suspensos.\t\t7. Añadir Nota.");
            System.out.println("\n\t4. Ver Nota media.\t\t\t0. Salir.");
            System.out.println("\n**************************************************************************");

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
        String dni = this.util.leerDni(DNI_QUERY_STRING, DNI_ERROR__STRING);
        Alumno alumno = findAlumno(dni);
        if (alumno != null) {
            double nota = this.util.leerNota(NOTA_QUERY_STRING, NOTA_ERROR_STRING);
            this.alumnos.stream()
                    .filter(a -> a.getDni().equals(dni)).findAny().orElse(null).setNota(nota);
            ;
            System.out.println("\n******************* NOTA REGISTRADA CORRECTAMENTE ***********************\n");
        } else
            System.err.println("[ERROR] Alumno no encontrado!!!");
    }

    private void addAlumno() {
        Alumno alumno = new Alumno();
        String nombre = this.util.leerDatosAlumno(NOMBRE_QUERY_STRING, DATA_ERROR_STRING);
        String apellidos = this.util.leerDatosAlumno(APELLIDOS_QUERY_STRING, DATA_ERROR_STRING);
        String dni = this.util.leerDni(DNI_QUERY_STRING, DNI_ERROR__STRING);
        alumno.setNombre(nombre);
        alumno.setApellidos(apellidos);
        alumno.setDni(dni);
        alumno.setNota(NOTA_MINIMA - 1);
        this.alumnos.add(alumno);
        this.util.guardarDatosEnFichero(INPUT_FILE, this.alumnos);
        System.out.println("\n******************* ALUMNO CREADO CORRECTAMENTE ***********************\n");
    }

    private void deleteAlumno() {
        String dni = this.util.leerDni(DNI_QUERY_STRING, DNI_ERROR__STRING);
        Alumno alumno = findAlumno(dni);
        if (alumno != null) {
            this.alumnos.remove(alumno);
            this.util.guardarDatosEnFichero(INPUT_FILE, this.alumnos);
            System.out.println("\n******************* ALUMNO BORRADO CORRECTAMENTE ***********************\n");
        } else
            System.err.println("[ERROR] Alumno no encontrado!!!");

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
                : String.format("%.2f", alumno.getNota());
        System.out.println("> " + alumno.getApellidos() +
                ", " + alumno.getNombre()
                + "\t\t > DNI: " + alumno.getDni()
                + "\t > Nota: " + calificacion);
    }

    private void mostrarNotaMedia() {
        System.out.println("\n******************** Estos chic@s necesitan mejorar ***************************\n");
        System.out.println("> Nota media del IES de Teis: " + String.format("%.2f", calcularNotaMedia()));
        System.out.println("\n*********************************************************************************");
    }

    private double calcularNotaMedia() {
        return alumnos.stream()
                .filter(alumno -> alumno.getNota() > NOTA_MINIMA)
                .mapToDouble(Alumno::getNota).average().orElse(Double.NaN);
    }

}
