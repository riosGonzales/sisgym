package reportes;

import Service.FacturaService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class rptExcel {

    FacturaService objFactura = new FacturaService();

    public void generarExcelIngresos() throws IOException {
        List<Map<String, Object>> ingresosList = objFactura.getIngresos();

        Workbook workbook = WorkbookFactory.create(true);

        Sheet sheet = workbook.createSheet("Ingresos");

        // Crear el estilo con bordes
        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);

        // Crear la fila de encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Año");
        headerRow.createCell(1).setCellValue("Mes");
        headerRow.createCell(2).setCellValue("Ingresos");

        // Aplicar el estilo de borde al encabezado
        for (Cell cell : headerRow) {
            cell.setCellStyle(borderStyle);
        }

        int rowNum = 1;
        double totalIngresos = 0.0;

        // Nombres de los meses
        String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        // Crear el estilo de moneda
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(borderStyle);
        DataFormat dataFormat = workbook.createDataFormat();
        currencyStyle.setDataFormat(dataFormat.getFormat("[$S/.-280]#,##0.0"));

        for (Map<String, Object> ingresoMap : ingresosList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Integer) ingresoMap.get("year"));
            int numMes = (Integer) ingresoMap.get("month");
            row.createCell(1).setCellValue(nombresMeses[numMes - 1]);

            // Crear celda de ingresos
            Cell ingresosCell = row.createCell(2);
            ingresosCell.setCellValue((Double) ingresoMap.get("ingresos"));

            // Aplicar formato de número y borde a la celda de ingresos
            ingresosCell.setCellStyle(currencyStyle);

            totalIngresos += (Double) ingresoMap.get("ingresos");
        }

        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getColumnIndex() != 2) { // Evitar la celda de ingresos
                    cell.setCellStyle(borderStyle);
                }
            }
        }

        // Crear una fila para el total
        Row formulaTotalRow = sheet.createRow(rowNum + 2);
        formulaTotalRow.createCell(1).setCellValue("Total:");

        // Agregar símbolo de moneda y fórmula para el total
        Cell totalCell = formulaTotalRow.createCell(2);
        totalCell.setCellFormula("SUBTOTAL(109,C2:C" + (rowNum) + ")");
        totalCell.setCellType(CellType.FORMULA);

        // Aplicar el formato de número y borde al total
        totalCell.setCellStyle(currencyStyle);

        // Evaluar la fórmula para obtener el resultado
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateFormulaCell(totalCell);

        // Obtener el valor calculado de la celda
        double totalValue = totalCell.getNumericCellValue();

        // Actualizar el valor de la celda con el formato de moneda
        totalCell.setCellValue("S/." + String.format("%.2f", totalValue));

        // Crear un filtro para las columnas
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:C" + rowNum));
        for (Cell cell : formulaTotalRow) {
            cell.setCellStyle(borderStyle);
        }
        // Ajustar el ancho de las columnas
        for (int i = 0; i <= 2; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\factura\\ingresos.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel generado con éxito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        rptExcel generador = new rptExcel();

        try {
            generador.generarExcelIngresos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
