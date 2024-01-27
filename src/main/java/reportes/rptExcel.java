package reportes;

import Service.AsistenciaService;
import Service.FacturaService;
import Service.MatriculaService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class rptExcel {

    FacturaService objFactura = new FacturaService();
    MatriculaService objMatricula = new MatriculaService();
    AsistenciaService objAsistencia = new AsistenciaService();
    
    String ruta = "C:\\Users\\wtke9\\OneDrive\\Documentos\\NetBeansProjects\\sisgym_\\sisgym\\src\\main\\webapp\\factura\\";

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

        try (FileOutputStream fileOut = new FileOutputStream(ruta + "ingresos.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel generado con éxito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarExcelMembresias() throws IOException {
        List<Map<String, Object>> membresiasList = objMatricula.countMembresias();

        Workbook workbook = WorkbookFactory.create(true);

        Sheet sheet = workbook.createSheet("Membresias");

        // Ajustar el ancho de la columna A
        sheet.setColumnWidth(0, 3000);

        // Crear estilo para el encabezado con negrita y fondo color turquesa
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);

        // Crear la fila de encabezado
        Row headerRow = sheet.createRow(1);
        Cell tipoMembresiaHeader = headerRow.createCell(1);
        tipoMembresiaHeader.setCellValue("Tipo de Membresia");
        tipoMembresiaHeader.setCellStyle(headerStyle);

        Cell cantidadHeader = headerRow.createCell(2);
        cantidadHeader.setCellValue("Cantidad");
        cantidadHeader.setCellStyle(headerStyle);

        CellStyle goldStyle = workbook.createCellStyle();
        goldStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        goldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle platinumStyle = workbook.createCellStyle();
        platinumStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        platinumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle basicStyle = workbook.createCellStyle();
        basicStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        basicStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Llenar la hoja de trabajo con los datos
        int rowNum = 2;

        for (Map<String, Object> membresiaMap : membresiasList) {
            Row row = sheet.createRow(rowNum++);
            String tipoMembresia = (String) membresiaMap.get("tipoMembresia");
            row.createCell(1).setCellValue(tipoMembresia);

            // Asignar estilo según el tipo de membresía
            Cell cantidadCell = row.createCell(2);
            cantidadCell.setCellValue((Long) membresiaMap.get("cantidad"));

            if ("Gold".equals(tipoMembresia)) {
                cantidadCell.setCellStyle(goldStyle);
            } else if ("Platino".equals(tipoMembresia)) {
                cantidadCell.setCellStyle(platinumStyle);
            } else if ("Básico".equals(tipoMembresia)) {
                cantidadCell.setCellStyle(basicStyle);
            }
        }

        // Ajustar el ancho de las columnas
        for (int i = 0; i <= 1; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar el libro de trabajo en un archivo
        try (FileOutputStream fileOut = new FileOutputStream(ruta + "membresias.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Informe de membresías generado con éxito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void generarExcelAsistencia() {
        List<Map<String, Object>> asistenciaList = objAsistencia.countAsistencia();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Asistencia");

        // Crear estilo para el encabezado con negrita y fondo color turquesa
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);

        // Crear la fila de encabezado
        Row headerRow = sheet.createRow(0);
        Cell fechaHeader = headerRow.createCell(0);
        fechaHeader.setCellValue("Fecha");
        fechaHeader.setCellStyle(headerStyle);

        Cell cantidadHeader = headerRow.createCell(1);
        cantidadHeader.setCellValue("Cantidad");
        cantidadHeader.setCellStyle(headerStyle);

        // Llenar la hoja de trabajo con los datos
        int rowNum = 1;

        for (Map<String, Object> asistenciaMap : asistenciaList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(asistenciaMap.get("fecha").toString());
            row.createCell(1).setCellValue((Long) asistenciaMap.get("cantidad"));
        }

        // Ajustar el ancho de las columnas
        for (int i = 0; i <= 1; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar el libro de trabajo en un archivo
        try (FileOutputStream fileOut = new FileOutputStream(ruta + "asistencia.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Informe de asistencia generado con éxito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        rptExcel generador = new rptExcel();

        generador.generarExcelAsistencia();
    }
}
