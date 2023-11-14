/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;

/**
 *
 * @author anshi
 */
public class PagoDTO {

    private Integer idPago;
    private Date fechaPago;
    private Double monto;
    private String metodoPago;
    private int matricula;
    //private List<Factura> facturaList;

    public PagoDTO() {
    }

    public PagoDTO(Integer idPago, Date fechaPago, Double monto, String metodoPago, int Matricula) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.matricula = Matricula;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int Matricula) {
        this.matricula = Matricula;
    }

    
    
}
