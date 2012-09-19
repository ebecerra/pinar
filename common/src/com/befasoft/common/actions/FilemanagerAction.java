package com.befasoft.common.actions;

import com.befasoft.common.beans.UTL_FILE;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilemanagerAction extends BaseUploadManager {

    protected String directory, content, filename;
    protected String rootDir;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if ("delete".equals(action)) {
            deleteFile();
            result = Action.SUCCESS;
        }
        if ("download".equals(action)) {
            downloadFile();
            return null;
        }

        return result;
    }

    /**
     * Descarga un fichero
     * @throws Exception Error
     */
    protected void downloadFile() throws Exception {
        log.debug("downloadFile("+directory+", "+filename+")");
        String path = "";
        if ("WEBAPP".equals(rootDir))
            path = Constants.realAppPath;
        path += directory + '/' + filename;
        File showFile = new File(path);

        if (!showFile.exists()) {
            log.debug("Error el fichero NO existe , filePath: " + path);
            return;
        }

        InputStream sout = new FileInputStream(showFile);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(new MimetypesFileTypeMap().getContentType(showFile));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + showFile.getName() + "\"");
        if (content != null)
            response.setContentType(content);

        BufferedInputStream bis = new BufferedInputStream(sout);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead, totalBytes = 0;

        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
            totalBytes += bytesRead;
        }
        response.setContentLength(totalBytes);

        bis.close();
        bos.close();
        sout.close();
    }

    /**
     * Borra un fichero del directorio
     */
    protected void deleteFile() {
        log.debug("deleteFile("+directory+", "+uploadFileName+")");
        File f = new File(getDestFileName());
        f.delete();
    }

    /**
     * Retorna el listado de elementos
     *
     * @return Lista de elementos
     * @throws Exception Error en la llamada a los bean
     */
    protected String executeList() throws Exception {
        File dir = new File(Constants.realAppPath + File.separator + directory);
        List<UTL_FILE> allElements = new ArrayList<UTL_FILE>();
        if (dir.exists() && dir.isDirectory()) {
            String files[] = dir.list();
            for (String file : files) {
                allElements.add(new UTL_FILE(new File(dir.getPath() + File.separator + file)));
            }
        }
        bodyResult.setElements(allElements);
        bodyResult.setTotalCount(allElements.size());
        return Action.SUCCESS;
    }

    /**
     * Obtiene en nombre del fichero donde se almacena el fichero subido
     * @return Ruta completa de acceso al fichero destino
     */
    @Override
    protected String getDestFileName() {
        return Constants.realAppPath + File.separator + directory + File.separator + uploadFileName;
    }

    /**
     * Verifica el tipo del fichero
     * @return Verdadero si es un tipo aceptado
     */
    @Override
    protected boolean isValidContentType() {
        return Converter.isEmpty(content) || uploadContentType.startsWith(content);
    }

    /**
     * Retorna la clase asociada al bean..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return null;
    }

    /**
     * Retorna la clase asociada al manager del bean..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return null;
    }

    /*
     * Metodos Get/Set
     */

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}