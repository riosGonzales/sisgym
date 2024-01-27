
package Service;

import Entities.Cliente;
import Entities.Matricula;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpa.MatriculaJpaController;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

public class MatriculaService {

    MatriculaJpaController jpaMatricula = new MatriculaJpaController();
    ClienteService clienteService = new ClienteService();

    public int crear(Matricula entidad) {
        int codigo = 0;
        try {
            jpaMatricula.create(entidad);
            codigo = entidad.getIdMatricula();
        } catch (Exception e) {
        }
        return codigo;
    }

    public List<Matricula> listar() {
        return jpaMatricula.findMatriculaEntities();
    }

    public List<Map<String, Object>> countMembresias() {
        List<Object[]> contarMembresias = jpaMatricula.countMembresias();
        List<Map<String, Object>> membresiasList = new ArrayList<>();
        for (Object[] membresia : contarMembresias) {
            Map<String, Object> membresiaMap = new HashMap<>();
            membresiaMap.put("tipoMembresia", membresia[0]);
            membresiaMap.put("cantidad", membresia[1]);
            membresiasList.add(membresiaMap);
        }
        return membresiasList;
    }

    public Matricula buscar(int id) {
        return jpaMatricula.findMatricula(id);
    }

    public void eliminar(int id) throws IllegalOrphanException {
        try {
            jpaMatricula.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MatriculaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editar(int id, Matricula entidad) throws Exception {
        try {
            Matricula buscado = buscar(id);

            buscado.setIdMembresia(entidad.getIdMembresia());
            buscado.setFechaInicio(entidad.getFechaInicio());
            buscado.setFechaFin(entidad.getFechaFin());
            jpaMatricula.edit(buscado);
        } catch (Exception e) {
        }

    }

    public Date obtenerFechaFinMasRecientePorDNI(String dni) {
        Cliente cliente = clienteService.buscarDNI(dni);
        if (cliente != null) {
            List<Matricula> matriculas = cliente.getMatriculaList();
            if (!matriculas.isEmpty()) {
                Matricula matriculaMasReciente = matriculas.get(matriculas.size() - 1);
                return matriculaMasReciente.getFechaFin();
            }
        }
        return null;
    }

    public Matricula obtenerFechas(String dni) {
        Cliente cliente = clienteService.buscarDNI(dni);
        if (cliente != null) {
            List<Matricula> matriculas = cliente.getMatriculaList();
            if (!matriculas.isEmpty()) {
                Matricula matriculaMasReciente = matriculas.get(matriculas.size() - 1);
                Date fechaInicio = matriculaMasReciente.getFechaInicio();
                Date fechaFin = matriculaMasReciente.getFechaFin();
                return new Matricula(fechaInicio, fechaFin);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            MatriculaService matriculaService = new MatriculaService();
            String dniABuscar = "70861979";
            Matricula matriculaConFechas = matriculaService.obtenerFechas(dniABuscar);
            if (matriculaConFechas != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                System.out.println("Fecha de inicio: " + dateFormat.format(matriculaConFechas.getFechaInicio()));
                System.out.println("Fecha de fin: " + dateFormat.format(matriculaConFechas.getFechaFin()));
            } else {
                System.out.println("no se encuentra");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
