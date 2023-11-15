package PDF;

import Entities.Cliente;
import Service.ClienteService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jano_
 */
public class GeneradorPDF {

    List<String> direcciones = new ArrayList<>();
        public GeneradorPDF() {
            direcciones.add("E:\\Downloads\\Gym_SegundoMOD\\Casi_Final\\Sis_Gym\\src\\main\\webapp\\reporte\\Registro.pdf");
            direcciones.add("C:\\Users\\wtke9\\Downloads\\Sis_Gym\\Sis_Gym\\src\\main\\webapp\\reporte\\Registro.pdf");
            direcciones.add("C:\\Users\\Daniscarft\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");
            direcciones.add("E:\\User\\Documentos\\NetBeansProjects\\AAAAAAA\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");
            direcciones.add("C:\\Users\\jano_\\OneDrive\\Documents\\NetBeansProjects\\sisgym\\src\\main\\webapp\\reporte\\Registro.pdf");
    }

    
    
    public void imprimirPDF() {
        ClienteService cService = new ClienteService();
        List<Cliente> listCliente = cService.ListaClientes(); // Reemplaza esto con tu lista de registros
       

        Document document = new Document();

        try {
            //String rutaPDF = "C:\\Users\\Daniscarft\\Downloads\\Sis_Gym\\Sis_Gym\\src\\main\\webapp\\reporte\\Registro.pdf";
            String rutaPDF = direcciones.get(2);
            PdfWriter.getInstance(document, new FileOutputStream(rutaPDF));
            document.open();

            PdfPTable table = new PdfPTable(4); // Cambia el número de columnas según tus necesidades

            // Agrega encabezados de columna
            table.addCell(new PdfPCell(new Paragraph("IdCliente")));
            table.addCell(new PdfPCell(new Paragraph("Nombre")));
            table.addCell(new PdfPCell(new Paragraph("Apellido")));
            table.addCell(new PdfPCell(new Paragraph("Email")));
            // Agrega los registros a la tabla 
            for (Cliente cliente : listCliente) {
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(cliente.getIdCliente()))));
                table.addCell(new PdfPCell(new Paragraph(cliente.getNombreCliente())));
                table.addCell(new PdfPCell(new Paragraph(cliente.getApellidos())));
                table.addCell(new PdfPCell(new Paragraph(cliente.getEmailClie())));
            }

            document.add(table);
            document.close();

            System.out.println("PDF generado correctamente.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        GeneradorPDF gpdf = new GeneradorPDF();
        gpdf.imprimirPDF();
    }

}
