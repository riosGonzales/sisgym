/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entities.Asistencia;
import Entities.Cliente;
import dto.AsistenciaDTO;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import jpa.AsistenciaJpaController;
import jpa.ClienteJpaController;

/**
 *
 * @author anshi
 */
public class AsistenciaService {

    AsistenciaJpaController jpaAsistencia = new AsistenciaJpaController();
    ClienteService clienteservice = new ClienteService();

    public void crear(AsistenciaDTO asistdto) {
        Cliente cliente = clienteservice.buscarDNI(asistdto.getClienteidCliente());
        Asistencia entidadAsistencia = new Asistencia();
        LocalDateTime fechaActual = LocalDateTime.now();
        Date fecha = Date.from(fechaActual.atZone(java.time.ZoneId.systemDefault()).toInstant());
        entidadAsistencia.setFechaYHora(fecha);
        entidadAsistencia.setClienteidCliente(cliente);
        try {
            jpaAsistencia.create(entidadAsistencia);
        } catch (Exception e) {
        }
    }
    
    public List<Asistencia> Listar (){
        return  jpaAsistencia.findAsistenciaEntities();
    }
    
    public static void main(String[] args) {
        AsistenciaService aservice = new AsistenciaService();
        AsistenciaDTO asistdto = new AsistenciaDTO();
        asistdto.setClienteidCliente("75626947");
        aservice.crear(asistdto);
        
    }

}
