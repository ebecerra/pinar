package com.befasoft.common.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTool {

    private static Log log = LogFactory.getLog(FileTool.class);

    public static String dir = "";
    public static String dateInfo = "";

    /**
     * Obtiene la extension de un fichero
     * @param file Fichero
     * @return Extension del fichero
     */
    public static String getFileExt(File file) {
        int dotPos = file.getName().lastIndexOf(".");
        return file.getName().substring(dotPos);
    }

    public static String getFileExt(String fName) {
        int dotPos = fName.lastIndexOf(".");
        return fName.substring(dotPos);
    }

    /**
     * Obtiene un nombre unico para el fichero
     * @param prefix Prefijo
     * @param ext Extension
     * @return Nombre del fichero
     */
    public static String getFileName(String prefix, String ext) {
        String fname = dir+"/"+prefix+dateInfo;
        File file = new File(fname+"_1"+ext);
        for (int i = 2; file.exists(); i++)
            file = new File(fname+"_"+i+ext);
        return file.getName();
    }

    /**
     * Actualiza la informacion de fecha (dia de hoy)
     */
    public static void setDateInfo() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        dateInfo = df.format(new Date());
    }

    /**
     * Borra el contenido de un directorio
     * @param path Directorio
     * @return Verdadero si borro todos los ficheros
     */
    public static boolean deleteDirContent(String path) {
        boolean result = true;
        File f = new File(path);
        String files[] = f.list();
        if (files != null) {
            for (String file : files) {
                File f2 = new File(path + "/" + file);
                if (f2.isDirectory())
                    result = deleteDirContent(path + "/" + file);
                if (result)
                    result = f2.delete();
                if (!result)
                    return false;
            }
        }
        return true;
    }

    /**
     * Borra el contenido de un directorio y el directorio incluido
     * @param path Directorio
     * @return Verdadero si borro todos los ficheros
     */
    public static boolean deleteDir(String path) {
        boolean result = deleteDirContent(path);
        if (result) {
            result = deleteFile(path);
        }
        return result;
    }

    /**
     * Borra un fichero
     * @param path Nombre del fichero
     * @return Verdadero si borro el fichero
     */
    public static boolean deleteFile(String path) {
        File f = new File(path);
        return !f.exists() || f.delete();
    }

    /**
     * Verifica si existe el PATH y lo crea si no existe
     * @param path PATH a verificar
     * @return true si se creo correctamente
     */
    public static boolean buildPath(String path) {
        File dir = new File(path);
        return dir.isDirectory() || dir.mkdirs();
    }

    /**
     * Escribe a un fichero un StringBuffer
     * @param fName Nombre del fichero
     * @param ficLines Datos a escribir
     * @throws java.io.IOException Error al escribir
     */
    public static void writeTxtToFile(String fName, StringBuffer ficLines) throws IOException {
        // Escribe al fichero
        FileWriter out = new FileWriter(fName);
        out.write(ficLines.toString());
        out.flush(); out.close();
    }

    /**
     * Carga el contenido de un fichero de texto
     * @param path Camino del fichero
     * @return Contenido del fichero
     * @throws Exception Error al leer el fichero
     */
    public static String loadTextFile(String path) throws Exception {
        //Leemos el fichero xml
        FileReader fR = null;
        StringBuffer content = new StringBuffer();
        fR = new FileReader(path);
        BufferedReader bufRead = new BufferedReader(fR);

        String line;
        while ((line = bufRead.readLine()) != null) {
            content.append(line).append("\n");
        }
        return content.toString();
    }

    public static String loadTextFile(File f) throws Exception {
        return loadTextFile(f.getPath());
    }

    /**
     * Copia un fichero
     * @param f1 Fichero origen
     * @param f2 Fichero destino
     * @return Verdadero si la copia fue correcta
     */
    public static boolean copyFile(File f1, File f2) {
        try{
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[10*1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return true;
        }
        catch(FileNotFoundException ex){
            log.error("Error copiando el fichero: "+f1.getPath()+" a "+f2.getPath(), ex);
            return false;
        }
        catch(IOException e){
            log.error("Error copiando el fichero: "+f1.getPath()+" a "+f2.getPath(), e);
            return false;
        }
    }

    public static boolean copyFile(File f1, String outName) {
        File f2 = new File(outName);
        return copyFile(f1, f2);
    }

    public static boolean copyFile(String inName, String outName) {
        File f1 = new File(inName);
        File f2 = new File(outName);
        return copyFile(f1, f2);
    }

    /**
     * Copia el contenido de un directorio a otro
     * @param sDir Directorio de origen
     * @param dDir Directorio de destino (sino existe se crea)
     * @return Verdadero si se copi el directorio
     */
    public static boolean copyDirectory(File sDir, File dDir) {
        if (!dDir.exists())
            dDir.mkdirs();
        if (sDir.exists() && sDir.isDirectory()) {
            File[] files = sDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (!copyDirectory(file, new File(dDir+"/"+file.getName())))
                        return false;
                } else if (!copyFile(file, dDir+"/"+file.getName()))
                    return false;
            }
            return true;
        }
        return false;
    }
}
