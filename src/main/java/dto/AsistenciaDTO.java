/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Entities.Cliente;
import java.util.Date;

/**
 *
 * @author anshi
 */
public class AsistenciaDTO {

    //private Integer idAsistencia;
    //private Date fechaYHora;
    private String clienteidCliente;

    public AsistenciaDTO() {
    }

    public AsistenciaDTO(String clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }


    public String getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(String clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }
    
    
}
