package PDF;

import Entities.Cliente;
import Entities.Rolusuario;
import Service.ClienteService;
import Service.RolUsuService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wtke9
 */
public class GenerarPDF {

    List<String> direcciones = new ArrayList<>();
    private final Map<Integer, String> roles = new HashMap<>();
    private final Map<Integer, String> nombres = new HashMap<>();

    public GenerarPDF() {
        direcciones.add("E:\\Downloads\\Gym_SegundoMOD\\Casi_Final\\Sis_Gym\\src\\main\\webapp\\reporte\\Registro.pdf");
        direcciones.add("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\reporte\\Usuarios.pdf");
        direcciones.add("C:\\Users\\Daniscarft\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");
        direcciones.add("E:\\User\\Documentos\\NetBeansProjects\\AAAAAAA\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");
        direcciones.add("C:\\Users\\jano_\\OneDrive\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");

        roles.put(1, "Admin");
        roles.put(2, "Asistente");
        roles.put(3, "Contador");
        roles.put(4, "Analista");
        
        nombres.put(1, "dachi");
        nombres.put(2, "tUki");
        nombres.put(3, "root");
    }

    public void imprimirPDF() {
        RolUsuService rolusuService = new RolUsuService();
        List<Rolusuario> listCliente = rolusuService.ListaUsu(); // Reemplaza esto con tu lista de registros

        Document document = new Document();

        try {
            //String rutaPDF = "C:\\Users\\Daniscarft\\Downloads\\Sis_Gym\\Sis_Gym\\src\\main\\webapp\\reporte\\Registro.pdf";
            String rutaPDF = direcciones.get(1);
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            document.open();

            PdfPTable table = new PdfPTable(2); // Cambia el número de columnas según tus necesidades

            // Agrega encabezados de columna
            table.addCell(new PdfPCell(new Paragraph("Rol")));
            table.addCell(new PdfPCell(new Paragraph("Usuario")));

            // Agrega los registros a la tabla 
            for (Rolusuario cliente : listCliente) {
                // Obtén el nombre del rol según el mapeo
                String nombreRol = roles.get(cliente.getCodRol());
                String nombre = nombres.get(cliente.getCodiUsua());

                // Si no hay un nombre en el mapeo, usa el valor por defecto
                if (nombreRol == null) {
                    nombreRol = String.valueOf(cliente.getCodRol());
                }
                if (nombre == null) {
                    nombre = String.valueOf(cliente.getCodiUsua());
                }

                table.addCell(new PdfPCell(new Paragraph(nombreRol)));
                table.addCell(new PdfPCell(new Paragraph(nombre)));
            }
            document.add(table);
            document.close();

            System.out.println("PDF generado correctamente.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GenerarPDF gpdf = new GenerarPDF();
        gpdf.imprimirPDF();
    }
}
