package com.befasoft.common.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZipManager {

    private static Log log = LogFactory.getLog(UnZipManager.class);

    private ZipFile zipFile;
    byte[] buffer;
    int rBytes;

    /**
     * Constructor UnZipManager
     * @param fileName Nombre del fichero .ZIP a manipular
     */
    public UnZipManager(String fileName) {
        try {
            zipFile = new ZipFile(fileName);
            buffer = new byte[Constants.MAX_LENGTH_READ];
            rBytes = 0;
        } catch (Exception e) {
            log.error("Error creando el ZIP:" + e.getMessage());
        }
    }

    /**
     * Extrae el contenido de un .ZIP en un directorio
     * @param fileDirName Nombre del directorio donde descomprimir el .ZIP
     * @throws Exception Error en el tratamiento de los ficheros
     */
    public void extractFilesFromZipFile(String fileDirName) throws Exception {
        File fileDir = new File(fileDirName);
        extractFilesFromZipFile(fileDir);
    }

    /**
     * Extrae el contenido de un .ZIP en un directorio
     * @param fileDir Directorio donde descomprimir el .ZIP
     * @throws Exception Error en el tratamiento de los ficheros
     */
    public void extractFilesFromZipFile(File fileDir) throws Exception {
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // Obtener la Entries del .ZIP
        Enumeration zipEntries = zipFile.entries();
        // Recorrer las Entries obtenidas y extraer el fichero al directorio destino
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry)zipEntries.nextElement();
            FileOutputStream fileOS = new FileOutputStream(fileDir.getAbsolutePath() +
                    File.separator + zipEntry.getName());
            InputStream fileIS = zipFile.getInputStream(zipEntry);
            // Descomprimir el fichero
            while ((rBytes = fileIS.read(buffer)) != -1)
                fileOS.write(buffer, 0, rBytes);
            fileOS.close();
            fileIS.close();
        }
    }

    public InputStream getStreamFromEntry(ZipEntry zipEntry) throws Exception {
        return zipFile.getInputStream(zipEntry);
    }

    public Enumeration getZipEntries() {
        return zipFile.entries();
    }

    /**
     * Cierra los objetos involucrados en el proceso
     * @throws Exception Error en el tratamiento de los ficheros
     */
    public final void closeZip() throws Exception {
        zipFile.close();
    }

}
