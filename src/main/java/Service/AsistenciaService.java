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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Asistencia> Listar() {
        return jpaAsistencia.findAsistenciaEntities();
    }

    public List<Map<String, Object>> countAsistencia() {
        List<Object[]> countAsistencia = jpaAsistencia.countAsistenciasPorDia();
        List<Map<String, Object>> asistenciaList = new ArrayList<>();
        for (Object[] asistencia : countAsistencia) {
            Map<String, Object> asistenciaMap = new HashMap<>();
            asistenciaMap.put("fecha", asistencia[0]);
            asistenciaMap.put("cantidad", asistencia[1]);
            asistenciaList.add(asistenciaMap);            
        }
        return asistenciaList;
    }

    public static void main(String[] args) {
        try {
            AsistenciaService objAsistencia = new AsistenciaService();
            objAsistencia.countAsistencia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
