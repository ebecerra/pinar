package com.befasoft.common.tools;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipManager {

    private static Log log = LogFactory.getLog(ZipManager.class);

    private String fileNameCompressed;
    private OutputStream fileOS;
    private ZipOutputStream zipOS;

    byte[] buffer;
    int rBytes;

    /**
     * Constructor ZipManager
     * @param fileName Nombre del fichero .ZIP a manipular
     */
    public ZipManager(String fileName) {
        try {
            fileNameCompressed = fileName;
            fileOS = new FileOutputStream(fileName);
            zipOS = new ZipOutputStream(fileOS);
            buffer = new byte[Constants.MAX_LENGTH_READ];
            rBytes = 0;
        } catch (Exception e) {
            log.error("Error al crear el fichero ZIP:" + e.getMessage());
        }
    }

    public ZipManager(OutputStream fileOS) {
        this.fileOS = fileOS;
        zipOS = new ZipOutputStream(fileOS);
        buffer = new byte[Constants.MAX_LENGTH_READ];
        rBytes = 0;
    }

    public ZipManager(File file) {
        try {
            fileNameCompressed = file.getPath();
            fileOS = new FileOutputStream(file);
            zipOS = new ZipOutputStream(fileOS);
            buffer = new byte[Constants.MAX_LENGTH_READ];
            rBytes = 0;
        } catch (Exception e) {
            log.error("Error al crear el fichero ZIP:" + e.getMessage());
        }
    }

    /**
     * Obtiene el camino completo del fichero Zip
     * @return Nombre del fichero
     */
    public String getZipFile() {
        return fileNameCompressed;
    }

    /**
     * Agrega el contenido de un directorio al .ZIP
     * @param fileDirName Nombre del directorio donde estan los ficheros a comprimir
     * @throws Exception Error en la grstion de ficheros
     */
    public void addDirContentToZipFile(String fileDirName) throws Exception {
        File fileDir = new File(fileDirName);
        if ((fileDir.exists()) && (fileDir.isDirectory())) {
            addDirContentToZipFile(fileDir);
        }
    }

    /**
     * Agrega el contenido de un directorio al .ZIP
     * @param fileDir Directorio donde estan los ficheros a comprimir
     * @throws Exception Error en la grstion de ficheros
     */
    public void addDirContentToZipFile(File fileDir) throws Exception {
        if ( (fileDir.exists()) && (fileDir.isDirectory()) ) {
            File[] files = fileDir.listFiles();
            for (File file : files) {
                if ((file.isFile()) && (!file.isHidden())) {
                    addFileToZipFile(file);
                }
            }
        }
    }

    /**
     * Agrega un fichero al .ZIP
     * @param fileName Nombre del fichero a comprimir
     * @throws Exception Error en la grstion de ficheros
     */
    public void addFileToZipFile(String fileName) throws Exception {
        File file = new File(fileName);
        if ( ( file.exists() ) && ( file.isFile() ) ) {
            addFileToZipFile(file);
        }
    }

    /**
     * Agrega un fichero al .ZIP
     * @param file Fichero a comprimir
     * @throws Exception Error en la gestion de ficheros
     */
    public void addFileToZipFile(File file) throws Exception {
        if ( ! file.getAbsolutePath().toLowerCase().equals(fileNameCompressed.toLowerCase()) ) {
            FileInputStream fileIS = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOS.putNextEntry(zipEntry);
            while ((rBytes = fileIS.read(buffer)) != -1)
                zipOS.write(buffer, 0, rBytes);
            zipOS.closeEntry();
            fileIS.close();
        }
    }

    public ZipEntry addEntry(String entry) throws Exception {
        ZipEntry zipEntry = new ZipEntry(entry);
        zipOS.putNextEntry(zipEntry);
        return zipEntry;
    }

    /**
     * Agrega datos al .ZIP
     * @param buffer Bytes a escribir
     * @throws Exception Error en la gestion de ficheros
     */
    public void writeToOS(byte[] buffer) throws Exception {
        zipOS.write(buffer);
    }

    public void closeEntry() throws Exception {
        zipOS.closeEntry();
    }

    /**
     * Cierra los objetos involucrados en el proceso
     * @throws Exception Error en la grstion de ficheros
     */
    public void closeZip() throws Exception {
        zipOS.close();
        fileOS.close();
    }

    public ZipOutputStream getZipOS() {
        return zipOS;
    }
}
