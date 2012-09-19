package com.befasoft.common.actions;

import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.UnZipManager;
import com.opensymphony.xwork2.Action;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintWriter;

public abstract class BaseUploadManager extends BaseManagerAction {

    protected File upload;
    protected String uploadContentType;
    protected String uploadFileName;

    protected String destFileName, contentType;
    protected boolean unzip, cleardir;
    protected File directory;
    protected long maxSize;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if ("upload".equals(action)) {
            uploadFile();
            return null;
        }

        return result;
    }

    /**
     * Obtiene en nombre del fichero donde se almacena el fichero subido
     * @return Ruta completa de acceso al fichero destino
     */
    abstract protected String getDestFileName();

    /**
     * Procesa el contenido del fichero
     * @return Verdadero si se proceso correctamente
     */
    protected boolean processFile() throws Exception {
        return true;
    }

    /**
     * Retorna el texto del error que ha dado procesando el fichero
     * @return Mensaje de error
     */
    protected String getProcessError() {
        return getText("appbs.upload.error.process");
    }

    /**
     * Retorna el texto del error cuando el fichero es muy grande
     * @return Mensaje de error
     */
    protected String getSizeError() {
        return getText("appbs.upload.error.maxsize", new String[] { Long.toString(maxSize) });
    }

    /**
     * Verifica el tipo del fichero
     * @return Verdadero si es un tipo aceptado
     */
    protected boolean isValidContentType() {
        if (!Converter.isEmpty(contentType)) {
            String[] contents = contentType.split(";");
            for (String content : contents) {
                if (content.equals(uploadContentType))
                    return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Verifica el tipo del fichero
     * @return Verdadero si es un tipo aceptado
     */
    protected String getContentTypeError() {
        return getText("appbs.upload.error.contenttype");
    }

    /**
     * Valores extras para retornar en el JSON
     * @return Cadena con los valores extra
     */
    protected String getResultExtra() {
        return "";
    }

    /**
     * Carga el fichero en el servidor
     * @throws Exception Error en la carga del fichero
     */
    private void uploadFile() throws Exception {
        log.debug("uploadFile("+uploadFileName+", "+uploadContentType+")");

        String responseText = "{ \"success\":false,\"msg\":'"+getText("appbs.upload.error.generic")+"'}";

        if (isValidContentType()) {
            // Valida la longitud
            boolean sizeOk = maxSize <= 0;
            if (!sizeOk) {
                if (upload.length() > maxSize * 1024) {
                    String error = getSizeError();
                    responseText = "{ \"success\":false, \"msg\":'"+error+"', \"uploadFileName\":\""+uploadFileName+"\" }";
                    bodyResult.setSuccess(false);
                    bodyResult.setMessage(error);
                    log.error(error);
                } else
                    sizeOk = true;
            }
            if (sizeOk) {
                // Procesa el fichero
                destFileName = getDestFileName();
                log.debug("uploadFile -> "+destFileName);
                try {
                    File desFile = new File(destFileName);
                    FileUtils.copyFile(upload, desFile);

                    if (unzip) {
                        directory = (new File(destFileName)).getParentFile();
                        if (cleardir) {
                            // Borra los ficheros (excepto el .zip)
                            String[] files = directory.list();
                            for (String f : files) {
                                if (!f.equals(uploadFileName)) {
                                    File df = new File(directory.getPath()+"/"+f);
                                    df.delete();
                                }
                            }
                        }
                        UnZipManager unzip = new UnZipManager(destFileName);
                        unzip.extractFilesFromZipFile(directory);
                        unzip.closeZip();
                        File f = new File(destFileName);
                        f.delete();
                    }

                    if (!processFile()) {
                        String error = getProcessError();
                        responseText = "{ \"success\":false, \"msg\":'"+error+"', \"uploadFileName\":\""+uploadFileName+"\" }";
                        bodyResult.setSuccess(false);
                        bodyResult.setMessage(error);
                        log.error(error);
                    } else
                        responseText = "{ \"success\":true, \"msg\":'"+getText("appbs.upload.ok")+"', \"uploadFileName\":\""+uploadFileName+"\", \"extra\": \""+getResultExtra()+"\" }";
                } catch (Throwable e) {
                    bodyResult.setSuccess(false);
                    bodyResult.setMessage(getText("appbs.upload.error.load"));

                    responseText = "{ \"success\":false, \"msg\":'"+getText("appbs.upload.error.load")+"'}";
                    log.error("Error cargando el fichero ", e);
                }
            }
        } else {
            String error = getContentTypeError();
            responseText = "{ \"success\":false,\"msg\":'"+error+"'}";
            bodyResult.setSuccess(false);
            bodyResult.setMessage(error);
            log.error(error);
        }

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        pw.print(responseText);
    }

    /*
     * Metodos Get/Set
     */

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public boolean isUnzip() {
        return unzip;
    }

    public void setUnzip(boolean unzip) {
        this.unzip = unzip;
    }

    public boolean isCleardir() {
        return cleardir;
    }

    public void setCleardir(boolean cleardir) {
        this.cleardir = cleardir;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
