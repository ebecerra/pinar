package com.befasoft.common.actions;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Converter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.servlet.ServletOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


public abstract class BaseExcelAction extends BaseManagerAction {

    protected HSSFWorkbook wb;
    protected HSSFSheet sheet;
    protected HSSFCellStyle csCaption;
    protected HSSFCellStyle csColTitle;
    protected HSSFCellStyle csBold;
    protected HSSFCellStyle csNormal;
    protected HSSFCellStyle csNormalRed;
    protected HSSFCellStyle csNormalDate;
    protected String sheetName = "Hoja1";
    protected String file;
    protected boolean toFile = false;
    protected boolean toRequest = true;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {
        String result = super.execute();

        if ("export".equals(action)) {
            if (exportToExcel())
                return null;
        }

        return result;
    }

    /**
     * Genera el Excel
     * @throws Exception  Error al crear el PDF
     * @return Verdadero si termino la exportacion correctamente
     */
    protected boolean exportToExcel() throws Exception {
        initBook(getSheetName());
        boolean result = writeExcel();
        closeExcel();
        return result;
    }

    /**
     * Inicializa una libro excel con una hoja
     * @param sheetName Nombre de la hoja
     */
    protected void initBook(String sheetName) {
        // Crea el libro con una hoja
        wb = new HSSFWorkbook();
        sheet = wb.createSheet(sheetName);
        // Crea estilos
        csCaption = getStyleCaption();
        csColTitle = getStyleColTitle();
        csBold = getStyleBold();
        csNormal = getStyleNormal();
        csNormalRed = getStyleNormalRed();
        csNormalDate = getStyleNormalDate();
    }

    /**
     * Genera el Excel
     * @throws Exception Error
     * @return Verdadero si se genero correctamente el documento
     */
    abstract protected boolean writeExcel() throws Exception;

    /**
     * Cierra el Excel y los redirige a la salida HTTP o fichero
     * @throws Exception Error
     */
    protected void closeExcel() throws Exception {
        if (!toFile) {
            if (toRequest) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Cache-Control", "no-cache");
                if (getFile() != null)
                    response.setHeader("Content-disposition", "inline; filename=" + getFile() );
                response.setHeader("Expires", "0");
                response.setHeader("Pragma", "public");
                ServletOutputStream output = response.getOutputStream();
                wb.write(output);
                output.flush();
            }
        } else {
            FileOutputStream output = new FileOutputStream(getFile());
            wb.write(output);
            output.flush();
            output.close();
        }
    }

    /**
     * Exporta una lista de bean a un fichero Excel
     *
     * @param title Titulo de la hoja
     * @param prefix Prefijo utilizado para recurperar los properties
     * @param items Lista de elementos a exportar
     * @param bean Clase de los bean de la lista de Items
     * @throws Exception Error
     * @return Indice de la ultima fila insertada
     */
    protected int exportItems(String title, String prefix, List<EntityBean> items, EntityBean bean) throws Exception {
        // Titulo del informe
        HSSFRow row = sheet.createRow(0);
        createCell(row, 0, title, csCaption);
        sheet.createRow(1);
        sheet.createRow(2);
        return bean == null ? 3 : exportItems(3, prefix, items, bean);
    }

    protected int exportItems(String title, String prefix, List<EntityBean> items) throws Exception {
        return exportItems(title, prefix, items, items.size() > 0 ? items.get(0) : null);
    }

    protected int exportItems(int rowIndx, String prefix, List<EntityBean> items, EntityBean bean) throws Exception {
        if (items.size() == 0)
            return rowIndx;
        // Cabecera y lineas
        String[] fields = bean.getExportFields();
        String[] formulas = bean.getFormulas();
        int[] width = bean.getColWidth();
        Class cls = bean.getClass();
        createRowCaptions(rowIndx++, prefix, fields, bean.getFormulaCaptions());
        // Obtiene los metodos GET
        Method[] methods = new Method[fields.length];
        int index = 0;
        for (String field : fields) {
            methods[index++] = cls.getMethod("get" + Converter.capitalize(field));
        }
        // Exporta los datos
        for (EntityBean item : items) {
            HSSFRow row = sheet.createRow(rowIndx++);
            int colIndex = 0;
            for (int i = 0; i < fields.length; i++) {
                Object obj = methods[i].invoke(item);
                if (obj == null) {
                    createCell(row, colIndex++, "", csNormal);
                } else {
                    if (obj instanceof Date)
                        createCell(row, colIndex++, (Date) obj, csNormalDate);
                    else if (obj instanceof Double)
                        createCell(row, colIndex++, (Double) obj, csNormal);
                    else if (obj instanceof Integer)
                        createCell(row, colIndex++, (Integer) obj, csNormal);
                    else if (obj instanceof Long)
                        createCell(row, colIndex++, (Long) obj, csNormal);
                    else
                        createCell(row, colIndex++, obj.toString(), csNormal);
                }
            }
            // Aplica las formulas
            if (formulas != null) {
                for (String formula : formulas) {
                    createFormula(row, colIndex++, rowIndx-1, formula, csNormal);
                }
            }
        }
        // Aplica los anchos de columnas
        if (width != null) {
            int indx = 0;
            for (int w : width) {
                sheet.setColumnWidth(indx++, (int) ((w * 8) / ((double) 1 / 20)));
            }
        }
        return rowIndx;
    }

    protected int exportItems(int rowIndx, String prefix, List<EntityBean> items) throws Exception {
        if (items.size() > 0)
            return exportItems(rowIndx, prefix, items, items.get(0));
        else
            return rowIndx;
    }

    /**
     * Crea una fila con las cabeceras de las columnas
     * @param rowIndex Indice de la fila
     * @param prefix Prefijo para obtener los valores de fichero .properties
     * @param cols Nombres de los campos
     * @param formulas Nombre de las formulas
     * @return Fila Excel
     */
    protected HSSFRow createRowCaptions(int rowIndex, String prefix, String[] cols, String[] formulas) {
        HSSFRow row = sheet.createRow(rowIndex);
        int colIndx = 0;
        for (String col : cols) {
            createCell(row, colIndx++, getText(prefix + col), csColTitle);
        }
        if (formulas != null) {
            for (String col : formulas) {
                createCell(row, colIndx++, getText(col), csColTitle);
            }
        }
        return row;
    }


    /*
     * Crea una celda con un estilo determinado
     */
    protected void createCell(HSSFRow row, int col, String value, HSSFCellStyle cs) {
        HSSFCell c = row.createCell(col);
        c.setCellValue(new HSSFRichTextString(value));
        c.setCellStyle(cs);
    }

    protected void createCell(HSSFRow row, int col, double value, HSSFCellStyle cs) {
        HSSFCell c = row.createCell(col);
        c.setCellValue(value);
        c.setCellStyle(cs);
    }

    protected void createCell(HSSFRow row, int col, Date value, HSSFCellStyle cs) {
        HSSFCell c = row.createCell(col);
        if (value == null)
            c.setCellValue("");
        else
            c.setCellValue(value);
        c.setCellStyle(cs);
    }

    protected void createCell(HSSFRow row, int col, Long value, HSSFCellStyle cs) {
        HSSFCell c = row.createCell(col);
        c.setCellValue(value != null ? value : 0);
        c.setCellStyle(cs);
    }

    protected void createFormula(HSSFRow row, int col, int rowIndx, String value, HSSFCellStyle cs) {
        value = value.replaceAll("%", Integer.toString(rowIndx+1));
        HSSFCell c = row.createCell(col);
        c.setCellFormula(value);
        c.setCellStyle(cs);
    }

    /*
     * Crea una fila con una sola celda con un estilo determinado
     */
    protected HSSFRow createRow(HSSFSheet sheet, int indx, int col, String value, HSSFCellStyle cs) {
        HSSFRow row = sheet.createRow(indx);
        HSSFCell c = row.createCell(col);
        c.setCellValue(new HSSFRichTextString(value));
        if (cs != null)
            c.setCellStyle(cs);
        return row;
    }

    /**
     * Metodos para obtener los diferentes estilos predefinidos
     * @return Estilo
     */
    protected HSSFCellStyle getStyleCaption() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
        return style;
    }

    protected HSSFCellStyle getStyleColTitle() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    protected HSSFCellStyle getStyleBold() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFFont.COLOR_NORMAL);
        style.setFont(font);
        return style;
    }

    protected HSSFCellStyle getStyleNormal() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFFont.COLOR_NORMAL);
        style.setFont(font);
        return style;
    }

    protected HSSFCellStyle getStyleNormalRed() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFFont.COLOR_RED);
        style.setFont(font);
        return style;
    }

    protected HSSFCellStyle getStyleNormalDate() {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFFont.COLOR_NORMAL);
        style.setFont(font);
        style.setDataFormat((short) 14);
        return style;
    }

    /**
     * Obtiene el valor de una celda
     * @param cell Celda
     * @return Valor
     */
    public static Object getCellValue(Cell cell) {
        if (cell != null) {
            switch(cell.getCellType()) {
                case Cell.CELL_TYPE_STRING: return cell.getRichStringCellValue().getString();
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    } else {
                        return cell.getNumericCellValue();
                    }
                case Cell.CELL_TYPE_BOOLEAN: return cell.getBooleanCellValue();
                case Cell.CELL_TYPE_FORMULA: cell.getCellFormula();
            }
        }
        return null;
    }

    /*
     * Metodos Get/Set
     */
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isToFile() {
        return toFile;
    }

    public void setToFile(boolean toFile) {
        this.toFile = toFile;
    }

    public boolean isToRequest() {
        return toRequest;
    }

    public void setToRequest(boolean toRequest) {
        this.toRequest = toRequest;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
