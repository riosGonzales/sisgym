package dao.service;

import Entities.Factura;
import Service.CorsUtil;
import Service.FacturaService;
import dto.FacturaDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import reportes.rptExcel;
import reportes.rptPDF;

@Stateless
@Path("entities.factura")
public class FacturaFacadeREST extends AbstractFacade<Factura> {

    FacturaService facturaService = new FacturaService();
    rptExcel excel = new rptExcel();
    rptPDF pdf = new rptPDF();

    public FacturaFacadeREST() {
        super(Factura.class);
    }

    @OPTIONS
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response optionscrear() {
        return CorsUtil.buildCorsResponse();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response crear(FacturaDTO facturaDTO) {
        int codigo = 0;
        try {
            codigo = facturaService.create(facturaDTO);
            return CorsUtil.buildCorsResponse(codigo);
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Factura entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Factura find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Factura> findAll() {
        return facturaService.Listar();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Factura> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @OPTIONS
    @Path("generarFactura/{idFactura}")
    public Response optionsgenerarFactura() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("generarFactura/{idFactura}")
    public Response generarFactura(@PathParam("idFactura") Integer idFactura) {
        try {
            facturaService.generarFactura(idFactura);
            return CorsUtil.buildCorsResponse();
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @OPTIONS
    @Path("ingresos")
    public Response optionsIngresos() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("ingresos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getIngresos() {
        try {
            List<Map<String, Object>> ingresos = facturaService.getIngresos();
            return CorsUtil.buildCorsResponse(ingresos);
        } catch (Exception e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @OPTIONS
    @Path("generarIngresos")
    public Response generarIngresos() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("generarIngresos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarExcelIngresos() {
        try {
            excel.generarExcelIngresos();
            return CorsUtil.buildCorsResponse();
        } catch (IOException e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @OPTIONS
    @Path("generarIngresosPDF")
    public Response ingresosPDF() {
        return CorsUtil.buildCorsResponse();
    }

    @GET
    @Path("generarIngresosPDF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresos_pdf() {
        try {
            pdf.generarPdfIngresos();
            return CorsUtil.buildCorsResponse();
        } catch (IOException e) {
            return CorsUtil.buildCorsResponseError();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
