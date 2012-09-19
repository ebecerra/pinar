package com.befasoft.common.actions;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.Action;

import javax.servlet.ServletOutputStream;
import java.io.*;


public abstract class BasePdfAction extends BaseManagerAction {

    protected Font fontT1;
    protected Font fontT2;
    protected Font fontBold;
    protected Font fontItem;
    protected Font fontLite;
    protected BaseColor bg;
    protected ByteArrayOutputStream baos = null;
    protected Document document;
    protected PdfWriter writer = null;
    protected Rectangle pgSize;

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

        if ("print".equals(action)) {
            printPdf();
            return null;
        }

        return result;
    }

    /**
     * Genera el PDF
     * @throws Exception  Error al crear el PDF
     * @return Indica si se genero el PDF
     */
    protected boolean printPdf() throws Exception {
        initPdf();
        boolean result = writePdf();
        if (result)
            closePdf();
        return result;
    }

    /**
     * Inicializa el PDF
     * @throws Exception Error al crear el PDF
     */
    protected void initPdf() throws Exception {
        // Carga los tipos de letras
        fontT1 = getFontT1();
        fontT2 = getFontT2();
        fontBold = getFontBold();
        fontItem = getFontItem();
        fontLite = getFontLite();
        bg = getBaseColor();
        // Inicializa el documento
        document = new Document();
        if (toFile) {
            File f = new File(getFile());
            File dir = new File(f.getParent());
            if (!dir.exists())
                dir.mkdirs();
            writer = PdfWriter.getInstance(document, new FileOutputStream(f));
        } else {
            baos = new ByteArrayOutputStream();
            writer = PdfWriter.getInstance(document, baos);
        }
        pgSize = writer.getPageSize();
        document.open();
    }

    /**
     * Genera el PDF
     * @throws Exception Error
     * @return Verdadero si se genero correctamente el documento
     */
    abstract protected boolean writePdf() throws Exception;

    /**
     * Cierra el PDF y los redirige a la salida HTTP
     * @throws Exception Error
     */
    protected void closePdf() throws Exception {
        document.close();
        if (!toFile) {
            if (toRequest) {
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                if (getFile() != null)
                    response.setHeader("Content-disposition", "inline; filename=" + getFile() );
                response.setContentType("application/pdf");
                response.setContentLength(baos.size());
                ServletOutputStream out = response.getOutputStream();
                baos.writeTo(out);
                out.flush();
            }
        } else {
            writer.flush();
            writer.close();
        }
    }

    /**
     * Obtiene una tabla con el valor pasado como argumento
     * @param value Contenido
     * @param width Ancho
     * @param font Font a utilizar
     * @param align Alineacion de la celda
     * @return PDF Table
     */
    protected PdfPTable buildTable(String value, float width, Font font, int align) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(width);
        table.addCell(getCell(font, align, value));
        return table;
    }

    /**
     * Dibuja un rectangulo redondeado
     * @param cb Area de dibujo
     * @param left X de la izq.
     * @param top Y de la izq.
     * @param right X de la derecha
     * @param bottom Y de la derecha
     * @param round Puntos de las esquinas
     */
    protected void drawRoundRect(PdfContentByte cb, int left, int top, int right, int bottom, int round) {
        cb.moveTo(left + round, top); cb.lineTo(right - round, top);
        cb.curveTo(right, top, right, top - round);
        cb.lineTo(right, bottom + round);
        cb.curveTo(right, bottom, right - round, bottom);
        cb.lineTo(left + round, bottom);
        cb.curveTo(left, bottom, left, bottom + round);
        cb.lineTo(left, top - round);
        cb.curveTo(left, top, left + round, top);
        cb.stroke();
    }

    /**
     * Dibuja un rectangulo
     * @param cb Area de dibujo
     * @param left X de la izq.
     * @param top Y de la izq.
     * @param right X de la derecha
     * @param bottom Y de la derecha
     */
    protected void drawRect(PdfContentByte cb, int left, int top, int right, int bottom) {
        cb.moveTo(left, top); cb.lineTo(right, top);
        cb.lineTo(right, bottom);
        cb.lineTo(left, bottom);
        cb.lineTo(left, top);
        cb.stroke();
    }

    /**
     * Dibuja una linea
     * @param cb Area de dibujo
     * @param left X de la izq.
     * @param top Y de la izq.
     * @param right X de la derecha
     * @param bottom Y de la derecha
     */
    protected void drawLine(PdfContentByte cb, int left, int top, int right, int bottom) {
        cb.moveTo(left, top); cb.lineTo(right, top);
        cb.stroke();
    }

    /*
     * Obtiene una celda de una tabla
     */
    protected PdfPCell getCellPadding(Font font, int align, int padding, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        cell.setPadding(padding);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    protected PdfPCell getCell(Font font, int align, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    protected PdfPCell getCell(Font font, int align, String text, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    protected PdfPCell getCell(Font font, int align, String text, int border, BaseColor bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        cell.setHorizontalAlignment(align);
        cell.setBackgroundColor(bg);
        return cell;
    }

    protected PdfPCell getExpandCell(Font font, int align, String text, int cols) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        cell.setHorizontalAlignment(align);
        cell.setColspan(cols);
        return cell;
    }

    protected PdfPCell getExpandCell(Font font, int align, String text, int cols, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        cell.setHorizontalAlignment(align);
        cell.setColspan(cols);
        return cell;
    }

    protected PdfPCell getExpandCell(Font font, int align, String text, int border, BaseColor bg, int cols) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        cell.setHorizontalAlignment(align);
        cell.setBackgroundColor(bg);
        cell.setColspan(cols);
        return cell;
    }

    /**
     * Envia el PDF como respuesta
     * @return Si hubo error
     */
    public boolean loadPDF() {
        // Abre el fichero
        File pdf = new File(file);
        if (!pdf.exists()) {
            return false;
        }
        // Content-type
        response.setContentType("application/pdf");
        try {
            FileInputStream input = new FileInputStream(pdf);
            ServletOutputStream output = response.getOutputStream();
            byte buf[] = new byte[4096];
            int nSize = input.read(buf);
            while(nSize >= 0) {
                output.write(buf, 0, nSize);
                nSize = input.read(buf);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Adiciona una fila vacia a la tabla de lineas de factura
     * @param table Tabla con las lineas de factura
     * @param cols Cantidad de columnas
     * @param font Font que se utiliza
     * @param border Border a aplicar
     */
    protected void addEmtyRow(PdfPTable table, int cols, Font font, int border) {
        for (int i = 0; i < cols; i++)
            table.addCell(getCell(font, Element.ALIGN_CENTER, " ", border));
    }

    /*
     * Obtiene los Font, Colores, Tamaño de pagina, etc.
     */
    protected Font getFontT1() {
        return FontFactory.getFont("Times new roman", "Times new roman", 12, Font.NORMAL);
    }

    protected Font getFontT2() {
        return FontFactory.getFont("Times new roman", "Times new roman", 14, Font.BOLD);
    }

    protected Font getFontBold() {
        return FontFactory.getFont("Times new roman", "Times new roman", 10, Font.BOLD);
    }

    protected Font getFontItem() {
        return FontFactory.getFont("Times new roman", "Times new roman", 10, Font.NORMAL);
    }

    protected Font getFontLite() {
        return FontFactory.getFont("Times new roman", "Times new roman", 8, Font.NORMAL);
    }

    protected BaseColor getBaseColor() {
        return new BaseColor(0xD0, 0xD0, 0xD0);
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
}
