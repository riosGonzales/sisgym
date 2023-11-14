/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entities.Clases;
import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import jpa.ClasesJpaController;

/**
 *
 * @author Usuario
 */
public class ClaseService {

    ClasesJpaController claseJPA = new ClasesJpaController();
    ClienteService cs = new ClienteService();

 public void crearRapido(Clases entidad) {
    claseJPA.create(entidad);

    String asunto = "¡Nueva Clase en MuslitoFIT! No te la pierdas con asincronia y paralelismo.";
    String cuerpo = construirCuerpoCorreo(entidad);

 
    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> cs.CorreoMasivo(asunto, cuerpo));
    completableFuture.join(); // Espera hasta que se complete

}
 
 public void crearLento(Clases entidad) {
        claseJPA.create(entidad);

        String asunto = "¡Nueva Clase en MuslitoFIT! No te la pierdas metodo normal.";
        String cuerpo = construirCuerpoCorreo(entidad);

        cs.CorreoMasivo(asunto, cuerpo);
    }

    public String construirCuerpoCorreo(Clases entidad) {
        return "Estimado/a " + "muslito" + ",\n\n"
                + "¡Estamos emocionados de anunciar nuestra nueva clase en MuslitoFIT!\n\n"
                + "Detalles:\n"
                + "- Clase: " + entidad.getDescripcion() + "\n"
                + "- Instructor: " + entidad.getInstructor() + "\n"
                + "- Fecha: " + entidad.getFecha() + "\n"
                + "- Horario: " + entidad.getHorario() + "\n"
                + "- Dirección: Av. 28 Julio 600 " + "\n\n"
                + "No te pierdas esta oportunidad para ampliar tu rutina de ejercicios. ¡Únete a nosotros para esta clase exclusiva!\n\n"
                + "Esperamos verte pronto en nuestras instalaciones para disfrutar juntos de esta nueva experiencia.\n\n"
                + "¡Atentamente,\n"
                + "El Equipo de MuslitoFIT";
    }

    public String getJSon() {
        List<Clases> lista = claseJPA.findClasesEntities();
        Gson g = new Gson();
        String resultado = g.toJson(lista);
        return "{\"data\":" + resultado + " }";
    }
    
    public static void main(String[] args) {
        
          // Crea una instancia de tu clase
        ClaseService instancia = new ClaseService();

        // Crea una instancia de la entidad o clase que se utilizará
        Clases entidad = new Clases(0, "anshi", new Date(), "4:30pm-5:30pm", "yambal");

        // Guarda el tiempo de inicio
        long startTime = System.nanoTime();

        // Llama al método y realiza el trabajo cronometrado
        instancia.crearRapido(entidad);

        // Calcula el tiempo transcurrido
        long endTime = System.nanoTime();
        long tiempoTranscurrido = endTime - startTime;

        // Muestra el tiempo transcurrido en milisegundos
        System.out.println("El método tardó " + (tiempoTranscurrido / 1000000) + " milisegundos en ejecutarse.");
        
        
    }

}
