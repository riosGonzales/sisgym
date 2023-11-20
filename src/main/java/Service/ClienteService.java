package Service;

import Correo.CorreoClass;
import Entities.*;
import PDF.GeneradorPDF;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.*;
import jpa.*;
import jpa.exceptions.*;

public class ClienteService {

    ClienteJpaController jpaCliente = new ClienteJpaController();

//    public void crear(ClienteDTO clienteDTO) {
//
//        List<Factura> facturasCli = new LinkedList<>();
//        List<Asistencia> asistenciasCli = new LinkedList<>();
//        List<Matricula> matriculasCli = new LinkedList<>();
//
//        Cliente entidad = new Cliente(0, clienteDTO.getNombre(), clienteDTO.getApellido(), clienteDTO.getDni(),
//                clienteDTO.getTelefono(), facturasCli, asistenciasCli, matriculasCli);
//        jpaCliente.create(entidad);
//
//    }
    public int crear(Cliente entidad) {
        int codigo = 0;
        List<Factura> facturasCli = new LinkedList<>();
        List<Asistencia> asistenciasCli = new LinkedList<>();
        List<Matricula> matriculasCli = new LinkedList<>();

        entidad.setFacturaList(facturasCli);
        entidad.setAsistenciaList(asistenciasCli);
        entidad.setMatriculaList(matriculasCli);
        entidad.setEstaClie((short) 1);

        try {
            jpaCliente.create(entidad);
            codigo = entidad.getIdCliente();
            CorreoSingular(entidad);
        } catch (Exception e) {
        }

        return codigo;
    }

    public List<Cliente> ListaClientes() {
        return jpaCliente.findClientesByEstado(1);
    }

    public void eliminar(int id) throws IllegalOrphanException {
        try {
            jpaCliente.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //jiijijij
//en mi mente funciona jiijji
    public Cliente buscar(int id) {
        return jpaCliente.findCliente(id);
    }

    public Cliente buscarDNI(String dni) {
        return jpaCliente.findByDni(dni);
    }

//    public void editar(ClienteDTO clienteDTO) throws NonexistentEntityException {
//        try {
//            Cliente entidad = buscar(clienteDTO.getId());
//            entidad.setApellidos(clienteDTO.getApellido());
//            entidad.setDni(clienteDTO.getDni());
//            entidad.setNombreCliente(clienteDTO.getNombre());
//            entidad.setTelefonoCliente(clienteDTO.getTelefono());
//            jpaCliente.edit(entidad);
//
//        } catch (Exception ex) {
//            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void LogicDelete(int id) throws IllegalOrphanException, NonexistentEntityException {
        Cliente cli = buscar(id);
        System.out.println("El estado actual de: " + cli.getNombreCliente() + " es " + cli.getEstaClie());
        cli.setEstaClie((short) 0);
        System.out.println("El estado renovado de: " + cli.getNombreCliente() + " es " + cli.getEstaClie());

        editar2(id, cli);
    }

    public void editar2(int id, Cliente entidad) throws NonexistentEntityException {
        try {
            Cliente buscado = buscar(id);
            buscado.setApellidos(entidad.getApellidos());
            buscado.setDni(entidad.getDni());
            buscado.setNombreCliente(entidad.getNombreCliente());
            buscado.setTelefonoCliente(entidad.getTelefonoCliente());
            buscado.setEmailClie(entidad.getEmailClie());
            buscado.setEstaClie(entidad.getEstaClie());

            // Establece explícitamente el valor de idCliente en la entidad buscado
            buscado.setIdCliente(id);

            jpaCliente.edit(buscado);

            System.out.println("ID del cliente actualizado: " + buscado.getIdCliente());

        } catch (Exception ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirReporte() {

        GeneradorPDF gpdf = new GeneradorPDF();
        gpdf.imprimirPDF();
    }

//Si el correo se repite en los registros solo se le enviara a uno
    //El paralelismo sirve a partir de 1.8. Ejemplo: 
//           <plugin>
//                <groupId>org.apache.maven.plugins</groupId>
//                <artifactId>maven-compiler-plugin</artifactId>
//                <version>3.1</version>
//                <configuration>
//                    <source>1.8</source>
//                    <target>1.8</target>
//                    <compilerArguments>
//                        <endorseddirs>${endorsed.dir}</endorseddirs>
//                    </compilerArguments>
//                </configuration>
//            </plugin>
    
    public void CorreoMasivo(String asunto, String cuerpo) {
    List<Cliente> clientes = ListaClientes();
    Set<String> correosProcesados = new HashSet<>(); // Almacena los correos procesados

    // Limita la cantidad de hilos activos a 10 para no sobrecargar el servidor SMTP
    ForkJoinPool customThreadPool = new ForkJoinPool(10);

    customThreadPool.submit(() ->
        clientes.parallelStream()
                .filter(cliente -> cliente.getIdCliente() <= 6)
                .forEach(cliente -> {
                    String email = cliente.getEmailClie();
                    if (correosProcesados.add(email)) {
                        CorreoClass.enviarCorreo(email, asunto, cuerpo);
                    }
                })
    ).join();
}

    
    
//    public void CorreoMasivo(String asunto, String cuerpo) {
//        List<Cliente> clientes = ListaClientes();
//        Set<String> correosProcesados = new HashSet<>(); // Almacena los correos procesados
//
//        clientes.parallelStream()
//                .filter(cliente -> cliente.getIdCliente() <= 5)
//                .forEach(cliente -> {
//                    String email = cliente.getEmailClie();
//                    if (correosProcesados.add(email)) { // Intenta agregar el correo al conjunto
//                        CorreoClass.enviarCorreo(email, asunto, cuerpo);
//                    }
//                });
//    }

    public void CorreoMasivoLento(String asunto, String cuerpo) {

        for (Cliente cliente : ListaClientes()) {
            if (cliente.getIdCliente() <= 5 && cliente.getEstaClie() == 1) {

                CorreoClass.enviarCorreo(cliente.getEmailClie(), asunto, cuerpo);

            }
        }
    }

//    public void CorreoSingular(Cliente cliente) {
//        String asunto = "¡Bienvenido a MuslitoFIT, " + cliente.getNombreCliente() + "!";
//        String cuerpo = "Estamos encantados de tenerte como parte de nuestra comunidad en MuslitoFit. Como bienvenida, queremos ofrecerte un cupón de descuento para tu próxima membresía: chingaFIT.\n\nDisfruta de nuestras instalaciones y clases diseñadas para ayudarte a alcanzar tus metas de fitness. ¡Esperamos verte pronto en MuslitoFit!";
//        CorreoClass.enviarCorreo(cliente.getEmailClie(), asunto, cuerpo);
//    }
    //Asincronico para que la creacion del cliente sea rapida
    public void CorreoSingular(Cliente cliente) {
        CompletableFuture.runAsync(() -> {
            String asunto = "¡Bienvenido a MuslitoFIT, " + cliente.getNombreCliente() + "!";
            String cuerpo = "Estamos encantados de tenerte como parte de nuestra comunidad en MuslitoFit. Como bienvenida, queremos ofrecerte un cupón de descuento para tu próxima membresía: chingaFIT.\n\nDisfruta de nuestras instalaciones y clases diseñadas para ayudarte a alcanzar tus metas de fitness. ¡Esperamos verte pronto en MuslitoFit!";
            CorreoClass.enviarCorreo(cliente.getEmailClie(), asunto, cuerpo);
        });
    }

    public static void main(String[] args) {
        ClienteService cs = new ClienteService();

        long startTime = System.currentTimeMillis(); // Registra el tiempo de inicio

        cs.CorreoMasivo("prueba_sin_repeticion", "paralelismo");

        long endTime = System.currentTimeMillis(); // Registra el tiempo después de completar el método

        long duration = endTime - startTime; // Calcula la duración de la ejecución

        System.out.println("El método CorreoMasivo tardó " + duration + " milisegundos en ejecutarse.");

    }

}
