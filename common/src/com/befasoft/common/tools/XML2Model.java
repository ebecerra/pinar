/*
 * Project : Vinco- Service Delivery Platform
 *
 * Copyright (c) 2009 Vinco Mobile S.L.
 * All rights reserved
 */

package com.befasoft.common.tools;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.iBaseBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.hibernate.*;

import java.io.*;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XML2Model {

    private final static Log log = LogFactory.getLog(XML2Model.class);

    private String xmlFile;

    private Session session;
    private Session oldSession;

    private OutputStream failOutput;
    private boolean foundErrors = false;


    public XML2Model() {
    }

    /**
     * Dada una colleccion de elementos de tipo iBaseBean retorna una lista con sus claves primarias.
     *
     * @param beans Colleccion de beans
     * @return List con los primaryKey de los beans.
     */
    public static List persistentToId(Collection beans) {
        Vector result = new Vector(beans != null ? beans.size() : 0);
        for (Iterator iterator = beans.iterator(); iterator.hasNext(); ) {
            iBaseBean bean = (iBaseBean) iterator.next();

            if (bean != null) {
                result.add(bean.getPrimaryKey());
            }
        }

        return result;
    }

    /**
     * Retorna la lista de elementos que estan en la primera colleccion que NO estan en la segunda.
     *
     * @param oldBeanBag Coleccion original de beans
     * @param newBag     Nueva colleccion
     * @return Elementos que estan en 'oldBeanBag' que la clave primaria NO estan en 'newBag'
     */
    public static List<iBaseBean> excludedBeans(Collection oldBeanBag, Collection newBag) {
        List<iBaseBean> result = new ArrayList<iBaseBean>(oldBeanBag.size());

        for (Iterator iterator = oldBeanBag.iterator(); iterator.hasNext(); ) {
            iBaseBean item = (iBaseBean) iterator.next();

            // Si el elemento no esta en newBag entonces se a?ade a la lista de excluidos.
            if (item != null && !newBag.contains(item.getPrimaryKey())) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Retorna los elementos que esten en ambas colecciones.
     *
     * @param bag1 Coleccion  de beans
     * @param bag2 Coleccion de beans
     * @return Elementos que estan en ambas colecciones
     */
    public static List intercept(Collection bag1, Collection bag2) {
        List result = new Vector();

        if (bag1 != null && bag2 != null) {

            for (Iterator iterator = bag1.iterator(); iterator.hasNext(); ) {
                iBaseBean item1 = (iBaseBean) iterator.next();

                if (item1 != null) {
                    for (Iterator it2 = bag2.iterator(); it2.hasNext(); ) {
                        iBaseBean item2 = (iBaseBean) it2.next();

                        if (item2 != null && item1.getPrimaryKey().equals(item2.getPrimaryKey())) {
                            result.add(item1);
                            break;
                        }
                    }

                }
            }

        }

        return result;
    }

    public void openSession() {
        session = HibernateUtil.currentSession();
//        oldSession = HibernateUtil.getCurrentSession();
//        session = HibernateUtil.openSession();
//        HibernateUtil.setCurrentSession(session);
    }

    public void closeSession() {
//        session.close();
//        oldSession.flush();
//        HibernateUtil.setCurrentSession(oldSession);
//        HibernateUtil.closeSession();
    }

    protected static String getItemXML(Element item) {
        List items = item.elements();
        items = null;
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                Element element = (Element) items.get(i);
                item.remove(element);
            }
        }

        String result = item.asXML();

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                Element element = (Element) items.get(i);
                item.add(element);
            }
        }

        return result;
    }

    protected static void dump(String message) {
        System.out.println(message);
    }

    protected static void dumpItem(String message, Element item) {
        dump("" + message + ":" + getItemXML(item));
    }

    protected static String getClassName(String className) {
        if (className == null) {
            return "N/A";
        }
        else {
            if (className.indexOf('.') == -1) {
                return className;
            }
            else {
                return className.substring(className.lastIndexOf('.') + 1);
            }
        }
    }

    protected static String getText(Element item) {
        String result = item.getText();
        if (result == null) {
            result = "";
        }
        else {
            result = result.trim();
            result = StringUtil.replaceAll(result, "\n", "");
            result = StringUtil.replaceAll(result, "\\n", "\n");
            //en los copy/export de tiendas, los saltos de linea de las propiedades de portlet se escapan con caracter especial
            //para recuperar posteriormente el salto, también descapamos los textos dado que en SerializableBean se hace un
            //StringUtil.escapeToXml(result) para serializar en XML ok.
            result = StringUtil.replaceAll(result, "break.line", "\n");
            result = StringUtil.escapeFromXml(result);
        }

        return result;
    }

    protected static String getClassName(Object obj) {
        if (obj == null) {
            return "N/A";
        }
        else {
            return getClassName(obj.getClass().getName());
        }
    }

    protected static String getBeanName(iBaseBean bean) {
        if (bean == null) {
            return "N/A";
        }
        else {
            return getClassName(bean) + "(id=" + bean.getPrimaryKey() + ")";
        }
    }


    public Collection parseBag(iBaseBean bean, Element beanItem) throws Throwable, ParseException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        String method = "set";
        if (beanItem.attribute("method") != null) {
            method = beanItem.attribute("method").getText();
        }

        List items = beanItem.elements();
        Collection newBag = new HashSet(items.size());
        int errorCount = 0;
        int size = items.size();
        for (int i = 0; i < items.size(); i++) {
            Element item = (Element) items.get(i);

            Transaction tx = null;
            try {
                if (bean == null) {
                    //System.out.println("- Procesando " + (i + 1) + "/" + size + " (errores:" + errorCount + ")");
                    tx = session.beginTransaction();
                    tx.begin();
                }
                else {
                    //System.out.println("- a?adiendo " + (i + 1) + "/" + size + " (errores:" + errorCount + ")");
                }

                Object bagBean = parse(bean, item);

                if (tx != null) {
                    tx.commit();
                    dump(">>>>>> COMMIT " + bagBean);
                }
                newBag.add(bagBean);
            }
            catch (Throwable e) {
                foundErrors = true;
                e.printStackTrace(System.out);
                //System.exit(-1);  TODO eliminamos salida drástica

                errorCount++;

                if (bean == null) {

                    System.out.println("Exception en: " + item.asXML());
                    e.printStackTrace(System.out);
                    failOutput.write(("\n<--\n").getBytes());
                    e.printStackTrace(new PrintStream(failOutput));
                    failOutput.write(("\n-->\n").getBytes());
                    failOutput.write((item.asXML() + "\n").getBytes());

                    if (tx != null) {
                        tx.rollback();
                    }


                }
                else {

                    throw e;

                }

                //session.close();
                session = HibernateUtil.currentSession();
                return null;
            }
        }


        Collection result = null;
        // Si nos pasan un bean entonces pedimos la propiedad al bean.
        if (bean != null) {
            Collection oldBag = (Collection) getFieldValue(bean, beanItem.getName());

            if (oldBag != null) {

                if (!"add".equals(method)) {
                    result = oldBag;
                    List newBagIds = persistentToId(newBag);
                    List oldBagIds = persistentToId(oldBag);

                    // Buscando los elementos que hay que eliminar de la coleccion que
                    // ya tenia el bean
                    List needRemoveds = excludedBeans(oldBag, newBagIds);
                    // Busco si estos elementos tienen que ser eliminados ademas de quitarlos de la colleccion
                    for (Iterator it = needRemoveds.iterator(); it.hasNext(); ) {
                        iBaseBean needRemoved = (iBaseBean) it.next();

                        //System.out.println("needRemoved = " + needRemoved);
                        //System.out.println("bean = " + bean);
                        //System.out.println("item.getParent=" + getFieldValue(needRemoved, beanItem.getParent().getName()));
                        if (bean.equals(getFieldValue(needRemoved, beanItem.getParent().getName()))) {
                            //Transaction tx = session.beginTransaction();
                            session.delete(needRemoved);
                            //System.out.println(">>>> REMOVING COLLECTION ITEM:" + needRemoved);
                            //tx.commit();
                        }
                        else {
                            oldBag.remove(needRemoved);
                        }
                    }

                    // Quitando de la coleccion
                    //oldBag.removeAll(needRemoveds);

                    // Buscando los elementos que hay que adicionar
                    List needAdds = excludedBeans(newBag, oldBagIds);
                    oldBag.addAll(needAdds);
                }
                else {
                    // Adicinando a la coleccion actual
                    result = new HashSet(oldBag.size() + newBag.size());
                    result.addAll(oldBag);
                    result.addAll(newBag);
                }
            }
        }


        if (result == null) {
            // No hay bean donde buscar la propiedad por lo que se retorna la nueva
            // Collection
            result = newBag;
        }


        return result;
    }

    public EntityBean parseOne
            (Element
                     item) throws Throwable, ParseException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        EntityBean bean = null;

        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            bean = (EntityBean) parse(null, item);

            if (tx != null) {
                tx.commit();
                dump(">>>>>> COMMIT " + bean);
            }
        }
        catch (Throwable e) {
            e.printStackTrace(System.out);
            //System.exit(-1);
            foundErrors = true;

            if (bean == null) {

                System.out.println("Exception en: " + item.asXML());
                e.printStackTrace(System.out);
                failOutput.write(("\n<--\n").getBytes());
                e.printStackTrace(new PrintStream(failOutput));
                failOutput.write(("\n-->\n").getBytes());
                failOutput.write((item.asXML() + "\n").getBytes());

                if (tx != null) {
                    tx.rollback();
                }

            }
            else {
                throw e;
            }

            //session.close();
            session = HibernateUtil.currentSession();
        }
        return bean;
    }


    public Object parse
            (iBaseBean
                     bean, Element
                    item) throws Throwable, InstantiationException, ClassNotFoundException, ParseException, InvocationTargetException {
        if (item == null) {
            System.out.println("item = " + item);
        }
        Attribute attr = item.attribute("type");

        if (attr == null) {
            String result = getText(item);
            //System.out.println(item.getName() + "=" + result);
            return result;
        }

        String type = attr.getValue();
        if (type.equals("refClass")) {
            return parseReference(bean, item);
        }
        else if (type.equals("manager")) {
            return parseManager(bean, item);
        }
        else if (type.equals("bag")) {
            return parseBag(bean, item);
        }
        else if (type.equals("time")) {
            return parseTime(getText(item));
        }
        else if (type.equals("date")) {
            return parseDate(getText(item));
        }
        else if (type.equals("bool")) {
            return parseBool(getText(item));
        }
        else if (type.equals("int")) {
            return parseInt(getText(item));
        }
        else if (type.equals("long")) {
            return parseLong(getText(item));
        }
        else if (type.equals("double")) {
            return parseDouble(getText(item));
        }
        else if (type.equals("password")) {
            return AlgorithmSHA1.calcSHA1(getText(item));
        }
        else {
            return parseClass(item, type, bean);
        }
    }

    private iBaseBean parseClass
            (Element
                     item, String
                    className, iBaseBean
                    parentBean) throws
            Throwable, IllegalAccessException, InstantiationException, ParseException, InvocationTargetException {
        dumpItem("  >> item", item);

        Class beanClass = Class.forName(className);
        String parentName = null;
        // Buscando si tiene un metodo setXXXX(parentBean)
        if (parentBean != null) {
            Method[] beanMethods = beanClass.getMethods();
            for (int j = 0; j < beanMethods.length; j++) {
                Method beanMethod = beanMethods[j];

                if (beanMethod.getName().startsWith("set") &&
                        beanMethod.getParameterTypes().length == 1 &&
                        beanMethod.getParameterTypes()[0].isAssignableFrom(parentBean.getClass())) {
                    parentName = beanMethod.getName().substring(3);
                }
            }
        }

        iBaseBean bean = findEntity(item, className, parentBean);

        //System.out.println("session.contains(" + bean + ") = " + session.contains(bean));

        boolean needSave = !session.contains(bean) || item.element("id") != null;

        // Se ponen todos los atributos que no sean bag, ref o manager
        List fields = item.elements();
        for (int i = 0; i < fields.size(); i++) {
            Element fieldItem = (Element) fields.get(i);

            String name = fieldItem.getName();
            String type = fieldItem.attributeValue("type");
            // En el caso de ser un manager no hay que hacer ninguna accion.
            //if (!"manager".equals(type) && !"bag".equals(field)) {
            if (!"bag".equals(type)) {
                //System.out.println("type = " + field);
                Object value = parse(bean, fieldItem);

                // Si el objeto existe y el campo solo se pone cuando el objeto es nuevo entonces nos saltamos el set
                if (fieldItem.attribute("onlynew") != null && !bean.isNewEntity()) {
                    continue;
                }
                setFieldValue(bean, name, value);
            }
        }

        if (needSave) {
            System.out.println(">>>> SAVING before references:" + bean);
            session.save(bean);
            session.flush();
        }

        // Se ponen los atributos de tipo bag, ref y manager
        for (int i = 0; i < fields.size(); i++) {
            Element fieldItem = (Element) fields.get(i);

            String name = fieldItem.getName();
            String type = fieldItem.attributeValue("type");

            // En el caso de ser un manager no hay que hacer ninguna accion.
            if ("bag".equals(type)) {
                Object value = parse(bean, fieldItem);
                setFieldValue(bean, name, value);
                session.flush();
            } /*else if ("manager".equals(type)) {
                setFieldValue(bean, name, callManagerMethod(bean, fieldItem););
            }*/
        }

//        if (needSave) {
//            System.out.println(">>>> SAVING after references:"+bean);
//            session.save(bean);
//        }

        return bean;
    }

    private Object parseManager
            (iBaseBean
                     bean, Element
                    item) throws Throwable, ParseException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return callManagerMethod(bean, item);
    }

    private Boolean parseBool
            (String
                     boolStr) {
        if (boolStr == null || "".equals(boolStr)) {
            return null;
        }
        if ("0".equalsIgnoreCase(boolStr)
                || "false".equalsIgnoreCase(boolStr)
                || "no".equalsIgnoreCase(boolStr)
                || "not".equalsIgnoreCase(boolStr)) {
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }

    private Date parseDate
            (String
                     dateStr) throws ParseException {
        Date result = null;
        if (dateStr != null && !dateStr.equals("")) {
            if ("now".equals(dateStr)) {
                result = new Date();
            }
            else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                try {
                    result = format.parse(dateStr.toString());
                }
                catch (Throwable e) {
                    try {
                        result = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(dateStr);
                    }
                    catch (Throwable e2) {
                        result = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateStr);
                    }

                }
            }
        }
        return result;
    }

    private Time parseTime(String timeStr) throws ParseException {
        Date result = null;
        if (timeStr != null && !timeStr.equals("")) {
            if ("now".equals(timeStr)) {
                result = new Date();
            }
            else {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.S");
                try {
                    result = format.parse(timeStr.toString());
                }
                catch (Throwable e) {
                    result = (new SimpleDateFormat("HH:mm")).parse(timeStr);
                }
            }
        }
        return result != null ? new Time(result.getTime()) : null;
    }


    private Long parseLong
            (String
                     longStr) {
        Long result = null;
        if (longStr != null && !longStr.equals("")) {
            result = new Long(longStr);
        }

        return result;
    }

    private Integer parseInt
            (String
                     intSr) {
        Integer result = null;
        if (intSr != null && !intSr.equals("")) {
            result = new Integer(intSr);
        }

        return result;
    }


    private Double parseDouble
            (String
                     doubleStr) {
        Double result = null;
        if (doubleStr != null && !doubleStr.equals("")) {
            result = new Double(doubleStr);
        }

        return result;
    }


    private iBaseBean parseReference
            (iBaseBean
                     bean, Element
                    item) throws Throwable, InvocationTargetException, ClassNotFoundException {

        String className = item.attributeValue("refClass");
        String names = item.attributeValue("refAttr");
        String values = getText(item);


        String[] fieldNames = TypeParser.convertToArrayString(names, "$");
        for (int i = 0; i < fieldNames.length; i++) {
            fieldNames[i] = fieldNames[i].trim();
        }
        Object[] fieldValues = TypeParser.convertToArrayString(values, "$");
        for (int i = 0; i < fieldValues.length; i++) {
            fieldValues[i] = fieldValues[i].toString().trim();
        }


        if (fieldNames.length != fieldValues.length) {
            //throw new RuntimeException("No coinciden la cantidad de campos (refAttr) con la cantida de valores." + getItemXML(item));
            // Sino hay valores entonces se intenta buscar como Objetos en la entidad
            fieldValues = new Object[fieldNames.length];
            for (int i = 0; i < fieldNames.length; i++) {
                String fieldName = fieldNames[i].trim();
                Element newItem = item.element(fieldName);
                fieldValues[i] = parse(null, newItem);
            }
            //dump("No coinciden la cantidad de campos (refAttr) con la cantida de valores." + getItemXML(item));
        }

        String parentName = null;

        // Busco si el bean ya tiene esta propiedad
        if (bean != null) {
            Object fieldValue = (Object) getFieldValue(bean, item.getName());
            iBaseBean beanFieldValue = fieldValue instanceof iBaseBean ? (iBaseBean) fieldValue : null;
            if (beanFieldValue != null) {
                // Busco si la propiedad en el campo refAttr tiene el mismo valor que busco
                Object beanFieldValueKey = getFieldValue(beanFieldValue, names);


                if (beanFieldValueKey == null) {
                    // No hay objeto se asigna null,

                }
                else if (beanFieldValueKey.equals(values)) {
                    // Ya esta puesta la referencia correcta, no hay que buscar nada a BD
                    dump("  - exist ref (" + getClassName(className) + ",[" + names + "," + values + ") = " + beanFieldValue);
                    return beanFieldValue;
                }
            }
            else {
                // Busco si tiene un enlace al padre
                Method[] beanMethods = Class.forName(className).getMethods();
                for (int j = 0; j < beanMethods.length; j++) {
                    Method beanMethod = beanMethods[j];

                    if (beanMethod.getName().startsWith("get") &&
                            beanMethod.getParameterTypes().length == 0 &&
                            beanMethod.getReturnType().equals(bean.getClass())) {
                        parentName = beanMethod.getName().substring(3);
                        parentName = Character.toLowerCase(parentName.charAt(0)) + parentName.substring(1);
                        break;
                    }
                }
            }
        }

        // Buscando la referencia en BD
        String query = "from " + className + " where ";
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            query = query + (i > 0 ? " and " : "") + fieldName + " = ? ";
        }

        if (parentName != null) {
            query = query + " and " + parentName + " = ? ";
        }

        Query hQuery = session.createQuery(query);

        for (int i = 0; i < fieldValues.length; i++) {
            hQuery.setParameter(i, fieldValues[i]);
        }

        String message = "";
        if (parentName != null) {
            message = "  - finding ref (" + getClassName(className) + ",[" + names + " = " + values + "] and " + parentName + " = " + bean.getPrimaryKey() + ")";
            hQuery.setParameter(fieldNames.length, bean);
        }
        else {
            message = "  - finding ref (" + getClassName(className) + ",[" + names + " = " + values + "]) NOT PARENT " + bean;
        }

        dump(message);

        List results = hQuery.list();

        if (results == null || results.size() == 0) {
            throw new RuntimeException("NO se encuentra la referencia:" + getItemXML(item));
        }
        else if (results.size() > 1) {
            //System.out.println("bean = " + bean);
            for (int i = 0; i < results.size(); i++) {
                iBaseBean persistenceBean = (iBaseBean) results.get(i);
                //System.out.println(" ref[" + i + "] = " + persistenceBean);
            }
            throw new RuntimeException("Verifique la BD. Se han encontrado MAS de un elemento en la referencia:" + getItemXML(item));
        }
        else {
            dump("  - find ref (" + getClassName(className) + "," + names + "," + values + ") = " + results.get(0));

            return (iBaseBean) results.get(0);
        }
    }


    public Collection parse
            () throws
            Throwable, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ParseException, IOException {
        // Si existia el fichero de error entonces lo borro
        File failFile = new File(this.getXmlFile() + ".FAIL.xml");
        if (failFile.exists()) {
            failFile.delete();
        }
        failOutput = new FileOutputStream(failFile);

        Collection result = null;

        foundErrors = false;
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(this.getXmlFile());

            Element root = document.getRootElement();

            failOutput.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n").getBytes());
            failOutput.write(("<" + root.getName() + ">\n").getBytes());

            result = parseBag(null, root);

            failOutput.write(("</" + root.getName() + ">\n").getBytes());
            failOutput.close();
        }
        finally {
            // Si al terminar no se han encontrado errores entonces se borra el archivo.
            System.out.println("foundErrors = " + foundErrors);
            if (!foundErrors) {
                (new File(failFile.getAbsolutePath())).delete();
            }
        }

        return result;

    }

    private iBaseBean findEntity
            (Element
                     item, String
                    className, iBaseBean
                    parentBean) throws
            Throwable, IllegalAccessException, InstantiationException, InvocationTargetException, ParseException {

        Class beanClass = Class.forName(className);
        String parentName = null;

        if (item.getName().equals("priceRule")) {
            parentName = null;
        }
        // Busco si tengo un metodo setXXXX(parentBean)
        if (parentBean != null) {
            Method[] beanMethods = beanClass.getMethods();
            for (int j = 0; j < beanMethods.length; j++) {
                Method beanMethod = beanMethods[j];

                if (beanMethod.getName().startsWith("set") &&
                        beanMethod.getParameterTypes().length == 1 &&
                        beanMethod.getParameterTypes()[0].isAssignableFrom(parentBean.getClass())) {
                    parentName = beanMethod.getName().substring(3);
                    parentName = Character.toLowerCase(parentName.charAt(0)) + parentName.substring(1);
                    break;
                }
            }
        }


        iBaseBean result = null;
        String key = item.attributeValue("key");
        if (key == null) {
            throw new RuntimeException("No hay 'key' en " + item.asXML());
        }

        String[] keys = TypeParser.convertToArrayString(key);
        Object[] values = new Object[keys.length];

        // Buscando el valor de los parametros
        for (int i = 0; i < keys.length; i++) {
            keys[i] = keys[i].trim();
            String keyName = keys[i];
            values[i] = parse(parentBean, item.element(keyName));
        }

        // obtencion de la clase que quiero asociar
        String query = "from " + className + " where ";

        for (int i = 0; i < keys.length; i++) {
            String keyName = keys[i];

            if (i != 0) {
                query = query + " and ";
            }
            query = query + " " + keyName + " = ? ";
        }

        // Si tiene un padre entonces se enlaza con el.
        if (parentName != null) {
            query = query + " and " + parentName + " = ? ";
        }

        System.out.println("query = " + query);
        Query hQuery = (Query) session.createQuery(query);

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];

            //System.out.println("query[" + i + "].value = " + value);
            ;

            if (value instanceof iBaseBean) {
                hQuery.setParameter(i, value);
            }
            else if (value instanceof String) {
                hQuery.setString(i, "" + value);
            }
            else if (value instanceof Date) {
                hQuery.setDate(i, (Date) value);
            }
            else if (value instanceof Integer) {
                hQuery.setInteger(i, ((Integer) value).intValue());
            }
            else if (value instanceof Long) {
                hQuery.setLong(i, ((Long) value).longValue());
            }
            else if (value instanceof Number) {
                hQuery.setDouble(i, ((Number) value).doubleValue());
            }
            else {
                throw new RuntimeException("Tipo de dato de 'key' no soportado:" + value);
            }
        }

        if (parentName != null) {
            //System.out.println("query[" + keys.length + "].value = " + parentBean);
            ;
            hQuery.setParameter(keys.length, parentBean);
        }

        //System.out.println("query = " + query);
        List results = hQuery.list();

        // Si no se encuentra o no tiene clave entonces se crea una instancia
        if (results == null || results.size() == 0) {
            // No se encuentra entonces se le ponen los valores que ya he buscado para no tener que buscarlos de nuevo.
            result = buildEntity(className, parentBean);

            for (int i = 0; i < keys.length; i++) {
                String field = keys[i];
                Object value = values[i];

                setFieldValue(result, field, value);
                if (!field.equals("id")) {
                    item.remove(item.element(key));
                }
            }
        }
        else if (results.size() > 1) {
            dump(" find more one " + results);
        }
        else {
            result = (iBaseBean) results.get(0);
        }

        return result;
    }


    private iBaseBean buildEntity
            (String
                     className, iBaseBean
                    parentBean) throws
            ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, ParseException {
        Class beanClass = Class.forName(className);
        String parentName = null;
        // Busco si tengo un metodo setXXXX(parentBean)
        if (parentBean != null) {
            Method[] beanMethods = beanClass.getMethods();
            for (int j = 0; j < beanMethods.length; j++) {
                Method beanMethod = beanMethods[j];

                if (beanMethod.getName().startsWith("set") &&
                        beanMethod.getParameterTypes().length == 1 &&
                        beanMethod.getParameterTypes()[0].equals(parentBean.getClass())) {
                    parentName = beanMethod.getName().substring(3);
                    parentName = Character.toLowerCase(parentName.charAt(0)) + parentName.substring(1);
                    break;
                }
            }
        }

        iBaseBean result = (iBaseBean) beanClass.newInstance();

        if (parentName != null) {
            setFieldValue(result, parentName, parentBean);
        }
        dump("  - create entity (" + getClassName(className) + ") = " + result);
        return result;
    }

    private static String getSetMethodName
            (String
                     tag) {
        String firstChar = tag.substring(0, 1).toUpperCase();
        String lastChars = tag.substring(1);

        String methodName = "set" + firstChar + lastChars;

        if (methodName.indexOf(".") > 0) {
            // permite llamar por ej: el set de repository.folderName en el bean
            // que se traduce en una llamada a  setRepository_folderNameXML

            methodName = methodName.replaceAll("\\.", "_"); // los métodos en java no pueden tener un punto como parte de su nombre
            methodName += "XML";
        }

        return methodName;
    }

    public static void setFieldValue
            (iBaseBean
                     bean, String
                    fieldName, Object
                    fieldValue) throws IllegalAccessException, InvocationTargetException {
        System.out.println("XML2Model.setFieldValue(" + bean + "," + fieldName + "," + fieldValue + ")");
        if (bean == null) {
            System.out.println("Es null");
        }
        // obtencion del nombre del metodo Set
        String methodName = getSetMethodName(fieldName);

        // Busqueda del metodo
        Method[] methods = bean.getClass().getMethods();
        Method attrMethod = null;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().equals(methodName)) {
                attrMethod = method;
                break;
            }
        }

        if (attrMethod != null) {
            //System.out.println("    - " + getBeanName(bean) + "." + attrMethod.getName() + "(" + fieldValue + ":" + getClassName(fieldValue) + ")");
            if (fieldValue instanceof Collection) {
                Class param = attrMethod.getParameterTypes()[0];
                if (List.class.isAssignableFrom(param) && !(fieldValue instanceof List)) {
                    attrMethod.invoke(bean, new Object[]{new ArrayList((Collection) fieldValue)});
                }
                else {
                    attrMethod.invoke(bean, new Object[]{fieldValue});
                }
            }
            else {
                attrMethod.invoke(bean, new Object[]{fieldValue});
            }
        }
        else {
            throw new RuntimeException("No exits method " + methodName + " in class " + bean.getClass());
        }
    }


    public static Object getFieldValue
            (iBaseBean
                     bean, String
                    fieldName) throws IllegalAccessException, InvocationTargetException {

        Method attrMethod = getFieldGetMethod(bean, fieldName);

        if (attrMethod != null) {
            Object result = attrMethod.invoke(bean);
            //System.out.println("    - " + getBeanName(bean) + "." + attrMethod.getName() + "() = " + result + ":" + getClassName(result));
            return result;
        }
        else {
            //throw new RuntimeException("No exits method get" + methodName + " or is" + methodName + " in class " + bean.getClass());
            return null;
        }
    }


    private static Method getFieldGetMethod(iBaseBean bean, String fieldName) {
        // obtencion del nombre del metodo get
        String firstChar = fieldName.substring(0, 1).toUpperCase();
        String lastChars = fieldName.substring(1);
        String methodName = "get" + firstChar + lastChars; // el campo se accede norm
        String methodName2 = "is" + firstChar + lastChars;


        // Busqueda del metodo
        Method[] methods = bean.getClass().getMethods();
        Method attrMethod = null;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if ((method.getName().equals(methodName) && method.getParameterTypes().length == 0) ||
                    (method.getName().equals(methodName2) && method.getParameterTypes().length == 0)
                    ) {
                attrMethod = method;
                break;
            }
        }

        return attrMethod;
    }


    public static Class getFieldClass
            (iBaseBean
                     bean, String
                    fieldName) throws IllegalAccessException, InvocationTargetException {
        Method attrMethod = getFieldGetMethod(bean, fieldName);

        if (attrMethod != null) {
            //System.out.println("    - " + getBeanName(bean) + "." + attrMethod.getName() + "() = " + result + ":" + getClassName(result));
            return attrMethod.getReturnType();
        }
        else {
            //throw new RuntimeException("No exits method get" + methodName + " or is" + methodName + " in class " + bean.getClass());
            return null;
        }
    }


    private Object[] getMethodParameters
            (Method
                     method, iBaseBean
                    bean, Element
                    element) throws
            Throwable, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Vector result = new Vector();

        // El primer parametro es la clase de peristencia que estamos tratando
        int argIndex = 0;
        Class[] args = method.getParameterTypes();
        if ((args.length > argIndex && bean != null && args[argIndex].isAssignableFrom(bean.getClass()))) {
            result.add(bean);
            argIndex++;
        }

        List params = element.elements();
        if ((params == null || params.size() == 0) && (args.length > argIndex && args[argIndex].equals(String.class))) {
            result.add(getText(element));
        }
        else {
            Iterator itInstances = params.iterator();

            while (itInstances.hasNext()) {
                Element param = (Element) itInstances.next();

                Object paramValue = parse(bean, param);
                result.add(paramValue);
            }
        }

        return result.toArray();
    }

    private Object callManagerMethod
            (iBaseBean
                     bean, Element
                    item) throws Throwable, IllegalAccessException, InvocationTargetException, ParseException, InstantiationException {

        String managerClass = item.attributeValue("mngClass");
        String methodName = item.attributeValue("mngMethod");

        // obtencion del metodo de la clase del Manager que quiero llamar
        Class mngClass = Class.forName(managerClass);

        // Busqueda del metodo
        Method[] methods = mngClass.getMethods();
        Method attrMethod = null;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().equals(methodName)) {
                attrMethod = method;
                break;
            }
        }

        if (attrMethod != null) {
            Object[] params = getMethodParameters(attrMethod, bean, item);
            //System.out.println("attrMethod = " + attrMethod);
            //System.out.println("params.length = " + params.length);
            Object result = attrMethod.invoke(null, params);

            String dumpText = "  - manager " + attrMethod.getName() + "(";
            for (int i = 0; params != null && i < params.length; i++) {
                Object param = params[i];
                dumpText += param + ",";
            }
            dumpText += ") = " + result;
            dump(dumpText);

            if (result == null) {
                throw new RuntimeException("El manager no ha retornado valor:" + getItemXML(item));
            }

            return result;
        }
        else {
            throw new RuntimeException("Error invoking " + methodName + " class " + mngClass);
        }
    }

    public String getXmlFile
            () {
        return xmlFile;
    }

    public void setXmlFile
            (String
                     xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void process(File file) throws Throwable, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, ParseException {
        if (!file.exists()) {
            dump("No existe el file:" + file);
        }
        else if (file.isDirectory()) {
            if (".svn".equals(file.getName())) {
                return;
            }

            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                process(file1);
            }
        }
        else if (!file.getName().endsWith(".xml") || file.getName().endsWith("FAIL.xml")) {
            dump("No es un XML:" + file);
        }
        else {
            dump("Procesando " + file);
            setXmlFile(file.getAbsolutePath());
            parse();
        }
    }

    public static void main(String[] args) throws Throwable {

        XML2Model xml2model = new XML2Model();

        xml2model.openSession();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println("****************");
            System.out.println("Procesando:" + arg);
            System.out.println("****************");
            File file = new File(arg);
            xml2model.process(file);
            //HibernateUtil.closeSession();
            System.out.println("****************");
            System.out.println("Fin:" + arg);
            System.out.println("****************");
        }
    }


}
