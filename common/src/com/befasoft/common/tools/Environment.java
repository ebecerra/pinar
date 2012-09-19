/*
 * Project Info:  http://www.drivingmobile.com/mobile
 * Project Date:  05/03/2007
 * Project Lead:  Tomas Couso (tomas.couso@vincomobile.com);
 *
 * (C) Copyright 2007, by Driving Mobile S.L.
 *
 * $Id:$
 *
 * Changes
 * --------------------------
 * $Log:$
 *
 */
package com.befasoft.common.tools;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * Carga el fichero de configuracion global. El fichero es buscado en el CLASS_PATH.
 *
 * @author <a href="mailto:tomas.couso@vincomobile.com">Tomas Couso Alvarez</a>
 */
public class Environment extends Properties implements Serializable {
    /**
     * Create an empty Config object.
     */
    public Environment() {
    }

    /**
     * Crea una configuracion a partir de un archivo.
     */
    public Environment(String configFile) throws IOException {
        this();

        load(configFile);
    }

    /**
     * M�todo: Load - Carga el fichero de configuraci�n teniendo en cuenta
     * si tiene INCLUDE's o no
     *
     * @param configFile a value of type 'String'
     * @throws java.io.FileNotFoundException if an error occurs
     * @throws java.io.IOException           if an error occurs
     */
    public void load(String configFile)
            throws FileNotFoundException, IOException {

        //Leemos el fichero de configuraci�n la primera vez
        InputStream in = openResourceFile(configFile);

        //System.out.println("Loading properties from " + configFile);
        load(in);

        File file = new File(configFile);
        String path = file.getAbsolutePath();
        path = path.substring(0, path.length() - file.getName().length());

        //log.debug("getProperty(\"INCLUDE\") = " + getProperty("INCLUDE"));

        // Comprobar si el fichero original contiene INCLUDE'S
        while (containsKey("INCLUDE")) {
            //log.debug("contiene includes");
            String[] includes =
                    TypeParser.convertToArrayString(getEntry("INCLUDE"));
            remove("INCLUDE");

            for (int i = 0; i < includes.length; i++) {
                /* Creamos un nuevo objeto para comprobar si a su vez tiene
                INCLUDES */
                Environment tmp = new Environment();
                file = new File(includes[i]);
                if (file.exists()) {
                    tmp.load(includes[i]);
                } else {
                    tmp.load(path + includes[i]);
                }

                // Obtenemos todas sus claves
                // Por cada clave insertamos el par clave-valor
                for (Enumeration keyList = tmp.keys(); keyList.hasMoreElements();) {
                    String key = (String) keyList.nextElement();
                    put(key, tmp.getProperty(key));
                }
            }
        } // while
    } // load

    /**
     * Return string value of param.
     *
     * @param name Name of param
     * @return String value of param. If type of param is String then substitute
     *         the internal reference of params.<br> If type of param is not String then<br>
     *         if exist default formater in <code>FormaterManager</code> use it<br>
     *         else<br>
     *         return <code>value.toString()</code>
     */
    public String getEntry(String name) {
        Object result = get(name);
        if (result == null) {
            return null;
        } else if (result instanceof String) {
            return replaceParamRef(result.toString().trim());
        } else {
            return result.toString();
        }
    }

    /**
     * Replace <i>param references</i> by its values
     *
     * @param source Original string with reference to other params.
     * @return Source with reference expanded.
     */
    public String replaceParamRef(String source) {
        // If no have parameter reference then return same data
        if (source.indexOf("$") == -1) {
            return source;
        }
        String result = "";

        // Replace parameters clossed in $
        String[] parts = convertToArray(source, "$");

        for (int i = 0; i < (parts.length - 1) / 2; i++) {
            String key = parts[i * 2 + 1];
            String refParam = getEntry(key);
            if (refParam != null) {
                parts[i * 2 + 1] = refParam;
            } else {
                parts[i * 2 + 1] = "$" + key + "$";
            }
        }
        for (int i = 0; i < parts.length; i++) {
            result = result + parts[i];
        }
        return result;
    }

   /**
     * Replace <i>param references</i> by its values
     *
     * @param source Original string with reference to other params.
     * @return Source with reference expanded.
     */
    public String replaceAllParamRef(String source, String defaultValue) {
        // If no have parameter reference then return same data
        if (source.indexOf("$") == -1) {
            return source;
        }
        String result = "";

        // Replace parameters clossed in $
        String[] parts = convertToArray(source, "$");

        for (int i = 0; i < (parts.length - 1) / 2; i++) {
            String key = parts[i * 2 + 1];
            String refParam = getEntry(key);
            if (refParam != null) {
                parts[i * 2 + 1] = refParam;
            } else {
                parts[i * 2 + 1] = defaultValue;
            }
        }
        for (int i = 0; i < parts.length; i++) {
            result = result + parts[i];
        }
        return result;
    }

     /**
     * Replace <i>param references</i> by its values
     *
     * @param source Original string with reference to other params.
     * @return Source with reference expanded.
     */
    public String replaceParamRef(String source, String startDelimit, String endDelimit) {
        // If no have parameter reference then return same data
        if (source.indexOf("$") == -1) {
            return source;
        }
        String result = "";

        // Replace parameters clossed in $
        String[] parts = convertToArray(source, "$");

        for (int i = 0; i < (parts.length - 1) / 2; i++) {
            String key = parts[i * 2 + 1];
            String refParam = getEntry(key);
            if (refParam != null) {
                parts[i * 2 + 1] = startDelimit + refParam + endDelimit;
            } else {
                parts[i * 2 + 1] = "$" + key + "$";
            }
        }
        for (int i = 0; i < parts.length; i++) {
            result = result + parts[i];
        }
        return result;
    }


    /**
     * Aplana las referencias que existan dentro del codigo.
     */
    public void plainReferences() {
        Iterator iter = keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = get(key);

            if (key instanceof String && value instanceof String) {
                value = getEntry((String) key);
                put(key, value);
            }
        }
    }

    /**
     * Split a string with separator
     */
    protected static String[] convertToArray(String source, String sep) {
        int sepLen = sep.length();
        Vector parts = new Vector();

        int index = -1;
        while ((index = source.indexOf(sep)) != -1) {
            String prefix = source.substring(0, index);
            parts.add(prefix);
            if (index + sepLen < source.length()) {
                source = source.substring(index + sepLen);
            } else {
                source = "";
            }
        }

        parts.add(source);
        return (String[]) parts.toArray(new String[0]);
    }

    /**
     * Busca un archivo en disco y si no lo encuentra lo busca en el CLASSPATH
     * <p/>
     * TODO: Este metodo no debe estar aqui. Lo hago porque no quiero incluir
     * todo el paquete regexp de la clase Util.
     */
    public static InputStream openResourceFile(String fileName)
            throws FileNotFoundException {
        InputStream result = null;
        File file = new File(fileName);
        if (file.exists()) {
            // Buscamos primero en disco
            result = new FileInputStream(fileName);
        } else {
            // Buscamos en el CLASSPATH
            result = ClassLoader.getSystemResourceAsStream(fileName);
        }

        // Test if exist menuItem
        if (result == null) {
            throw new FileNotFoundException(fileName);
        }

        return result;
    }

    /**
     * Convierte un objeto a cadena.
     * <p/>
     * TODO: Este metodo no debe estar aqui. Lo hago porque no quiero incluir
     * todo una clase para esta funcion.
     * TODO: Seria mejor que esta clase no hiciera solo un obj.toString() sino que
     * utilizara formaters que pudieran estar definidos para cada tipo.
     */
    public static String toString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
 * Created by IntelliJ IDEA.
     * User: daniel_root
     * Date: 02-oct-2009
     * Time: 13:22:30
     * To change this template use File | Settings | File Templates.
     */
    public static class Model2ArrayStore {
    }
}
