/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entities.Matricula;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        try {
            MatriculaService objMatricula = new MatriculaService();
            objMatricula.countMembresias();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
