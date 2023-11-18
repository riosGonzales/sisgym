package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClaveGeneratorTXT {
//        direcciones.add("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\bloc\\clave.txt");

    static List<String> direcciones = new ArrayList<>();

    public ClaveGeneratorTXT() {
    }

        public static void borrarContenido() {
        direcciones.add("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\bloc\\clave.txt"); //0
        direcciones.add("C:\\Users\\Daniscarft\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\bloc\\clave.txt");      //1
        direcciones.add("E:\\Proyectos Java\\Cripto 2\\GIT\\pruebaza\\sisgym\\src\\main\\webapp\\bloc\\clave.txt");                //2
        direcciones.add("C:\\Users\\jano_\\OneDrive\\Documents\\NetBeansProjects\\Sis_Gym\\src\\main\\webapp\\bloc\\clave.txt");
        try {
            try (FileWriter fileWriter = new FileWriter(direcciones.get(2), false)) {
                fileWriter.write(""); // Escribe una cadena vacía para borrar el contenido
            } // Escribe una cadena vacía para borrar el contenido
            System.out.println("Contenido del archivo borrado exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al intentar borrar el contenido del archivo: " + e.getMessage());
        }
    }
    public static String generarYGuardarClave(String claveCompartida) {

        File archivo = new File(direcciones.get(2));
        // Verificar si el archivo está vacío
        if (archivo.length() == 0) {
            try ( BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(claveCompartida);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return claveCompartida;
        } else {
            System.out.println("El archivo no está vacío. No se realizará la escritura.");
            return ""; // Puedes ajustar el valor de retorno según tus necesidades
        }
    }

    public static String recuperarClave() {
        StringBuilder claveRecuperada = new StringBuilder();
        try ( BufferedReader reader = new BufferedReader(new FileReader(direcciones.get(2)))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                claveRecuperada.append(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"resultado\": \"" + claveRecuperada.toString() + "\"}";
    }



    public static void main(String[] args) {
        try {
            ClaveGeneratorTXT metodo = new ClaveGeneratorTXT();
            String clave = "zzzz";
            metodo.generarYGuardarClave(clave);
            System.out.println("texto: " + metodo.recuperarClave());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
