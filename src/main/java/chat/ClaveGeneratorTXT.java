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

   public static String generarYGuardarClave(String claveCompartida) {
       direcciones.add("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\bloc\\clave.txt"); 
       direcciones.add("C:\\Users\\jano_\\OneDrive\\Documents\\NetBeansProjects\\Sis_Gym\\src\\main\\webapp\\bloc\\clave.txt"); 
       File archivo = new File(direcciones.get(1));
        // Verificar si el archivo está vacío
        if (archivo.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader(direcciones.get(1)))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                claveRecuperada.append(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "{\"resultado\": \"" +claveRecuperada.toString() + "\"}";
    }

    public static void main(String[] args) {
        try {
            ClaveGeneratorTXT metodo = new ClaveGeneratorTXT();
            String clave = "zzzz";
            metodo.generarYGuardarClave(clave);
            System.out.println("texto: " +  metodo.recuperarClave());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
