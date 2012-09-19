package com.befasoft.common.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 22-may-2005
 * Time: 14:32:05
 */
public class Converter {

    static DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    static DecimalFormat df = new DecimalFormat();

    /*
     * Verifica que un numero real sea correcto
     */
    private static boolean checkF(String value) {
        try {
            Float.parseFloat(value);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean validFloat(String value, boolean nullOk) {
        return nullOk && isEmpty(value) || checkF(value.replace(',', '.')) || checkF(value.replace('.', ','));
    }

    /*
     * Convierte una cadena a real
     */
    public static double getFloat(String value) {
        return getFloat(value, 0.0);
    }

    public static double getF(String value, double defVal) {
        try {
            return Float.parseFloat(value);
        }
        catch (Exception e) {
            return defVal;
        }
    }

    public static double getFloat(String value, double defVal) {
        if (value == null)
            return defVal;
        double res = getF(value.replace(',', '.'), defVal);
        if (res == defVal)
            res = getF(value.replace('.', ','), defVal);
        return res;
    }

    public static double getFloatPrec(String value, int prec) {
        return getFloatPrec(getFloat(value, 0.0), prec);
    }

    public static double getFloatPrec(double value, int prec) {
        double mult = Math.pow(10, prec);
        value = value * mult;
        double tmp = Math.round(value);
        return tmp / mult;
    }

    /*
     * Convierte una real a cadena
     */
    public static String formatFloat(double value) {
        return formatFloat(value, 2);
    }

    public static String formatFloat(double value, int digit) {
        dfs.setDecimalSeparator(',');
        df.setGroupingUsed(false);
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(digit);
        df.setMinimumFractionDigits(digit);
        return df.format(value);
    }

    public static String formatFloat(double value, int digit, boolean grouping) {
        dfs.setDecimalSeparator(',');
        df.setGroupingUsed(grouping);
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(digit);
        df.setMinimumFractionDigits(digit);
        return df.format(value);
    }

    public static float formatFloatToReport(float num){
    	return Math.round(num*100)/100f;
    }

    public static double formatDoubleToReport(double num){
    	return Math.round(num*100)/100d;
    }

    /*
     * Convierte un real a cadena
     */
    public static String getSQLFloat(double value) {
        dfs.setDecimalSeparator('.');
        df.setGroupingUsed(false);
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(6);
        return df.format(value);
    }

    public static String getSQLFloat(double value, int min, int max) {
        dfs.setDecimalSeparator('.');
        df.setGroupingUsed(false);
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(min);
        df.setMinimumFractionDigits(max);
        return df.format(value);
    }

    public static String getFloat(double value, int dec) {
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(dec);
        df.setMinimumFractionDigits(dec);
        return df.format(value);
    }

    public static String getSQLDouble(Double value) {
        return value == null ? "null" : getSQLFloat(value);
    }

    /*
     * Verifica que un numero entero sea correcto
     */
    public static boolean validInt(String value, boolean nullOk) {
        if (nullOk && isEmpty(value))
            return true;
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*
     * Convierte una cadena en un entero
     */
    public static int getInt(String value) {
        return getInt(value, 0);
    }

    public static int getInt(Integer value) {
        if (value == null)
            return 0;
        else
            return value;
    }

    public static int getInt(String value, int defVal) {
        try {
            return Integer.parseInt(value);
        }
        catch (Exception e) {
            return defVal;
        }
    }

    /*
     * Convierte una cadena en un entero largo
     */
    public static long getLong(String value) {
        return getLong(value, 0);
    }

    public static long getLong(Long value) {
        if (value == null)
            return 0;
        else
            return value;
    }

    public static long getLong(String value, long defVal) {
        try {
            return Long.parseLong(value);
        }
        catch (Exception e) {
            return defVal;
        }
    }

    /*
     * Convierte una cadena en un boolean (S - true, !S - false
     */
    public static boolean getBoolean(String value, boolean defVal) {
        if (value == null)
            return defVal;
        else {
            value = value.toUpperCase();
            return "1.0".equals(value) || "1".equals(value) || "S".equals(value) || "Y".equals(value) || "TRUE".equals(value) || "SI".equals(value) || "SÍ".equals(value) || "YES".equals(value) || "ON".equals(value);
        }
    }

    public static boolean getBoolean(String value) {
        return getBoolean(value, false);
    }

    public static String getBooleanStr(boolean value) {
        return value ? "S" : "N";
    }

    /*
     * Verifica que una hora sea correcta
     */
    public static boolean validTime(String time) {
        if (!time.equals("")) {
            SimpleDateFormat dFormat = new SimpleDateFormat("HH:mm");
            ParsePosition pos = new ParsePosition(0);
            return dFormat.parse(time, pos) != null;
        }
        return true;
    }

    /*
     * Convierte un time a cadena
     */
    public static String getTimeStr(Date time) {
        if (time != null) {
            SimpleDateFormat dFormat = new SimpleDateFormat("HH:mm");
            return dFormat.format(time);
        }
        return null;
    }

    /*
     * Verifica que una fecha sea correcta
     */
    public static boolean validDate(String fec) {
        return validDate(fec, "dd/MM/yyyy");
    }

    public static boolean validDate(String fec, String format) {
        if (!fec.equals("")) {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            return dFormat.parse(fec, pos) != null;
        }
        return true;
    }

    /*
     * Convierte cadena a fecha
     */
    public static Date getDate(String fec) {
        return getDate(fec, "dd/MM/yyyy");
    }

    public static Date getDate(String fec, String format) {
        if (fec != null && !fec.equals("")) {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            return dFormat.parse(fec, pos);
        }
        return null;
    }

    /*
     * Convierte cadena a hora
     */
    public static Time getTime(String fec) {
        return getTime(fec, "HH:mm");
    }

    public static Time getTime(String fec, String format) {
        if (fec != null && !fec.equals("")) {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            return new Time(dFormat.parse(fec, pos).getTime());
        }
        return null;
    }

    /*
     * Covierte una fecha en una cadena con formato de fecha SQL
     */
    public static String getSQLDate(Date fec) {
        return getSQLDate(fec, true);
    }
    
    public static String getSQLDate(Date fec, boolean setSep) {
        return getSQLDate(fec, Constants.SQL_DATE_OUTPUT_FORMAT, setSep);
    }

    public static String getSQLDate(Date fec, String format, boolean setSep) {
        if (fec == null)
            return "null";
        SimpleDateFormat dFormat = new SimpleDateFormat(format);
        if (setSep)
            return "'"+dFormat.format(fec)+"'";
        else
            return dFormat.format(fec);
    }

    public static String getSQLDateTime(Date fec, boolean setSep) {
        return getSQLDateTime(fec, Constants.SQL_DATETIME_OUTPUT_FORMAT, setSep);
    }

    public static String getSQLDateTime(Date fec, String format, boolean setSep) {
        if (fec == null)
            return "null";
        SimpleDateFormat dFormat = new SimpleDateFormat(format);
        if (setSep)
            return "'"+dFormat.format(fec)+"'";
        else
            return dFormat.format(fec);
    }

    /*
     * Convierte una fecha a cadena
     */
    public static String formatDate(Date fec) {
        return formatDate(fec, "dd/MM/yyyy");
    }

    public static String formatDate(Date fec, String format) {
        if (fec == null)
            return "";
        SimpleDateFormat dFormat = new SimpleDateFormat(format);
        return dFormat.format(fec);
    }

    /**
     * Verifica si una direccion IP es valida
     * @param ip Direccion ip (xxx.xxx.xxx.xxx)
     * @return Verdadero si es valida
     */
    public static boolean validIP(String ip) {
		String octetos[] = ip.trim().split("\\.");
        try {
            int uno = Integer.parseInt(octetos[0]);
            int dos = Integer.parseInt(octetos[1]);
            int tres = Integer.parseInt(octetos[2]);
            int cuatro = Integer.parseInt(octetos[3]);
            return uno >= 0 && uno < 256 && dos >= 0 && dos < 256 && tres >= 0 && tres < 256  && cuatro >= 0 && cuatro < 256;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*
     * Covierte una cadena en una cadena con formato de SQL
     */
    public static String getSQLString(String str) {
        return getSQLString(str, true);
    }

    public static String getSQLString(String str, boolean sep) {
        if (str == null)
            return "null";
        else {
            str = str.replace("'", "''");
            str = str.replace("\\", "\\\\");
            if (sep)
                return "'"+str+"'";
            else
                return str;
        }
    }

    /*
     * Covierte una boolean en una cadena con formato de SQL
     */
    public static String getSQLBoolean(boolean bool) {
        return bool ? "1" : "0";
    }

    /*
     * Convierte un Integer a formato de SQL
     */ 
    public static String getSQLInteger(Integer value) {
        return value == null ? "null" : value.toString();
    }

    public static String getSQLInteger(String value) {
        return value == null ? "null" : Integer.toString(getInt(value));
    }

    public static String getString(String str) {
        return getString(str, "");
    }

    public static String getString(String str, String def) {
        if (str == null)
            return def;
        else
            return str;
    }

    /*
     * Verifica que una objecto es vacio
     */
    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    public static boolean isEmpty(Long value) {
        return value == null || value == 0;
    }

    public static boolean isEmpty(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isEmpty(Date value) {
        return value == null;
    }

    public static boolean isEmpty(Double value) {
        return value == null || value == 0;
    }

    public static boolean isEmpty(List value) {
        return value == null || value.size() == 0;
    }

    /*
     * Obtiene una cadena de un ResultSet, elimina los espacio en blanco al inicio y final
     */ 
    public static String getStringFromResultSet(ResultSet rs, String name) throws SQLException {
        String value = rs.getString(name);
        if (value != null)
            value = value.trim();
        return value;
    }

    /**
     * Convierte en una cadena el StackTrace
     * @param aThrowable Excepcion
     * @return Cadena con StackTrace
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Retorna el valor de una direccion IP
     * @param addr Direccion IP  (xxx.xxx.xxx.xxx)
     * @return Valor entero
     */
    public static long ipToInt(String addr) {
        try {
            String[] addrArray = addr.split("\\.");

            long num = 0;
            for (int i=0;i<addrArray.length;i++) {
                int power = 3-i;

                num += ((Integer.parseInt(addrArray[i])%256 * Math.pow(256,power)));
            }
            return num;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convierte una direccion IP (entero) en notacion  (xxx.xxx.xxx.xxx)
     * @param ip Direccion IP
     * @return IP en formato (xxx.xxx.xxx.xxx)
     */
    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >>  8) & 0xFF) + "." +
               ( ip        & 0xFF);
    }

    /**
     * Rellena una cadena por la izquierda
     * @param val Cadena a completar
     * @param fill Caracter de relleno
     * @param len Longitud total de la cadena generada
     * @return Cadena rellena a la izquierda
     */
    public static String leftFill(String val, char fill, int len) {
        int i = val.length();
        StringBuffer result = new StringBuffer();
        while (i < len) {
            result.append(fill);
            i++;
        }
        result.append(val);
        return result.toString();
    }

    public static String leftFill(int val, char fill, int len) {
        return leftFill(Integer.toString(val), fill, len);
    }

    public static String leftFill(long val, char fill, int len) {
        return leftFill(Long.toString(val), fill, len);
    }

    /**
     * Rellena una cadena por la derecha
     * @param val Cadena a completar
     * @param fill Caracter de relleno
     * @param len Longitud total de la cadena generada
     * @return Cadena rellena a la derecha
     */
    public static String rightFill(String val, char fill, int len) {
        int i = val.length();
        StringBuffer result = new StringBuffer();
        result.append(val);
        while (i < len) {
            result.append(fill);
            i++;
        }
        return result.toString();
    }

    public static String rightFill(int val, char fill, int len) {
        return rightFill(Integer.toString(val), fill, len);
    }

    public static String rightFill(long val, char fill, int len) {
        return rightFill(Long.toString(val), fill, len);
    }

    /*
     * Retorna un String con la primera letra en mayuscula y el resto minusculas
     */
    public static String capitalize(String name) {
        return name.toUpperCase().substring(0, 1) + name.toLowerCase().substring(1);
    }

    /*
     * Comprueba si dos cadenas son iguales, comprobando los nulos (null = null)
     */
    public static boolean compareStr(String a, String b) {
        return (a == null && b == null) || (a != null && b != null && a.equals(b));
    }

    /*
     * Sustituye los caracteres no permitidos en el XML
     */
    public static String getXMLString(String val) {
        val = val.replaceAll("&", "&amp;");
        val = val.replaceAll("<", "&lt;");
        val = val.replaceAll(">", "&gt;");
        return val;
    }

    public static String toUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }
    /**
     * Elimina los acentos de una cadena
     * @param val Valor a revisar
     * @return Valor sin acentos
     */
    public static String replaceTilde(String val) {
        if (val == null)
            return null;
        // á é í ó ú
        val = val.replaceAll("á", "a").replaceAll("é", "e").replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
        // Á É Í Ó Ú
        val = val.replaceAll("Á", "A").replaceAll("É", "E").replaceAll("Í", "I").replaceAll("Ó", "O").replaceAll("Ú", "U");
        // ñ Ñ ç Ç
        val = val.replaceAll("ñ", "n").replaceAll("Ñ", "&Ntilde;").replaceAll("ç", "&Ccedil;").replaceAll("Ç", "&Ccedil;");
        // à è ì ò ù
        val = val.replaceAll("à", "a").replaceAll("è", "e").replaceAll("ì", "i").replaceAll("ò", "o").replaceAll("ù", "u");
        // À È Ì Ò Ù
        val = val.replaceAll("À", "A").replaceAll("È", "E").replaceAll("Ì", "I").replaceAll("Ò", "O").replaceAll("Ù", "U");
        return val;
    }

    /*
     * Convierte una cadena a texto HTML
     */
    public static String getHTMLString(String val) {
        // < > "
        val = val.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
        return getHTMLTilde(val);
    }

    public static String getHTMLTilde(String val) {
        // ¡ ¿ º ª
        val = val.replaceAll("¡", "&iexcl;").replaceAll("¿", "&iquest;").replaceAll("º", "&ordm;").replaceAll("ª", "&ordf;");
        // á é í ó ú
        val = val.replaceAll("á", "&aacute;").replaceAll("é", "&eacute;").replaceAll("í", "&iacute;").replaceAll("ó", "&oacute;").replaceAll("ú", "&uacute;");
        // Á É Í Ó Ú
        val = val.replaceAll("Á", "&Aacute;").replaceAll("É", "&Eacute;").replaceAll("Í", "&Iacute;").replaceAll("Ó", "&Oacute;").replaceAll("Ú", "&Uacute;");
        // ñ Ñ ç Ç
        val = val.replaceAll("ñ", "&ntilde;").replaceAll("Ñ", "&Ntilde;").replaceAll("ç", "&Ccedil;").replaceAll("Ç", "&Ccedil;");
        // à è ì ò ù
        val = val.replaceAll("à", "&agrave;").replaceAll("è", "&egrave;").replaceAll("ì", "&igrave;").replaceAll("ò", "&ograve;").replaceAll("ù", "&ugrave;");
        // À È Ì Ò Ù
        val = val.replaceAll("À", "&Agrave;").replaceAll("È", "&Egrave;").replaceAll("Ì", "&Igrave;").replaceAll("Ò", "&Ograve;").replaceAll("Ù", "&Ugrave;");
        // ä ë ï ö ü
        val = val.replaceAll("ä", "&auml;").replaceAll("ë", "&euml;").replaceAll("ï", "&iuml;").replaceAll("ö", "&ouml;").replaceAll("ü", "&uuml;");
        // Ä Ë Ï Ö Ü
        val = val.replaceAll("Ä", "&Auml;").replaceAll("Ë", "&Euml;").replaceAll("Ï", "&Iuml;").replaceAll("Ö", "&Ouml;").replaceAll("Ü", "&Uuml;");
        return val.replaceAll("\n", "<br>").replaceAll("\r", " ");
    }

    private static final String PATTERN_EMAIL = "[_a-zA-Z0-9\\-]+(\\.[_a-z0-9\\-]+)*(\\.*)\\@[_a-z0-9\\-]+(\\.[a-z]{1,4})+";
    private static final String PATTERN_NAME = "[\\\"\\'>\\<]+";

    /**
     * De una direccion de email retorna el emai
     * @param email Direccion de email: "First Name" <email@email.com>
     * @return Email: email@email.com
     */
    public static String getEmailAddress(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        email = email.toLowerCase();
        Matcher matcher = pattern.matcher(email);
        matcher.find();
        return email.substring(matcher.start(), matcher.end());
    }

    /**
     * De una direccion de email retorna el nombre
     * @param email Direccion de email: "First Name" <email@email.com>
     * @return Nombre: First Name
     */
    public static String getEmailName(String email) {
        return email.replaceFirst(PATTERN_EMAIL, "").replaceAll(PATTERN_NAME, "");

    }

    /**
     * Valida un NIF, CIF o NIE
     * @param value Valor a verificar
     * @return 1 = NIF ok, 2 = CIF ok, 3 = NIE ok, -1 = NIF error, -2 = CIF error, -3 = NIE error, 0 = ??? error
     */
    public static int check_Nif_Cif_Nie(String value) {
        String temp = value.toUpperCase();
        String cadenadni="TRWAGMYFPDXBNJZSQVHLCKE";

        if (!"".equals(temp)) {
            //si no tiene un formato valido devuelve error
            Pattern patternLetraNumerosAlgo = Pattern.compile("^[A-Z]{1}[0-9]{7}[A-Z0-9]{1}$");
            Pattern patternT = Pattern.compile("^[T]{1}[A-Z0-9]{8}$");
            Pattern patternNif = Pattern.compile("^[0-9]{8}[A-Z]{1}$");
            if (patternLetraNumerosAlgo.matcher(temp).matches() || patternT.matcher(temp).matches() || patternNif.matcher(temp).matches()) {
                //comprobacion de NIFs estandar
                if (patternNif.matcher(temp).matches()) {
                    int posicion = Integer.parseInt(value.substring(0,8)) % 23;
                    char letra = cadenadni.charAt(posicion);
                    char letradni=temp.charAt(8);
                    return letra == letradni ? 1 : -1;
                }

                //algoritmo para comprobacion de codigos tipo CIF
                int sumaPares = Integer.parseInt(value.substring(2, 3)) + Integer.parseInt(value.substring(4, 5)) + Integer.parseInt(value.substring(6, 7));
                int sumaImpares = 0;
                for (int i = 1; i < 8; i += 2)	{
                	int digitoImpar = Integer.parseInt(value.substring(i, i+1));
                	int digitox2 = 2 * digitoImpar;
                	int sumaDigitos = digitox2 % 10;
                	if (digitox2 >= 10) {
                		sumaDigitos++;
                	}
                	sumaImpares += sumaDigitos;
                }
                int sumaTotal = sumaPares + sumaImpares;
                int restoTotal = sumaTotal % 10;
                int n = 10 - restoTotal; //en teoría si es cero no hay que restar

                //comprobacion de NIFs especiales (se calculan como CIFs)
                Pattern patternNifEsp = Pattern.compile("^[KLM]{1}.*");
                if (patternNifEsp.matcher(temp).matches())	{
                	char cosa = (char)(64+n);
                    return (value.charAt(8) == cosa) ? 1 : -1;
                }

                //comprobacion de CIFs
                Pattern patternCif = Pattern.compile("^[ABCDEFGHJNPQRSUVW]{1}.*");
                if (patternCif.matcher(temp).matches())	{
                    char numeroFinalCorrecto = (n + "").charAt(0);
                	char letraFinalCorrecta = (char)(64+n);
                	char caracterAValidar = value.charAt(8);
                    return ((caracterAValidar == letraFinalCorrecta) || (caracterAValidar == numeroFinalCorrecto)) ? 2 : -2;
                }

                //comprobacion de NIEs
                //T
                Pattern patternNie = Pattern.compile("^[T]{1}.*");
                if (patternNie.matcher(temp).matches())	{
                    Pattern patternRaro = Pattern.compile("^[T]{1}[A-Z0-9]{8}$");
                    boolean b = patternRaro.matcher(temp).matches();
                    char cosa = (b? (char)1: (char)0);
                    return (value.charAt(8) == cosa) ? 3 : -3;
                }

                //XYZ
                Pattern patternXyz = Pattern.compile("^[XYZ]{1}.*");
                if (patternXyz.matcher(temp).matches())	{
                	temp = temp.replace('X', '0').replace('Y', '1').replace('Z', '2');
                	String cosa = temp.substring(0, 8);
                	int pos = Integer.parseInt(cosa) % 23;
                    return (value.charAt(8) == cadenadni.charAt(pos)) ? 3 : -3;
                }
            }
            return 0;
        }
        return 0;
    }

    /**
     * Trunca un texto
     * @param text Texto a truncar
     * @param size Cantidad maxima de caracteres
     * @return  Texto truncado
     */
    public static String truncText(String text, int size) {
        if (text != null && text.length() > size)
            return text.substring(0, size) + "...";
        return text;
    }

    /**
     * Convierte un arreglo de bytes en Hexadecimal
     *
     * @param hash bytes
     * @return Representacion Hexadecimal
     */
    public static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * Retorna el valor entero de un objeto numerico
     *
     * @param value Valor a convertir
     * @return Valor entero (Long)
     */
    public static Long getLongValue(Object value) {
        if (value == null)
            return null;
        if (value instanceof BigInteger)
            return ((BigInteger) value).longValue();
        if (value instanceof BigDecimal)
            return ((BigDecimal) value).longValue();
        if (value instanceof Integer)
            return ((Integer) value).longValue();
        if (value instanceof Long)
            return ((Long) value);
        return null;
    }

    public static Integer getIntegerValue(Object value) {
        if (value == null)
            return null;
        if (value instanceof BigInteger)
            return ((BigInteger) value).intValue();
        if (value instanceof BigDecimal)
            return ((BigDecimal) value).intValue();
        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof Long)
            return ((Long) value).intValue();
        return null;
    }
}
