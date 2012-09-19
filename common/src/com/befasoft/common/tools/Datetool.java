package com.befasoft.common.tools;



import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date: 22-ene-2006
 * Time: 11:48:42
 */
public class Datetool {

    Date timer;

    /**
     * Inicializa un contador de tiempo
     */
    public void initTimeCounter() {
        timer = new Date();
    }

    /**
     * Retorna el tiempo transcurrido en milisegundos
     * @return Tiempo transcurrido desde que se inicio el timer (initTimeCounter()) en milisegundos
     */
    public long getTimer() {
        Date ct = new Date();
        return ct.getTime() - timer.getTime();
    }

    /**
     * Retorna el tiempo transcurrido hh:mm:ss.sss
     * @return Retorna el valor del timer en una cadena
     */
    public String getStringTimer() {
        long time = getTimer();
        long seg  = time / 1000;
        long min  = seg / 60;
        long hour = min / 60;
        return Converter.leftFill(hour, '0', 2)+":"+Converter.leftFill(min % 60, '0', 2)+":"+Converter.leftFill(seg % 60, '0', 2)+"."+(time-seg*1000); 
    }

    /**
     * Obtiene el año en curso
     * @return Año en curso
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.YEAR);
    }

    /**
     * Obtiene el mes en curso
     * @return Mes actual (1 - Enero, 2 - Febrero, ...)
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.MONTH)+1;
    }

    /**
     * Obtiene el mes en curso
     * @return Mes actual (1 - Enero, 2 - Febrero, ...)
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Dada una fecha retorna el numero del mes
     * @param fec Fecha
     * @return Numero del mes (1 - Enero, 2 - Febrero, ...)
     */
    public static int getMonth(Date fec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fec);
        return cal.get(Calendar.MONTH)+1;
    }

    /**
     * Retorna una fecha
     * @param day Dia
     * @param month Mes
     * @param year A�o
     * @return Fecha
     */
    public static Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * Retorna una hora
     * @param hour Hora
     * @param minute Minutos
     * @param second Segundos
     * @return Hora
     */
    public static Date getTime(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * Quita la infrormacion de la hora
     * @param value Fecha
     * @return Fecha con la hora en 00:00.00.000
     */
    public static Date clearTime(Date value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Retorna la hora actual
     * @return Hora actual
     */
    public static Date getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 1970);
        return cal.getTime();
    }

    /**
     * Obtiene la semana actual
     * @return Semana actual
     */
    public static int getCurrentWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.WEEK_OF_YEAR)+1;
    }

    /**
     * Dada una fecha retorna el año
     * @param fec Fecha
     * @return Año de la fecha
     */
    public static int getYear(Date fec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fec);
        return cal.get(Calendar.YEAR);
    }

    /**
     * Retorna el primer dia del mes "count" meses anteriores al mes "01/mes/year"
     * @param year A�o
     * @param mes Mes (1 - Enero, 2 - Febrero, ...)
     * @param count Numeros de meses
     * @return Fecha con el primer dia del mes correspondiente
     */
    public static Date getFirstDayOfPreviousMonth(int year, int mes, int count) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, mes-1, 1);
        if (count != 0)
            cal.add(Calendar.MONTH, -count);
        return cal.getTime();
    }

    /**
     * Retorna el primer dia del proximo mes
     * @param year Año
     * @param mes Mes (1 - Enero, 2 - Febrero, ...)
     * @return Fecha con el primer dia del mes siguiente
     */
    public static Date getFirstDayOfNextMonth(int year, int mes) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, mes-1, 1);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * Retorna la cantidad de meses entre dos meses
     * @param cal1 Primer mes
     * @param cal2 Segundo mes
     * @return Cantidad de meses entre los argumentos
     */
    public static int getMonthBetween(Calendar cal1, Calendar cal2) {
        int years = Math.abs(cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR));
        int months;
        if (years == 0)
            months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        else
            if (cal1.before(cal2))
                months =  (years - 1) * 12 + 12 - cal1.get(Calendar.MONTH) + cal2.get(Calendar.MONTH);
            else
                months =  (years - 1) * 12 + 12 - cal2.get(Calendar.MONTH) + cal1.get(Calendar.MONTH);
        return Math.abs(months)+1;
    }

    /**
     * Retorna el ultimo dia de un mes
     * @param year Año
     * @param mes Mes (1 - Enero, 2 - Febrero, ...)
     * @return Ultimo dia del mes
     */
    public static int getLastDayOfMonth(int year, int mes) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, mes-1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Retorna el ultimo dia de un año
     * @param year Año
     * @return Ultimo dia del año
     */
    public static Date getLastDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, 11, 31);
        return cal.getTime();
    }

    /**
     * Retorna el primer dia de un año
     * @param year Año
     * @return Primer dia del año
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, 0, 1);
        return cal.getTime();
    }

    /**
     * Retorna el dia de hoy con hora 00:00:00
     * @return El dia de hoy
     */
    public static Date getToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Verifica si dos fecha son iguales sin tomar en cuenta la hora
     * @param d1 Fecha 1
     * @param d2 Fecha 2
     * @return Verdadero si esta en mismo día
     */
    public static boolean equalDate(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        return cal1.compareTo(cal2) == 0;
    }

    /**
     * Obtiene una fecha de una fecha obtenida de un XML (WebServices)
     * 
     * @param xml Fecha del XML
     * @return Fecha
     */
    public static Date getDate(XMLGregorianCalendar xml) {
        if (xml == null || !xml.isValid())
            return null;
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.set(xml.getYear(), xml.getMonth()-1, xml.getDay());
        return cal.getTime();
    }
}

