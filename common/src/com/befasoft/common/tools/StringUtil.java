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

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * Funciones utiles
 *
 * @author <a href="mailto:tomas.couso@vincomobile.com">Tomas Couso Alvarez</a>
 */

public class StringUtil {

    private final static String ESCAPE_MASK = "#$%&&%$#";

    /**
     * Se define una propiedad a pasar via extJs/Json indicando los problemas encontrados en un formato que via ExtJS adaptaremos
     */
    public final static String ERRORS_KEY = "toolkitErrors";


    public static String escapeCSV(String text) {
        return StringUtil.replaceAll(text, "\\,", ESCAPE_MASK);
    }

    public static String unescapeCSV(String text) {
        return StringUtil.replaceAll(text, ESCAPE_MASK, ",");
    }

    public static String[] unescapeCSV(String[] texts) {
        String[] results = texts;
        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            results[i] = unescapeCSV(text);
        }

        return results;
    }


    public static String replaceAll(String str, String find, String replace) {
        if (str == null) {
            return null;
        } else if (find == null || find.equals("")) {
            return str;
        } else {
            if (replace == null) {
                replace = "";
            }

            StringBuffer buffer = new StringBuffer(str);
            int findLenght = find.length();
            int replaceLength = replace.length();
            int lastPos = 0;
            int pos;
            while ((pos = buffer.indexOf(find, lastPos)) != -1) {
                buffer.delete(pos, pos + findLenght);
                buffer.insert(pos, replace);
                lastPos = pos + replaceLength;
            }
            return buffer.toString();
        }
    }

    /**
     * @param str
     * @param find
     * @return
     */
    public static String deleteAll(String str, String find) {
        if (str == null) {
            return null;
        } else if (find == null || find.equals("")) {
            return str;
        } else {
            return str.replaceAll(find, "");
        }
    }

    /**
     * Retorna el valor del par�metro "parameter" que hay en el string "str".
     * Este par�metro se pasa junto a otros par�metros unido por "_"
     *
     * @param parameter
     * @param str
     * @return String
     */
    public static String getSpecialParameter(String parameter, String str) {
        String result = "";
        if (!empty(str)) {
            int indexOf = str.indexOf(parameter);
            if (indexOf > 0) {
                result = str.substring(indexOf);
                result = result.replaceAll(parameter, "");
                indexOf = result.indexOf("_");
                if (indexOf > 0) {
                    //si hay m�s par�metros enganchados a este par�metro se quitan
                    result = result.substring(0, indexOf);
                }
            }
        }
        return result;
    }

    /**
     * Localiza el parámetro introducido y devuelve la parte anterior del str original
     *
     * @param parameter parametro que buscaremos
     * @param str       String original
     * @return String con el valor del string original anterior al parameter solicitado
     */
    public static String removeSpecialParameter(String parameter, String str) {
        String result = "";
        if (!empty(str)) {
            int indexOf = str.indexOf(parameter);
            if (indexOf > 0) {
                result = str.substring(0, indexOf);
            }
        }
        return result;
    }

    /**
     * Eliminamos de un String todos los char solicitados devolviendo el String original sin este char
     *
     * @param x        char a eliminar
     * @param original String original
     * @return String sin el char solicitado
     */
    public static String removeCharFromString(char x, String original) {

        boolean exit = false;
        String current_result = original;
        while (!exit) {
            if (current_result.equals("")) {
                break;
            }
            char[] chain = current_result.toCharArray();
            for (int i = 0; i < chain.length; i++) {
                char current_char = chain[i];
                if (current_char == x) {
                    String first = current_result.substring(0, i);
                    String last = current_result.substring(i + 1);
                    current_result = first + last;
                    break;
                }
                if (i == chain.length - 1) exit = true;
            }
        }
        return current_result;
    }

    public static String encodeToUTF8(String str) {

        String strEncoded = null;

        try {
            byte[] utf8Bytes = str.getBytes("UTF8");
            strEncoded = new String(utf8Bytes, "UTF8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null == strEncoded ? str : strEncoded;

    }


    public static String escapeToUTF8(String str) {
        if (str == null) {
            return null;
        } else {
            str = escapeFromUTF8(str);
            //System.out.println("str = " + str);

            StringBuffer sb = new StringBuffer(str.length());
            // true if last char was blank
            boolean lastWasBlankChar = false;
            int len = str.length();

            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (c == ' ') {
                    // blank gets extra work,
                    // this solves the problem you get if you replace all
                    // blanks with &nbsp;, if you do that you loss
                    // word breaking
                    if (lastWasBlankChar) {
                        lastWasBlankChar = false;
                        sb.append("&nbsp;");
                    } else {
                        lastWasBlankChar = true;
                        sb.append(' ');
                    }
                } else {
                    lastWasBlankChar = false;
                    //
                    // HTML Special Chars
                    if (c == '"')
                        sb.append("&quot;");
                    else if (c == '&') {
                        sb.append("&amp;");
                    } else if (c == '<')
                        sb.append("&lt;");
                    else if (c == '>')
                        sb.append("&gt;");
                    else if (c == '\n')
                        // Handle Newline
                        sb.append("&lt;br/&gt;");
                    else {
                        int ci = 0xffff & c;
                        if (ci < 160)
                            // nothing special only 7 Bit
                            sb.append(c);
                        else {
                            // Not 7 Bit use the unicode system
                            sb.append("&#");
                            sb.append(new Integer(ci).toString());
                            sb.append(';');
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    /**
     * Convierte las letras del string str del formato UTF8 a letras con acentos
     *
     * @param str String cuyas letras presentadas en formato UTF8 se quieren transformar a letras acentuadas.
     * @return String que se le ha pasado como argumento al m�todo con las letras transformadas de UTF8 a letras acentuadas
     */
    public static String escapeFromUTF8(String str) {
        if (str == null) {
            return null;
        } else {
            // Se reemplazan los caracteres escapados con &#dddd;
            int beginPos = -1;
            while ((beginPos = str.indexOf("&#")) >= 0) {
                beginPos += 2;
                int endPos = str.indexOf(';', beginPos);

                if (endPos >= 0) {
                    String number = str.substring(beginPos, endPos);
                    int ci = TypeParser.convertToInteger(number, ' ');

                    str = str.substring(0, beginPos - 2) + (char) ci + str.substring(endPos + 1);
                } else {
                    // Se elimina el caracter de $#
                    str = str.substring(0, beginPos - 2) + str.substring(beginPos);
                }
            }

            // Se cambian los escapados especiales.
            String[][] mapping = {
                    {"&nbsp;", " "},
                    {"&quot;", "\""},
                    {"&lt;", "<"},
                    {"&gt;", ">"},
                    {"&lt;br/&gt;", "\n"},
                    {"&amp;", "&"},
            };
            for (int i = 0; i < mapping.length; i++) {
                String[] map = mapping[i];
                if (str.indexOf(map[0]) != -1) {
                    str = replaceAll(str, map[0], map[1]);
                }
            }
            return str;
        }
    }

    /**
     * @param val
     * @return
     */
    public static String escapeToXml(String val) {
        return StringEscapeUtils.escapeXml(val);
    }

    /**
     * @param val
     * @return
     */
    public static String escapeFromXml(String val) {
        return StringEscapeUtils.unescapeXml(val);
    }

    public static String escapeToCopyPasteXML(String val) {

        val = val.replaceAll("\n", "break.line");
        return escapeToXml(val);

    }


    /**
     * Convierte los acentos extendidos HTML a car�cteres v�lidos con acentos
     * Ej: � -> to UTF8 -> &#195;&#179; -> toHTMLExtended -> &oacute;
     *
     * @param str String cuyas letras pasadas a UTF8 se quieren transformar a letras acentuadas.
     * @return String con letras acentuadas en HTML extendido
     */
    public static String escapeToExtendedHtml(String str) {
        if (str == null) {
            return null;
        } else {

            // Se undecodea el posible string recibido desde JSP con el método 'decode' para parametrizar
            // caracteres especiales
            try {
                //str = URLDecoder.decode(str, "UTF-8");   TODO: El decode  da una excepcion del tipo URLDecoder: Incomplete trailing escape (%) pattern
            } catch (Throwable e) {
                System.out.println();
                e.printStackTrace();
            }

            // Se convierte string a codificaci�n UTF8
            str = escapeToUTF8(str);

            // Se cambian los UTF8 especiales a caracteres concretos
            // se usa el codigo ascii para asegurar la funcionalidad siendo el charset del
            //sistema irrelevante
            String[][] mapping = {
                    {"&#195;&#179;", "" + (char) 243},
                    {"&#195;&#161;", "" + (char) 225},
                    {"&#195;&#169;", "" + (char) 233},
                    {"&#195;&#173;", "" + (char) 237},
                    {"&#195;&#186;", "" + (char) 250},
                    {"&#195;&#178;", "" + (char) 242},
                    {"&#195;&#160;", "" + (char) 224},
                    {"&#195;&#168;", "" + (char) 232},
                    {"&#195;&#175;", "" + (char) 239},
                    {"&#195;&#188;", "" + (char) 252},
                    {"&#195;&#177;", "" + (char) 241},
                    {"&#195;\u0091", "" + (char) 209},
                    {"&#195;\u0081", "" + (char) 193},
                    {"&#195;\u0080", "" + (char) 192},
                    {"&#195;\u0089", "" + (char) 201},
                    {"&#195;\u0088", "" + (char) 200},
                    {"&#195;\u0093", "" + (char) 211},
                    {"&#195;\u0092", "" + (char) 210},
                    {"&#195;\u008D", "" + (char) 205},
                    {"&#195;\u008C", "" + (char) 204},
                    {"&#195;\u009A", "" + (char) 218},
                    {"&#195;\u0099", "" + (char) 217},
                    {"&#226;\u0082&#172;", "" + (char) 128},//€
                    {"&#194;&#191;", "¿"},
                    {"&#194;&#161;", "" + (char) 173},
                    {"&#194;&#169;", "" + (char) 169},
                    {"&#236;", "" + (char) 236},
                    {"&#224;", "" + (char) 224},
                    {"&#225;", "" + (char) 225},
                    {"&#233;", "" + (char) 233},
                    {"&#237;", "" + (char) 237},
                    {"&#250;", "" + (char) 250},
                    {"&#242;", "" + (char) 242},
                    {"&#239;", "" + (char) 239},
                    {"&#252;", "" + (char) 252},
                    {"&#241;", "" + (char) 241},
                    {"&#8216;", "" + (char) 212},
                    {"&#8217;", "" + (char) 213},
                    {"&#8220;", "" + (char) 210},
                    {"&#8221;", "" + (char) 211},
                    {"&#8211;", "" + (char) 209},
                    {"&#8212;", "" + (char) 208},
                    {"&#8230;", "" + "..."},//... unico caracter
                    {"&#8216;", "" + (char) 145},
                    {"&#8217;", "" + (char) 146},
                    {"&#8220;", "" + (char) 147},
                    {"&#8221;", "" + (char) 148},
                    {"&#8211;", "" + (char) 151},
                    {"&#8212;", "" + (char) 150},
                    {"&#8230;", "" + (char) 133},
                    {"&#8364;", "" + (char) 128},//€


            };
            for (int i = 0; i < mapping.length; i++) {
                String[] map = mapping[i];
                if (str.indexOf(map[0]) != -1) {
                    str = replaceAll(str, map[0], map[1]);
                }
            }
            return str;
        }
    }


    /**
     * Convierte los caracteres especiales recibidos desde UTF-8 a unicode y escapando los acentos correctamente
     *
     * @param str String cuyas letras pasadas a UTF8 se quieren transformar a letras acentuadas.
     * @return String con letras acentuadas en HTML extendido
     */
    public static String escapeFromExtendedHtml(String str) {
        if (str == null) {
            return null;
        } else {

            str = escapeFromUTF8(str);


            // Se cambian los UTF8 especiales a caracteres concretos
            // se usa el codigo ascii para asegurar la funcionalidad siendo el charset del
            //sistema irrelevante
            String[][] mapping = {
                    {"&#192;", "" + (char) 192},
                    {"&#193;", "" + (char) 193},
                    {"&#194;", "" + (char) 194},
                    {"&#195;", "" + (char) 195},
                    {"&#196;", "" + (char) 196},
                    {"&#197;", "" + (char) 197},
                    {"&#198;", "" + (char) 198},
                    {"&#199;", "" + (char) 199},
                    {"&#200;", "" + (char) 200},
                    {"&#201;", "" + (char) 201},
                    {"&#202;", "" + (char) 202},
                    {"&#203;", "" + (char) 203},
                    {"&#204;", "" + (char) 204},
                    {"&#205;", "" + (char) 205},
                    {"&#206;", "" + (char) 206},
                    {"&#207;", "" + (char) 207},
                    {"&#208;", "" + (char) 208},
                    {"&#209;", "" + (char) 209},
                    {"&#210;", "" + (char) 210},
                    {"&#211;", "" + (char) 211},
                    {"&#212;", "" + (char) 212},
                    {"&#213;", "" + (char) 213},
                    {"&#214;", "" + (char) 214},
                    {"&#215;", "" + (char) 215},
                    {"&#216;", "" + (char) 216},
                    {"&#217;", "" + (char) 217},
                    {"&#218;", "" + (char) 218},
                    {"&#219;", "" + (char) 219},
                    {"&#220;", "" + (char) 220},
                    {"&#221;", "" + (char) 221},
                    {"&#222;", "" + (char) 222},
                    {"&#223;", "" + (char) 223},
                    {"&#224;", "" + (char) 224},
                    {"&#225;", "" + (char) 225},
                    {"&#226;", "" + (char) 226},
                    {"&#227;", "" + (char) 227},
                    {"&#228;", "" + (char) 228},
                    {"&#229;", "" + (char) 229},
                    {"&#230;", "" + (char) 230},
                    {"&#231;", "" + (char) 231},
                    {"&#232;", "" + (char) 232},
                    {"&#233;", "" + (char) 233},
                    {"&#234;", "" + (char) 234},
                    {"&#235;", "" + (char) 235},
                    {"&#236;", "" + (char) 236},
                    {"&#237;", "" + (char) 237},
                    {"&#238;", "" + (char) 238},
                    {"&#239;", "" + (char) 239},
                    {"&#240;", "" + (char) 240},
                    {"&#241;", "" + (char) 241},
                    {"&#242;", "" + (char) 242},
                    {"&#243;", "" + (char) 243},
                    {"&#244;", "" + (char) 244},
                    {"&#245;", "" + (char) 245},
                    {"&#246;", "" + (char) 246},
                    {"&#247;", "" + (char) 247},


            };
            for (int i = 0; i < mapping.length; i++) {
                String[] map = mapping[i];
                if (str.indexOf(map[0]) != -1) {
                    str = replaceAll(str, map[0], map[1]);
                }
            }
            return str;
        }
    }

    /**
     * Normaliza un nombre para que pueda ser llave de un campo en concreto.
     * *Escapa los acentos
     * *Escapa los espacios
     * *Devuelve en mayúsculas si se pide
     *
     * @param str
     * @return str escapado como llave de un campo
     */
    public static String normalizeKeyName(String str, boolean uppercase) {

        str = escapeWrittenAccent(str);
        str = replaceAll(str, " ", "_");

        return uppercase ? str.toUpperCase() : str;

    }

    /**
     * Cambia las letras acentuadas de str por las letras sin acentuar y devuelve str
     *
     * @param str String cuyas letras acentuadas queremos cambiar por las letras sin acentuar
     * @return Retorna str cambiando las letras acentuadas por las letras sin acentuar
     */
    public static String escapeWrittenAccent(String str) {
        if (str == null) {
            return null;
        } else {
            String[][] mapping = {
                    {"a", (char) 225 + ""},
                    {"a", (char) 224 + ""},
                    {"e", (char) 233 + ""},
                    {"e", (char) 232 + ""},
                    {"i", (char) 237 + ""},
                    {"i", (char) 239 + ""},
                    {"o", (char) 243 + ""},
                    {"o", (char) 242 + ""},
                    {"o", (char) 246 + ""},
                    {"u", (char) 250 + ""},
                    {"u", (char) 252 + ""},
                    {"n", "–"},
                    {"c", (char) 231 + ""},
                    {"A", (char) 193 + ""},
                    {"A", (char) 192 + ""},
                    {"E", (char) 201 + ""},
                    {"E", (char) 200 + ""},
                    {"I", (char) 205 + ""},
                    {"I", (char) 207 + ""},
                    {"O", (char) 211 + ""},
                    {"O", (char) 210 + ""},
                    {"O", (char) 214 + ""},
                    {"U", (char) 218 + ""},
                    {"U", (char) 220 + ""},
                    {"N", (char) 209 + ""},
                    {"C", (char) 199 + ""},
                    {"¡", ""},
                    {"¿", ""},
                    {"´", ""},
                    {"º", ""},
                    {"ª", ""},
                    {"&", ""},
            };

            for (int i = 0; i < mapping.length; i++) {
                String[] map = mapping[i];
                if (str.indexOf(map[1]) != -1) {
                    str = replaceAll(str, map[1], map[0]);
                }
            }

            return str;
        }
    }

    public static boolean empty(String str) {
        return str == null || str.trim().length() == 0 || str.equals("null");
    }

    /**
     * Split a string with separator
     */
    public static String[] convertToArray(String source, String sep) {
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
     * Funci�n que dado un n�mero rellena con un 0 si no llega a las 2 columnas.
     *
     * @param num
     * @return String que representa el n�mero.
     */
    public static String intToString2Cols(int num) {
        String strNum;
        if (num < 10) {
            strNum = "0" + num;
        } else {
            strNum = Integer.toString(num);
        }
        return strNum;
    }

    /**
     * Retorna el string descripci�n en plural o singular en funci�n de number.
     * Si number es singular => descripci�n se retorna enm singular
     *
     * @param number
     * @param description
     * @return String con el n�mero cambiado en funci�n de number
     */
    public static String checkPlural(long number, String description) {
        String result = description;
        if (number > 1 && !description.endsWith("s")) {
            result = (description + "s");
        } else if (number == 1 && description.endsWith("s")) {
            result = description.substring(0, description.length() - 1);
        }
        return result;
    }

    /**
     * Retorna la fecha correspondiente al mismo d�a del mes anterior
     *
     * @param dayMonthYear Array de strings que contiene {d�a, mes, a�o}
     * @return String[] contiene {d�a, mes anterior, a�o (anterior si corresponde)}
     */
    public static String[] getLastMonth(String[] dayMonthYear) {
        int month = Integer.parseInt(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]);
        //se obtiene el mes anterior
        month = (month - 1) % 12;
        if (month == 0) {
            year--;
            month = 12;
        }
        String[] result = new String[3];
        result[0] = dayMonthYear[0];
        if (Long.toString(month).length() == 1) {
            result[1] = "0".concat(Long.toString(month));
        } else {
            result[1] = Long.toString(month);
        }
        result[2] = Long.toString(year);

        return result;
    }

    public static String[] getMonthBeforeTheLast(String[] dayMonthYear) {
        int month = Integer.parseInt(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]);
        //se obtiene el mes anterior al anterior (valga la redunudancia :-))
        month = (month - 2) % 12;
        if (month == 0) {
            year--;
            month = 12;
        } else if (month == -1) {
            year--;
            month = 11;
        }
        String[] result = new String[3];
        result[0] = dayMonthYear[0];
        if (Long.toString(month).length() == 1) {
            result[1] = "0".concat(Long.toString(month));
        } else {
            result[1] = Long.toString(month);
        }
        result[2] = Long.toString(year);
        return result;
    }

    /**
     * M�todo utilizado para convertir nombres a nombres aptos para repositorio
     * Suprimiendo car�cteres especiales por _ y en min�scula
     *
     * @param name
     * @return String
     */
    public static String normalizeFilenameClean(String name) {
        String result = normalizeFilename(name);

        do {
            String value = replaceAll(result, "__", "_");
            if (value == result || value.equals(result)) {
                break;
            }
            result = value;
        }
        while (true);

        return result;
    }

    /**
     * M�todo utilizado para convertir nombres a nombres aptos para repositorio
     * Suprimiendo car�cteres especiales por _ y en min�scula
     *
     * @param name
     * @return String
     */
    public static String normalizeFilename(String name) {

        if (name != null) {

            String trans = name.toLowerCase();
            trans = escapeWrittenAccent(trans);
            char[] chain = trans.toCharArray();

            for (int i = 0; i < trans.length(); i++) {

                char x = chain[i];

                if (!(x >= 'a' && x <= 'z' || x >= '0' && x <= '9')) {
                    chain[i] = '_';
                }
            }
            return String.valueOf(chain);
        }
        return " ";
    }

    /**
     * @param name
     * @return
     */
    public static String normalizeName(String name) {
        String finalName = "";

        name = name.toLowerCase();
        //Miramos si tiene un _
        if (name.indexOf("_") != -1) {
            StringTokenizer st = new StringTokenizer(name, "_");
            while (st.hasMoreElements()) {
                //nos quedamos con el primer caracter
                //Ejemplo: paraiso_asiatico = pa
                String subStr = st.nextToken();
                if (!StringUtil.empty(subStr)) {
                    finalName += subStr.substring(0, 1);
                    //Si alguno de los subelementos esta vacio, nos quedamos con el nombre entero
                } else {
                    finalName = name;
                }
            }
        } else {
            finalName = name;
        }
        return finalName;
    }


    public static boolean checkColor(String value) {
        return (value.matches("#{0,1}\\p{XDigit}{6}") ? true : false);
    }

    /**
     * Devuelve el numero de mayor longitud de una palabra que pueda contener numeros
     *
     * @param word Palabra sobre la que buscaremos el numero
     * @return String del numero
     */
    public static List<String> getValidNumbers(String word) {

        char[] chars = word.toCharArray();
        boolean writing = false;
        String currentWord = "";
        List<String> numbers = new ArrayList<String>();

        for (int i = 0; i < chars.length; i++) {

            char x = chars[i];

            if (isNumber(x)) {
                if (!writing) {
                    currentWord += x;
                    writing = true;
                } else {
                    currentWord += x;

                }
            } else if (writing) {
                //si estábamos escribiendo un numero lo cerramos y lo añadimos si es válido
                writing = false;
                if (currentWord.length() > 1)
                    numbers.add(currentWord);
                currentWord = "";
            }

        }
        if (writing) {
            //si estábamos escribiendo un numero lo añadimos al listado
            if (currentWord.length() > 1) {
                numbers.add(currentWord);
            }
        }

        return numbers;

    }

    public static boolean isNumber(char x) {
        return (x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5' || x == '6' ||
                x == '7' || x == '8' || x == '9');
    }


    /**
     * @param x
     * @return
     */
    public static boolean isNumber(String x) {
        for (int i = 0; i < x.length(); i++) {
            char c = x.charAt(i);
            if (!isNumber(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param x
     * @return
     */
    public static boolean isBoolean(String x) {
        return (x.equals("true") || x.equals("false"));
    }

    /**
     * Dado un array de string devolveremos su valor como string separando los tokens por el valor especificado
     *
     * @param array     Array de Strings que queremos juntar en un solo string
     * @param separator Elemento que se usará como separador de los string
     * @return String donde se separan por el separator definido los distintos elementos del String[]
     */
    public static String convertArrayToString(String[] array, String separator) {

        String result = "";
        boolean first = true;
        for (String item : array) {
            if (first) {
                result += item;
                first = false;
            } else {
                result += separator + item;
            }
        }

        return result;
    }

    public static String[] convertStringToArray(String str, String separator) {
        String[] aux = str.split(separator);
        for (int i = 0; i < aux.length; i++) {
            aux[i] = aux[i].trim();
        }
        return aux;
    }

    /**
     * @param arrLst
     * @param separator
     * @return
     */
    public static String convertArrayListToString(ArrayList<String> arrLst, String separator) {

        String result = "";
        boolean first = true;
        for (String item : arrLst) {
            if (first) {
                result += item;
                first = false;
            } else {
                result += separator + item;
            }
        }

        return result;
    }


        /**
     * @param arrLst
     * @param separator
     * @return
     */
    public static String convertLongListToString(ArrayList<Long> arrLst, String separator) {

        String result = "";
        boolean first = true;
        for (Long item : arrLst) {
            if (first) {
                result += item+"";
                first = false;
            } else {
                result += separator + item;
            }
        }

        return result;
    }

    public static String urlEncode(String str) {
        String userDict = str;
        try {
            userDict = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return userDict;
    }

    public static String urlDecode(String str) {
        String userDict = str;
        try {
            userDict = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return userDict;
    }


    public static void main(String[] args) {

        String data = "[{'text':'opt1','value':'1'},{'text':'opt2','value':'2'}]";
        data= data.replaceAll("'", "\\\"");

        data = "[{\"fieldName\":\"email\",\"fieldDescription\":\"Email\",\"fieldInput\":\"text\",\"fieldValue\":\"\",\"fieldType\":\"email\",\"fieldPage\":\"\",\"fieldAction\":\"\",\"fieldOptions\":\"[{'text':'item','value':'1'},{'text':'it s for your  false ','value':'2'}]\"},{\"fieldName\":\"city\",\"fieldDescription\":\"City\",\"fieldInput\":\"select_menu\",\"fieldValue\":\"\",\"fieldType\":\"\",\"fieldPage\":\"\",\"fieldAction\":\"\",\"fieldOptions\":\"[{'text':'opt1','value':'1'},{'text':'opt2','value':'2'}]\"}]";
      //  List optionList= JSONParser.defaultJSONParser().parse(List.class, data);
      //  System.out.println("data "+ optionList);






        //System.out.println("StringUtil.escapeToUTF8(…) = " + StringUtil.escapeToUTF8("…"));
        //System.out.println("StringUtil.escapeToExtendedHtml(…) = " + StringUtil.escapeToExtendedHtml("hola&lt;br&gt; mundo"));
        //System.out.println("StringUtil.escapeFromExtendedHtml(…) = " + StringUtil.escapeFromExtendedHtml("hola&lt;br&gt; mundo"));

    }

    public static String getSubdomain(String domain) {
        String[] data = domain.split("\\.");
        domain = data[0];

        return domain;
    }

    public static String getDomain(String domain) {
        String[] data = domain.split("\\.");
        if (data.length > 2) {
            domain = data[1] + "." + data[2];
        }

        return domain;
    }

    /**
     * @param lst
     * @return
     */
    public static String convertArrayToStrWithTrim(String[] lst) {

        if (lst != null) {
            ArrayList<String> aList = new ArrayList<String>(0);

            for (String name : lst) {
                name = name.trim();
                if (!name.equals("")) aList.add(name);
            }
            return aList.size() > 0 ? StringUtil.convertArrayListToString(aList, ",") : "";
        }
        return "";
    }


    /**
     * Valida que el string que define una fecha casa con el formato de fecha deseado
     *
     * @param value
     * @param dateFormat
     * @return
     */
    public static boolean isProperlyDateFormat(String value, String dateFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return null != sdf.parse(value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * M�todo para escapar los posibles caracteres recibidos desde los XML
     *
     * @param _characters
     * @return
     */
    public static String replaceMetatags(String _characters) {

        // Se cambian los escapados especiales.
        String[][] mapping = {
                //DATO: Imperativo dejar un espacio detras del <br> dado AbstractASTParser (parser wiki) lo necesita
                {"<br>", "<br> "},
                {"<br/>", "<br> "},
                {"<br />", "<br> "},
                {"</br>", "<br> "},
                {"<strong>", "'''"},
                {"</strong>", "'''"},
                {"<b>", "'''"},
                {"</b>", "'''"},
                {"@", "&#64;"},
                {"<li>", ""},
                {"<lu>", ""},
        };
        for (int i = 0; i < mapping.length; i++) {
            String[] map = mapping[i];
            if (_characters.indexOf(map[0]) != -1) {
                _characters = StringUtil.replaceAll(_characters, map[0], map[1]);
            }
        }
        return _characters;
    }

    /**
     * Método que parseará un texto recibido en formato Json, lo desglosará en items recuperando su llave, valor como un map
     *
     * @param JsonText del tipo : [{"id":19,"name":"no tcare DANILIS","description":"dsds","created":"2009-10-15 00:00:00.0","frequency":10080,"status":0},{"id":16,"name":"test 2 DANOLUS","description":"test 2 dsdd","created":"2009-10-15 00:00:00.0","frequency":10080,"status":0}]
     * @return una listado de mapas donde cada mapa es un objeto Json recibido
     */
    public static List<HashMap<String, String>> convertJsonTextIntoHashMapList(String JsonText) {

        List<HashMap<String, String>> objects = new ArrayList<HashMap<String, String>>(0);

        if (StringUtil.empty(JsonText) || JsonText.indexOf("[") == -1 || JsonText.indexOf("]") == -1
                || JsonText.indexOf("{") == -1 || JsonText.indexOf("}") == -1) {
            return objects;
        }

        int lastPosition = JsonText.indexOf("}");
        int firstPosition = JsonText.indexOf("{");

        while (lastPosition != -1) {

            String stringObject = JsonText.substring(firstPosition + 1, lastPosition);
            HashMap<String, String> map = new HashMap<String, String>(0);
            String[] components = convertStringToArray(stringObject, ",");
            for (int i = 0; i < components.length; i++) {

                String component = components[i];
                String key = component.substring(1, component.indexOf(":") - 1);
                String value = component.substring(component.indexOf(":") + 1, component.length());

                value = value.replace("\"", "");

                map.put(key, value);
            }

            objects.add(map);
            firstPosition = JsonText.indexOf("{", firstPosition + 1);
            lastPosition = JsonText.indexOf("}", lastPosition + 1);

        }


        return objects;
    }

    /**
     * Valida que el string sea un mail valido
     *
     * @param mail
     * @return true/false sobre si es mail
     */
    public static boolean isMail(String mail) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(mail);
        if (mat.find()) {
            //System.out.println("[" + mat.group() + "]");
            return true;
        } else {
            return false;
        }
    }

    public static String getGoogleApiChartsURL(String width, String eight, String values, String names) {
        return "http://chart.apis.google.com/chart?cht=p3&chd=t:" + values + "&chs=" + width + "x" + eight + "&chl=" + names;
    }



	public static String parseRGB(String rgb) {
		rgb = rgb.replaceAll(" ", "");
		rgb = rgb.substring(rgb.indexOf("(") + 1, rgb.indexOf(")"));
		String colors[] = rgb.split(",");
		int r = 0, g = 0, b = 0;
		if (colors.length == 3) {
			try {
				r = Integer.parseInt(colors[0]);
				g = Integer.parseInt(colors[1]);
				b = Integer.parseInt(colors[2]);
			} catch (NumberFormatException e) {
				System.err.println("In class parseRGB");
			}
		}
		Color c= new Color(r, g, b);

        String s = Integer.toHexString( c.getRGB() & 0xffffff );
        if ( s.length() < 6 ){
            // pad on left with zeros
            s = "000000".substring( 0, 6 - s.length() ) + s;
        }
        return '#' + s;

       // return   "#" + Integer.toHexString( c.getRGB() & 0x00ffffff );
      }

}