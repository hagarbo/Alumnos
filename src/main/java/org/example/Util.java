package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public Util() {
    };

    public ArrayList<Alumno> leerNumerosFichero(String fichero,int camposLinea) {
        ArrayList<Alumno> resultado = new ArrayList<>();
        try {
            File myFile = new File(fichero);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                Alumno nextAlumno = this.procesarLinea(myReader.nextLine(),camposLinea);
                resultado.add(nextAlumno);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Datos del fichero de entrada no válidos, revisar datos");
            e.printStackTrace();
        }
        return resultado;
    }

    private Alumno procesarLinea(String nextLine,int camposLinea) throws Exception {

        String[] line = nextLine.split(",");
        if (line.length != camposLinea)
            throw new Exception();
        else {
            Alumno nuevoAlumno = new Alumno(line[0].trim(), line[1].trim(), line[2].trim());
            return nuevoAlumno;
        }

    }
}
