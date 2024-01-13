package Service;

import Entities.Cliente;
import Entities.Empleado;
import Entities.Factura;
import Entities.Pago;
import conexion.MySQL;
import dto.FacturaDTO;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jpa.EmpleadoJpaController;
import jpa.FacturaJpaController;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class FacturaService {

    FacturaJpaController facturaJPA = new FacturaJpaController();
    ClienteService clienteService = new ClienteService();
    PagoService pagoService = new PagoService();
    EmpleadoJpaController empleadoJPA = new EmpleadoJpaController();

    List<String> direcciones = new ArrayList<>();

    public FacturaService() {
        direcciones.add("E:\\Downloads\\Gym_SegundoMOD\\Casi_Final\\Sis_Gym\\src\\main\\webapp\\factura\\");
        direcciones.add("C:\\Users\\jano_\\OneDrive\\Documents\\NetBeansProjects\\Sis_Gym\\src\\main\\webapp\\factura\\");
        direcciones.add("C:\\Users\\Daniscarft\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\factura\\");
        direcciones.add("E:\\User\\Documentos\\NetBeansProjects\\AAAAAAA\\sisgym\\src\\main\\webapp\\factura\\");
        direcciones.add("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\factura\\");
    }

    public int create(FacturaDTO facturaDTO) {

        int codigo = 0;
        try {

            //Creacion de las dependencias de factura
            Cliente clienteFactura = clienteService.buscar(facturaDTO.getClienteidCliente());
            Pago pagoFactura = pagoService.buscar(facturaDTO.getPago());
            Empleado empleadoFactura = empleadoJPA.findEmpleado(facturaDTO.getEmpleadoidEmpleado());

            Factura entidad = new Factura();
            //Asignacion
            entidad.setClienteidCliente(clienteFactura);
            entidad.setEmpleadoidEmpleado(empleadoFactura);
            entidad.setPagoidPago(pagoFactura);
            entidad.setFechaFin(facturaDTO.getFechaFin());
            entidad.setFechaInicio(facturaDTO.getFechaInicio());
            entidad.setSubtotal(facturaDTO.getSubtotal());
            entidad.setTotal(facturaDTO.getTotal());
            //Creacion
            facturaJPA.create(entidad);
            codigo = entidad.getIdFactura();

        } catch (Exception e) {
        }
        return codigo;

    }

    public List<Factura> Listar() {
        return facturaJPA.findFacturaEntities();
    }

    public List<Map<String, Object>> getIngresos() {
        List<Object[]> ingresos = facturaJPA.findIngresosPorMes();
        List<Map<String, Object>> ingresosList = new ArrayList<>();

        for (Object[] ingreso : ingresos) {
            Map<String, Object> ingresoMap = new HashMap<>();
            ingresoMap.put("year", ingreso[0]);
            ingresoMap.put("month", ingreso[1]);
            ingresoMap.put("ingresos", ingreso[2]);
            ingresosList.add(ingresoMap);
        }
        return ingresosList;
    }

    public static void main(String[] args) {
        try {
            FacturaService poto = new FacturaService();
            poto.getIngresos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarFactura(int num) {
        try {
            Connection cn = MySQL.getInstance().getConnection();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("factura.jrxml");
            if (inputStream == null) {
                throw new FileNotFoundException("El archivo factura.jrxml no se encontró en el classpath.");
            }
            JasperReport reporte = JasperCompileManager.compileReport(inputStream);
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idFactura", num);
            JasperPrint mostrarReporte = JasperFillManager.fillReport(reporte, parametros, cn);

            //DIRECION PA LA FACTURA
            String rutaPDF = direcciones.get(4) + "factura" + num + ".pdf";
            JasperExportManager.exportReportToPdfFile(mostrarReporte, rutaPDF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
