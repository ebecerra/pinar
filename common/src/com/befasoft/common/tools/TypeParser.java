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


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Permite parsear las propiedades de un archivo properties en valores nativos.
 *
 * @author <a href="mailto:tomas.couso@vincomobile.com">Tomas Couso Alvarez</a>
 */
public class TypeParser {
    /**
     * Crear una nueva instancia.
     * El constructor es privado para no permitir crear nuevas instancias,
     * todos los m�todos son est�ticos
     */
    private TypeParser() {
    }

    /**
     * Obtener cualquier formato.
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static String getParameterString(Properties p, String key) {
        String result = p.getProperty(key);

        if (result != null) {
            return result;
        } else {
            return result;
        }
    }

    /**
     * Obtener un entero.
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Integer getParameterInteger(Properties p, String key) {
        return convertToInteger(getParameterString(p, key));
    }

    /**
     * Obtener un long.
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Long getParameterLong(Properties p, String key) {
        return convertToLong(getParameterString(p, key));
    }

    /**
     * Obtener un Double.
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Double getParameterDouble(Properties p, String key) {
        return convertToDouble(getParameterString(p, key));
    }

    /**
     * Obtener un Boolean.
     * El formato puede ser:
     * <ul>
     * <li> F      (false)
     * <li> FALSE  (false)
     * <li> N      (false)
     * <li> 0      (false)
     * <li> T      (true)
     * <li> TRUE   (true)
     * <li> S      (true)
     * <li> SI     (true)
     * <li> Y      (true)
     * <li> YES    (true)
     * </ul>
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Boolean getParameterBoolean(Properties p, String key) {
        return convertToBoolean(getParameterString(p, key));
    }

    /**
     * Obtener un color con el formato R,G,B donde R,G,B son enteros entre
     * 0 y 255
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Color getParameterColor(Properties p, String key) {
        return convertToColor(getParameterString(p, key));
    }

    /**
     * Obtener una fuente con el formato Name,Type,Size.
     * Donde:
     * <ul>
     * <li>Name - Nombre de la fuente
     * <li>Type - BOLD, ITALIC, PLAIN
     * <li>Size - int
     * </ul>
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Font getParameterFont(Properties p, String key) {
        return convertToFont(getParameterString(p, key));
    }

    /**
     * Obtener un array de String separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static String[] getParameterArrayString(Properties p, String key, String sep) {
        return convertToArrayString(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de String separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static String[] getParameterArrayString(Properties p, String key) {
        return getParameterArrayString(p, key, ",");
    }

    /**
     * Obtener un array de Integer separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static Integer[] getParameterArrayInteger(Properties p, String key, String sep) {
        return convertToArrayInteger(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de Integer separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Integer[] getParameterArrayInteger(Properties p, String key) {
        return getParameterArrayInteger(p, key, ",");
    }

    /**
     * Obtener un array de Double separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static Double[] getParameterArrayDouble(Properties p, String key, String sep) {
        return convertToArrayDouble(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de Double separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Double[] getParameterArrayDouble(Properties p, String key) {
        return getParameterArrayDouble(p, key, ",");
    }


    /**
     * Obtener un array de Boolean separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static Boolean[] getParameterArrayBoolean(Properties p, String key, String sep) {
        return convertToArrayBoolean(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de Boolean separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Boolean[] getParameterArrayBoolean(Properties p, String key) {
        return getParameterArrayBoolean(p, key, ",");
    }

    /**
     * Obtener un array de Color separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static Color[] getParameterArrayColor(Properties p, String key, String sep) {
        return convertToArrayColor(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de Color separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Color[] getParameterArrayColor(Properties p, String key) {
        return getParameterArrayColor(p, key, ",");
    }

    /**
     * Obtener un array de Font separados por 'sep'
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     * @param sep Cadena separadora de los elementos de la lista
     */
    public static Font[] getParameterArrayFont(Properties p, String key, String sep) {
        return convertToArrayFont(getParameterString(p, key), sep);
    }

    /**
     * Obtener un array de Font separados por ','
     *
     * @param p   Conjunto de propiedades de la que extraer el valor
     * @param key Identificador de la propiedad de la que obtener el valor
     */
    public static Font[] getParameterArrayFont(Properties p, String key) {
        return getParameterArrayFont(p, key, ",");
    }

    /**
     * Obtener un entero a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static int convertToInteger(String value, int defaultValue) {
        Integer result = convertToInteger(value);
        return result != null ? result.intValue() : defaultValue;
    }

    /**
     * Obtener un entero a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static Integer convertToInteger(String value, Integer defaultValue) {
        Integer result = convertToInteger(value);
        return result != null ? result : defaultValue;
    }

    /**
     * Obtener un entero a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static Integer convertToInteger(String value) {
        if (value == null) {
            return null;
        } else {
            value = value.trim().toLowerCase();
            if (value.startsWith("0x")) {
                try {
                    value = value.substring(2);
                    return new Integer(Integer.parseInt(value, 16));
                }
                catch (Exception e) {
                    return null;
                }
            } else {
                try {
                    return new Integer(value.trim());
                }
                catch (Exception e) {
                    return null;
                }
            }
        }
    }

    /**
     * Obtener un long a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static long convertToLong(String value, long defaultValue) {
        Long result = convertToLong(value);
        return result != null ? result.longValue() : defaultValue;
    }

    /**
     * Obtener un long a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static Long convertToLong(String value) {
        if (value == null) {
            return null;
        } else
            try {
                return new Long(value.trim());
            }
            catch (Exception e) {
                return null;
            }
    }

    /**
     * Obtener un Double a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static double convertToDouble(String value, double defaultValue) {
        Double result = convertToDouble(value);
        return result != null ? result.doubleValue() : defaultValue;
    }

    /**
     * Obtener un Double a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static Double convertToDouble(String value) {
        if (value == null) {
            return null;
        } else {
            try {
                return new Double(value.trim());
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * Obtener un Double a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static float convertToFloat(String value, float defaultValue) {
        Float result = convertToFloat(value);
        return result != null ? result.floatValue() : defaultValue;
    }

    /**
     * Obtener un Double a partir de una cadena
     *
     * @param value Cadena a convertir
     */
    public static Float convertToFloat(String value) {
        if (value == null) {
            return null;
        } else {
            try {
                return new Float(value.trim());
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * Crear un Boolean a partir de una cadena.
     * <p/>
     * El formato puede ser:
     * <ul>
     * <li> F      (false)
     * <li> FALSE  (false)
     * <li> N      (false)
     * <li> 0      (false)
     * <li> T      (true)
     * <li> TRUE   (true)
     * <li> S      (true)
     * <li> SI     (true)
     * <li> Y      (true)
     * <li> YES    (true)
     * </ul>
     *
     * @param value Cadena a convertir
     */
    public static boolean convertToBoolean(String value, boolean defaultValue) {
        Boolean result = convertToBoolean(value);
        return result != null ? result.booleanValue() : defaultValue;
    }

    /**
     * Crear un Boolean a partir de una cadena.
     * <p/>
     * El formato puede ser:
     * <ul>
     * <li> F      (false)
     * <li> FALSE  (false)
     * <li> N      (false)
     * <li> 0      (false)
     * <li> T      (true)
     * <li> TRUE   (true)
     * <li> S      (true)
     * <li> SI     (true)
     * <li> Y      (true)
     * <li> YES    (true)
     * </ul>
     *
     * @param value Cadena a convertir
     */
    public static Boolean convertToBoolean(String value, Boolean defaultValue) {
        Boolean result = convertToBoolean(value);
        return result != null ? result : defaultValue;
    }

    /**
     * Crear un Boolean a partir de una cadena.
     * <p/>
     * El formato puede ser:
     * <ul>
     * <li> F      (false)
     * <li> FALSE  (false)
     * <li> N      (false)
     * <li> 0      (false)
     * <li> T      (true)
     * <li> TRUE   (true)
     * <li> S      (true)
     * <li> SI     (true)
     * <li> Y      (true)
     * <li> YES    (true)
     * </ul>
     *
     * @param value Cadena a convertir
     */
    public static Boolean convertToBoolean(String value) {
        if (value == null) {
            return null;
        } else {
            value = value.trim();

            if (value.equalsIgnoreCase("T") || value.equalsIgnoreCase("S") ||
                    value.equalsIgnoreCase("SI") || value.equalsIgnoreCase("Y") ||
                    value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("TRUE") ||
                    value.equals("1")) {
                return Boolean.TRUE;
            } else if (value.equalsIgnoreCase("F") || value.equalsIgnoreCase("N") ||
                    value.equalsIgnoreCase("NO") || value.equalsIgnoreCase("FALSE") ||
                    value.equals("0")) {
                return Boolean.FALSE;
            } else {
                return null;
            }
        }
    }

    /**
     * Crear un objeto Color a partir de una cadena con el formato
     * R<sep>G<sep>B.
     *
     * @param color Cadena con formato R<sep>G<sep>B
     * @param sep   Identificador de la propiedad de la que obtener el valor
     */
    public static Color convertToColor(String color, String sep) {
        if (color == null || sep == null) {
            return null;
        }

        try {
            StringTokenizer tokenizer = new StringTokenizer(color, sep);

            Integer r = new Integer(tokenizer.nextToken().trim());
            Integer g = new Integer(tokenizer.nextToken().trim());
            Integer b = new Integer(tokenizer.nextToken().trim());
            return new Color(r.intValue(), g.intValue(), b.intValue());
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Crear un objeto Color a partir de una cadena con el formato R,G,B.
     *
     * @param color Cadena con formato R,G,B
     */
    public static Color convertToColor(String color) {
        return convertToColor(color, ",");
    }

    /**
     * Crear un objeto fuente (Font) a partir de una cadena con el formato
     * Name<sep>Type<sep>Size.
     * Donde:
     * <ul>
     * <li>Name - Nombre de la fuente
     * <li>Type - BOLD, ITALIC, PLAIN
     * <li>Size - int
     * </ul>
     *
     * @param value Cadena con formato Name<sep>Type<sep>Size
     * @param sep   Identificador de la propiedad de la que obtener el valor
     */
    public static Font convertToFont(String value, String sep) {
        if (value == null || sep == null) {
            return null;
        }

        try {
            StringTokenizer tokenizer = new StringTokenizer(value, sep);

            String name = tokenizer.nextToken().trim();
            String type = tokenizer.nextToken().trim();
            Integer size = new Integer(tokenizer.nextToken().trim());

            int itype;

            if (size.equals("BOLD")) {
                itype = Font.BOLD;
            } else if (size.equals("ITALIC")) {
                itype = Font.ITALIC;
            } else {
                itype = Font.PLAIN;
            }

            return new Font(name, itype, size.intValue());
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Crear un objeto fuente (Font) a partir de una cadena con el formato
     * Name<sep>Type<sep>Size.
     * Donde:
     * <ul>
     * <li>Name - Nombre de la fuente
     * <li>Type - BOLD, ITALIC, PLAIN
     * <li>Size - int
     * </ul>
     *
     * @param value Cadena con formato Name<sep>Type<sep>Size
     */
    public static Font convertToFont(String value) {
        return convertToFont(value, ",");
    }

    /**
     * Obtener un array de String separados por 'sep'
     *
     * @param value Identificador de la propiedad de la que obtener el valor
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static String[] convertToArrayString(String value, String sep) {
        if (sep == null || sep.length() == 0 || StringUtil.empty(value)) {
            return new String[0];
        }

        try {

            StringTokenizer tokenizer = new StringTokenizer(value, sep);
            int len = tokenizer.countTokens();
            String[] array = new String[len];

            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                array[i] = tokenizer.nextToken();
                i++;
            }

            return array;
        }
        catch (Exception e) {
            return new String[0];
        }
    }

    /**
     * Obtener un array de String separados por ','
     *
     * @param value Identificador de la propiedad de la que obtener el valor
     */
    public static String[] convertToArrayString(String value) {
        return convertToArrayString(value, ",");
    }

    /**
     * Obtener un array de Integer separados por 'sep'
     *
     * @param value Cadena a convertir
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static Integer[] convertToArrayInteger(String value, String sep) {
        String[] array = convertToArrayString(value, sep);

        Integer[] result = new Integer[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = convertToInteger(array[i]);
        }

        return result;
    }

    /**
     * Obtener un array de Integer separados por ','
     *
     * @param value Cadena a convertir
     */
    public static Integer[] convertToArrayInteger(String value) {
        return convertToArrayInteger(value, ",");
    }

    /**
     * Obtener un array de Double separados por 'sep'
     *
     * @param value Cadena a convertir
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static Double[] convertToArrayDouble(String value, String sep) {
        String[] array = convertToArrayString(value, sep);

        Double[] result = new Double[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = convertToDouble(array[i]);
        }

        return result;
    }

    /**
     * Obtener un array de Double separados por ','
     *
     * @param value Cadena a convertir
     */
    public static Double[] convertToArrayDouble(String value) {
        return convertToArrayDouble(value, ",");
    }

    /**
     * Obtener un array de Boolean separados por 'sep'
     *
     * @param value Cadena a convertir
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static Boolean[] convertToArrayBoolean(String value, String sep) {
        String[] array = convertToArrayString(value, sep);

        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = convertToBoolean(array[i]);
        }

        return result;
    }

    /**
     * Obtener un array de Boolean separados por ','
     *
     * @param value Cadena a convertir
     */
    public static Boolean[] convertToArrayBoolean(String value) {
        return convertToArrayBoolean(value, ",");
    }

    /**
     * Obtener un array de Color separados por 'sep'
     *
     * @param value Cadena a convertir
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static Color[] convertToArrayColor(String value, String sep) {
        String[] array = convertToArrayString(value, sep);

        Color[] result = new Color[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = convertToColor(array[i]);
        }

        return result;
    }

    /**
     * Obtener un array de Font separados por 'sep'
     *
     * @param value Cadena a convertir
     * @param sep   Cadena separadora de los elementos de la lista
     */
    public static Font[] convertToArrayFont(String value, String sep) {
        String[] array = convertToArrayString(value, sep);

        Font[] result = new Font[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = convertToFont(array[i]);
        }

        return result;
    }

    /**
     * Obtener un array de String separados por ','
     *
     * @param value Identificador de la propiedad de la que obtener el valor
     */
    public static List<String> convertToListString(String value) {
        String[] array = convertToArrayString(value);
        List<String> result = new ArrayList<String>(array.length);
        for (int i = 0; i < array.length; i++) {
            String item = array[i];

            result.add(item);
        }

        return result;
    }

    /**
     * Obtener un array de Integer separados por 'sep'
     *
     * @param value Cadena a convertir
     */
    public static List<Integer> convertToListInteger(String value) {
        Integer[] array = convertToArrayInteger(value);
        List<Integer> result = new ArrayList<Integer>(array.length);
        for (int i = 0; i < array.length; i++) {
            Integer item = array[i];

            result.add(item);
        }

        return result;
    }

    /**
     * Obtener un array de Double separados por 'sep'
     *
     * @param value Cadena a convertir
     */
    public static List<Double> convertToListDouble(String value) {
        Double[] array = convertToArrayDouble(value);
        List<Double> result = new ArrayList<Double>(array.length);
        for (int i = 0; i < array.length; i++) {
            Double item = array[i];

            result.add(item);
        }

        return result;
    }

    /**
     * Obtener un array de Boolean separados por 'sep'
     *
     * @param value Cadena a convertir
     */
    public static List<Boolean> convertToListBoolean(String value) {
        Boolean[] array = convertToArrayBoolean(value);
        List<Boolean> result = new ArrayList<Boolean>(array.length);
        for (int i = 0; i < array.length; i++) {
            Boolean item = array[i];

            result.add(item);
        }

        return result;
    }

    /**
     * Obtener un array de Color separados por 'sep'
     *
     * @param value Cadena a convertir
     */
    public static List<Color> convertToListColor(String value) {
        Color[] array = convertToArrayColor(value,",");
        List<Color> result = new ArrayList<Color>(array.length);
        for (int i = 0; i < array.length; i++) {
            Color item = array[i];

            result.add(item);
        }

        return result;
    }

    /**
     * Obtener un array de Font separados por 'sep'
     *
     * @param value Cadena a convertir
     */
    public static List<Font> convertToListFont(String value) {
        Font[] array = convertToArrayFont(value,",");
        List<Font> result = new ArrayList<Font>(array.length);
        for (int i = 0; i < array.length; i++) {
            Font item = array[i];

            result.add(item);
        }

        return result;
    }


    public static Object convert(String strValue, String type) {
        Object value = null;
        if (StringUtil.empty(strValue)
                || "null".equalsIgnoreCase(strValue)
                || "nil".equalsIgnoreCase(strValue)
                || "undefined".equalsIgnoreCase(strValue)) {
            value = null;
        }
        else if ("STRING".equalsIgnoreCase(type)) {
            value = strValue;
        }
        else if ("BOOLEAN".equalsIgnoreCase(type) || "BOOL".equalsIgnoreCase(type)) {
            value = TypeParser.convertToBoolean(strValue);
        }
        else if ("INT".equalsIgnoreCase(type) || "INTEGER".equalsIgnoreCase(type)) {
            value = TypeParser.convertToInteger(strValue);
        }
        else if ("LONG".equalsIgnoreCase(type)) {
            value = TypeParser.convertToLong(strValue);
        }
        else if ("DOUBLE".equalsIgnoreCase(type)) {
            value = TypeParser.convertToDouble(strValue);
        }
        else if ("FLOAT".equalsIgnoreCase(type)) {
            value = TypeParser.convertToFloat(strValue);
        }

        return value;
    }

    public static Object convert(String strValue, Class type) {
        return convert(strValue, getType(type));
    }

    public static String getType(Class type) {
        String typeStr = null;
        if (type == null) {
            typeStr = "STRING";
        }
        else if (String.class.isAssignableFrom(type)) {
            typeStr = "STRING";
        }
        else if (Boolean.class.isAssignableFrom(type)) {
            typeStr = "BOOLEAN";
        }
        else if (Integer.class.isAssignableFrom(type)) {
            typeStr = "INTEGER";
        }
        else if (Long.class.isAssignableFrom(type)) {
            typeStr = "LONG";
        }
        else if (Double.class.isAssignableFrom(type)) {
            typeStr = "DOUBLE";
        }
        else if (Float.class.isAssignableFrom(type)) {
            typeStr = "FLOAT";
        }
        else {
            typeStr = "STRING";
        }

        return typeStr;
    }
}