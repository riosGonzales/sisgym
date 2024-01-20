package reportes;

import Service.FacturaService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class rptPDF {

    FacturaService objFactura = new FacturaService();

    public void generarPdfIngresos() throws IOException, DocumentException {
        List<Map<String, Object>> ingresosList = objFactura.getIngresos();

        try (Document document = new Document(PageSize.A4)) {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\factura\\ingresos.pdf"));

            // Agregar un evento de encabezado y pie de página para colorearlos
            writer.setPageEvent(new PdfPageEventHelper() {
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    cb.setColorFill(new Color(57, 73, 171)); // Nuevo color (azul más oscuro)
                    cb.rectangle(document.left(), document.top() + 10, document.right() - document.left(), 30);
                    cb.fill();
                }
            });

            document.open();

            // Añadir un título con una fuente bonita y margen inferior
            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(57, 73, 171)); // Nuevo color (azul más oscuro)
            Paragraph title = new Paragraph("Reporte de Ingresos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20); // Margen inferior
            document.add(title);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            // Encabezados de columna con colores y fuentes bonitas
            PdfPCell cell = new PdfPCell(new Paragraph("Año", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(255, 255, 255)))); // Blanco
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new Color(57, 73, 171)); // Nuevo color (azul más oscuro)
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Mes", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(255, 255, 255))));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new Color(57, 73, 171));
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Ingresos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(255, 255, 255))));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new Color(57, 73, 171));
            table.addCell(cell);

            // Nombres de los meses
            String[] nombresMeses = {
                    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
            };

            for (Map<String, Object> ingresoMap : ingresosList) {
                // Datos de la tabla
                table.addCell(String.valueOf(ingresoMap.get("year")));
                int numMes = (Integer) ingresoMap.get("month");
                table.addCell(nombresMeses[numMes - 1]); // Restamos 1 porque los arrays comienzan desde 0
                table.addCell(String.valueOf(ingresoMap.get("ingresos")));
            }

            document.add(table);

            System.out.println("PDF generado con éxito!");
        }
    }

    public static void main(String[] args) {
        rptPDF generador = new rptPDF();

        try {
            generador.generarPdfIngresos();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
