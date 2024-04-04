package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final int LONGITUD_DNI = 9;
    private static final String PATRON_DNI = "[0-9]{8}[a-zA-Z]";
    private static final String DELIMITADOR = ",";
    private static final String[] ARRAY_LETRAS = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J",
            "Z", "S", "Q", "V", "H", "L", "C", "K", "E" };
    private static final String[] ARRAY_INVALIDOS = { "00000000T", "00000001R", "99999999R" };

    public Util() {
    };

    public ArrayList<Alumno> leerAlumnosDeFichero(String fichero, int camposLinea) {
        ArrayList<Alumno> resultado = new ArrayList<>();
        try {
            File myFile = new File(fichero);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                Alumno nextAlumno = this.procesarLinea(myReader.nextLine(), camposLinea);
                resultado.add(nextAlumno);
            }
            myReader.close();
            System.out.println("\n******************** Datos Cargados Correctamente ****************************");

        } catch (FileNotFoundException e) {
            System.err.println(":::::: [ERROR] Fichero de datos no encontrado!!! :::::::::");
        } catch (Exception e) {
            System.err.println(":::::: [ERROR] Fallo en la lectura del fichero:" + e.getMessage() + " :::::::::");
        }
        return resultado;
    }

    public void guardarDatosEnFichero(String fichero, ArrayList<Alumno> datos) {
        try (FileWriter fileWriter = new FileWriter(fichero)) {
            for (Alumno alumno : datos) {
                String linea = alumno.getNombre() + DELIMITADOR + alumno.getApellidos() + DELIMITADOR + alumno.getDni()
                        + "\n";
                fileWriter.write(linea);
            }
        } catch (Exception e) {
            System.err.println(":::::: [ERROR] Fallo al guardar los datos");
        }

    }

    private Alumno procesarLinea(String nextLine, int camposLinea) throws Exception {

        String[] line = nextLine.split(",");
        if (line.length != camposLinea)
            throw new Exception("HA HABIDO UN ERROR EN LA LECTURA DE DATOS !!!");
        else {
            if (!this.validar(line[2].trim()))
                throw new Exception("Se han encontrado dnis Invalidos !!!");
            Alumno nuevoAlumno = new Alumno(line[0].trim(), line[1].trim(), line[2].trim());
            return nuevoAlumno;
        }

    }

    private boolean validar(String dni) {

        return (comprobarLongitud(dni) && tienePatronValido(dni) && tieneletraValida(dni)
                && estaEnInvalidos(dni));
    }

    private boolean estaEnInvalidos(String dni) {

        for (String invalido : ARRAY_INVALIDOS) {
            if (dni.equalsIgnoreCase(invalido))
                return false;
        }
        return true;
    }

    private boolean tieneletraValida(String dni) {

        int dniInt = Integer.parseInt(dni.substring(0, LONGITUD_DNI - 1));
        String letraDNI = dni.substring(LONGITUD_DNI - 1, LONGITUD_DNI);
        return letraDNI.equalsIgnoreCase(ARRAY_LETRAS[dniInt % ARRAY_LETRAS.length]);
    }

    private boolean tienePatronValido(String dni) {
        Pattern pattern = Pattern.compile(PATRON_DNI);
        Matcher matcher = pattern.matcher(dni);
        return matcher.find();
    }

    private boolean comprobarLongitud(String dni) {
        return dni.length() == LONGITUD_DNI;
    }

    public String leerDatosAlumno(String outputMsg, String errMsg) {
        System.out.println(outputMsg);
        String datos = this.leerDato();
        if (!datos.equals(""))
            return datos;
        else {
            System.out.println(errMsg);
            return this.leerDatosAlumno(outputMsg, errMsg);
        }
    }

    public String leerDni(String outputMsg, String errMsg) {
        System.out.println(outputMsg);
        String dni = this.leerDato();
        if (this.validar(dni))
            return dni;
        else {
            System.err.println(errMsg);
            return this.leerDni(outputMsg, errMsg);
        }
    }

    public double leerNota(String outputMsg, String errMsg) {
        System.out.println(outputMsg);
        String dato = this.leerDato();
        try {
            double nota = Double.parseDouble(dato);
            if (nota >= 0.0 && nota <= 10.0)
                return nota;
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println(errMsg);
            return this.leerNota(outputMsg, errMsg);
        }
    }

    private String leerDato() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }

}
