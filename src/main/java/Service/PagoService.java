/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entities.Matricula;
import Entities.Pago;
import dto.PagoDTO;
import java.util.List;
import jpa.PagoJpaController;

public class PagoService {

    PagoJpaController pagojpa = new PagoJpaController();
    MatriculaService matriServ = new MatriculaService();
    
    public int crear(PagoDTO pagodto) {
        int codigo = 0;
        try {
            Matricula matricula = matriServ.buscar(pagodto.getMatricula());
            Pago entidad = new Pago();
            entidad.setMonto(pagodto.getMonto());
            entidad.setMetodoPago(pagodto.getMetodoPago());
            entidad.setFechaPago(pagodto.getFechaPago());
            entidad.setMatricula(matricula);
            pagojpa.create(entidad);
            codigo = entidad.getIdPago();
        } catch (Exception e) {
        }
        return codigo;
    }
    
    public List<Pago> Lista (){
        return pagojpa.findPagoEntities();
    }
    
    public Pago buscar(int id){
        return pagojpa.findPago(id);
    }
}
