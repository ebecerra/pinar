package com.befasoft.common.actions;

import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;
import com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import com.opensymphony.xwork2.Action;
import org.apache.commons.beanutils.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.svenson.JSONParser;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public abstract class BaseManagerAction extends BaseAction implements NeedAuthentificate {

    protected BodyResult bodyResult = new BodyResult();

    protected String elementsToDelete;
    protected String dataToSave;
    protected int limit = -1;
    protected int start = -1;
    protected String sort = null;
    protected String dir = null;
    protected EntityBean element;

    /**
     * Construye el filtro de las llaves
     *
     * @param bean    Bean con el tipo de datos
     * @param dataRow Datos del bean
     * @return Filtro con las llaves
     * @throws Exception Error
     */
    private Map buildKeyFilter(EntityBean bean, Map dataRow) throws Exception {
        String[] keynames = bean.getKeyFields();
        BeanUtils.populate(bean, dataRow);

        Map filters = new HashMap();
        for (String key : keynames) {
            Object val = PropertyUtils.getProperty(bean, key);
            if (val != null) {
                filters.put(key, val);
            }
        }
        return filters;
    }

    /**
     * Borra los elementos seleccionados
     *
     * @param bean     Bean a borrar
     * @param dataList Datos a borrar
     */
    protected void deleteBeanElements(EntityBean bean, List<HashMap> dataList) {

        Transaction tx = null;
        try {
            List<EntityBean> beansDeleted = new ArrayList();
            // iteramos por los elementos a borrar
            for (Map dataRow : dataList) {
                tx = currentSession.beginTransaction();
                bean = (EntityBean) BeanManager.findFirstByFilter(bean.getClass().getName(), buildKeyFilter(bean, dataRow));
                if (bean != null) {
                    // borramos el bean
                    String error = canDelete(dataRow, bean);
                    if (error == null) {
                        BeanManager.delete(currentSession, bean);
                        beansDeleted.add(bean);
                        currentSession.flush();
                        tx.commit();
                    } else {
                        tx.rollback();
                        bodyResult.addError(bean, error);
                    }
                }
            }

            for (EntityBean beanDel : beansDeleted) {
                wasDeleted(beanDel);
            }
        }
        catch (Throwable e) {
            log.error(e);
            // Si ocurrio un error se hace rollback a la transaccion.
            try {
                tx.rollback();
            }
            catch (Throwable e1) {
                log.error(e1);
            }

            deleteError(e);
        }
    }

    protected void deleteError(Throwable e) {
        error(e);
        DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_DELETE", e);
        writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_ERR_DELETE, APPBS_USUARIOS_LOG.LEVEL_ERROR, e.getCause() != null ? e.getCause().getMessage() : e.getLocalizedMessage());
    }

    protected void updateError(Throwable e) {
        error(e);
        DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_UPDATE", e);
        writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_ERR_UPDATE, APPBS_USUARIOS_LOG.LEVEL_ERROR, e.getCause() != null ? e.getCause().getMessage() : e.getLocalizedMessage());
    }

    protected void error(Throwable error) {
        String msgErr =  error.getCause() != null ? error.getCause().getMessage() : error.getLocalizedMessage();
        // TODO: Hay que verificar las diferente casuistica de errores
        if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
            if (msgErr != null && msgErr.contains("ORA-02292"))
                msgErr = getText("appbs.dberror.foreingkey");
        } else {
            if (msgErr != null && msgErr.contains("Cannot delete or update a parent row"))
                msgErr = getText("appbs.dberror.foreingkey");
        }
        // Se marca como error
        bodyResult.setSuccess(false);
        bodyResult.setMessage(msgErr);
    }

    /**
     * Actualiza los elementos
     *
     * @param bean     Bean a actualizar
     * @param dataList Lista de objectos a actualizar
     */
    protected void updateBeanElements(EntityBean bean, List<HashMap> dataList) {
        Session currentSession = HibernateUtil.currentSession();
        List<EntityBean> beansModified = new ArrayList();
        Transaction tx = null;
        try {
            String[] keynames = bean.getKeyFields();

            // iteramos por los elementos a Actualizar
            for (Map dataRow : dataList) {
                try {
                    tx = currentSession.beginTransaction();
                    Map filters = buildKeyFilter(bean, dataRow);
                    EntityBean beanToModify = null;
                    boolean insert = false;
                    if (filters.size() == keynames.length) {
                        beanToModify = (EntityBean) BeanManager.findFirstByFilter(currentSession, bean.getClass().getName(), filters);
                    }

                    if (beanToModify == null) {
                        // es una nueva instancia
                        beanToModify = bean.getClass().newInstance();
                        insert = true;
                    }

                    // Se pasan todos los parametros del map al objeto
                    BeanUtils.populate(beanToModify, dataRow);

                    // Se verifica si existen Objetos (Hibernate) que es necesario guardar
                    PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(beanToModify);
                    for (PropertyDescriptor property : properties) {
                        Method getter = property.getReadMethod();
                        if (getter != null && !getter.getName().equals("getClass")) {
                            Object child = getter.invoke(beanToModify);
                            if (child != null && child instanceof EntityBean && ((EntityBean) child).isSave()) {
                                BeanManager.save(currentSession, child);
                                currentSession.flush();
                            }
                        }
                    }

                    // actualizamos el bean
                    if (insert) {
                        String error = canInsert(dataRow, beanToModify);
                        if (error == null) {
                            BeanManager.save(currentSession, beanToModify);
                            beansModified.add(beanToModify);
                            currentSession.flush();
                            tx.commit();
                        } else {
                            tx.rollback();
                            bodyResult.addError(beanToModify, error);
                        }
                    }
                    else if (!insert) {
                        String error = canModify(dataRow, beanToModify);
                        if (error == null) {
                            BeanManager.save(currentSession, beanToModify);
                            beansModified.add(beanToModify);
                            currentSession.flush();
                            tx.commit();
                        } else {
                            tx.rollback();
                            bodyResult.addError(beanToModify, error);
                        }
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                    log.error(e);

                    updateError(e);
                    // Si se marca qeu se continue la insercion aunque haya errores entonces se continua, sino se para.
                    if (stopSaveOnFirstError()) {
                        tx.rollback();
                        return;
                    }
                }
            }

            for (EntityBean beanMofied : beansModified) {
                currentSession.refresh(beanMofied);
                // Tarea posteriores a la actualizacion
                wasModified(beanMofied);
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            log.error(e);
            // Si ocurrio un error se hace rollback a la transaccion.
            try {
                tx.rollback();
            }
            catch (Throwable e1) {
                log.error(e1);
            }

            updateError(e);
        }

        bodyResult.setElements(beansModified);
    }


    /**
     * Metodo usado para validar los campos antes de guardarlos en BD y poder retornar errores asociados a cada campo.
     *
     * @param bean     Bean a actualizar
     * @param dataList Lista de objectos a actualizar
     * @return <code>true</code> Si es valido el bean
     */
    protected boolean validateSave(EntityBean bean, List<HashMap> dataList) {
        // Se valida el tipo de los campos
        // iteramos por los elementos a Actualizar
        boolean result = true;
        for (HashMap dataRow : dataList) {
            result = validateBeanSave(bean, dataRow);

            if (!result && stopSaveValidationOnFirstError(bean, dataRow)) {
                return false;
            }
            else {
                continue;
            }
        }

        return result;
    }


    /**
     * Valida cada campo del bean en este metodo solo se valida los valores de cada property
     *
     * @param bean     Bean a actualizar
     * @param dataRow  Hash con los datos
     * @return <code>true</code> Si es valido el bean
     */
    protected boolean validateBeanSave(EntityBean bean, HashMap dataRow) {
        String[] fields = bean.getAllFields();
        for (String field : fields) {
            if (!validateBeanFieldSave(bean, dataRow, field)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Valida el valor de un campo-
     *
     * @param bean Bean a actualizar
     * @param row  Hash con los datos
     * @param field Campo a validar
     * @return <code>true</code> Si es valido el bean
     */
    protected boolean validateBeanFieldSave(EntityBean bean, HashMap row, String field) {
        try {
            Class type = PropertyUtils.getPropertyType(bean, field);
            ConvertUtils.convert(row.get(field), type);
        }
        catch (Throwable e) {
            bodyResult.setSuccess(false);
            bodyResult.setMessage(getText("appbs.validate.err.field", new String[] {field, e.getMessage()}));
            log.error("El valor de campo " + field + " no es correcto:" + e.getMessage());
            DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_VALIDATE", "El valor de campo " + field + " no es correcto:" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Metodo que llama al Execute del padre
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception Error
     */

    public String executeInit() throws Exception {
        setLogger();
        return super.execute();
    }

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception Error
     */
    public String execute() throws Exception {
        setLogger();
        super.execute();

        if (Converter.isEmpty(action) && Converter.isEmpty(dataToSave) && Converter.isEmpty(elementsToDelete)) {
            return executeList();
        }
        else if (!Converter.isEmpty(dataToSave) || !Converter.isEmpty(elementsToDelete)) {
            return executeSave();
        }
        else {
            if (!Converter.isEmpty(action)) {
                // Busco si esta clase tiene un metodo que se llame como la accion, si es asi lo ejecuto.
                Method mtdAction = null;

                try {
                    mtdAction = getClass().getMethod(action, null);
                }
                catch (Throwable e) {
                    if (!(e instanceof NoSuchMethodException)) {
                        e.printStackTrace();
                        error(e);
                    }
                }

                // Si el metodo retorna unString se asume que coloca el resultado. Si retorna una coleccion entonces
                // se asume que esa es la respuesta.
                if (mtdAction != null) {
                    if (String.class.isAssignableFrom(mtdAction.getReturnType())) {
                        return (String) mtdAction.invoke(this, null);
                    }
                    else if (Collection.class.isAssignableFrom(mtdAction.getReturnType())) {
                        Collection collection = (Collection) mtdAction.invoke(this, null);
                        bodyResult.setElements(new ArrayList(collection));
                        bodyResult.setTotalCount(collection.size());
                    }
                }
            }
        }

        return Action.SUCCESS;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    protected Map getFilter() {
        return null;
    }

    /**
     * Retorna el listado de elementos siguiente el filtro y el paginado
     *
     * @return Lista de elementos
     * @throws Exception Error en la llamada a los bean
     */
    protected String executeList() throws Exception {
        Class manager = getManagerClass();
        if (manager != null) {
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_LIST, APPBS_USUARIOS_LOG.LEVEL_COMMON_INFO, getBeanClass().getName());
            Map filter = getFilter();
            List allElements;
            int totalCount;

            try {

                if ((filter == null || filter.size() == 0) && limit <= 0) {
                    // listAll
                    if (sort == null) {
                        allElements = listAll(manager);
                    }
                    else {
                        allElements = listAll(manager, sort, dir);
                    }
                }
                else {
                    // findByFilter
                    allElements = findByFilter(getBeanClass().getName(), manager, filter, start, limit, sort, dir);
                }

                // Si hay limit se asume que hay paginado entonces hay que contar los elementos.
                totalCount = allElements.size();
                if (limit > 0) {
                    // findCountByFilter
                    totalCount = findCountByFilter(manager, filter);
                }
            }
            catch (Exception e) {
                log.error(e);
                DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_LIST", e);
                e.printStackTrace();
                writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_ERR_LIST, APPBS_USUARIOS_LOG.LEVEL_ERROR, e.getMessage());
                throw e;
            }

            setReturn(allElements, totalCount, start, limit);
        }

        return Action.SUCCESS;
    }

    /**
     * Prepara los resultado
     * @param allElements Lista de elementos
     * @param totalCount Cantidad total de elementos (Paginado)
     * @param start Posicion del primer elemento (Paginado)
     * @param limit Cantidad de elementos (Paginado)
     */
    protected void setReturn(List allElements, int totalCount, int start, int limit) {
        bodyResult.setElements(allElements);
        bodyResult.setTotalCount(totalCount);
        if (limit < 0) {
            bodyResult.setCurrentPage(1);
        }
        else {
            bodyResult.setCurrentPage(start / limit + 1);
        }
    }

    protected void setReturn(List allElements) {
        if (allElements == null) {
            bodyResult.setElements(null);
            bodyResult.setTotalCount(0);
            bodyResult.setCurrentPage(1);
        } else
            setReturn(allElements, allElements.size());
    }

    protected void setReturn(List allElements, int totalCount) {
        setReturn(allElements, totalCount, -1, -1);
    }

    protected void setReturn(Object element) {
        List allElements = new ArrayList(1);
        allElements.add(element);

        setReturn(allElements, 1, -1, -1);
    }

    /**
     * Ejecuta el metodo findCountByFilter del manager asociado al bean
     *
     * @param manager Clase con el manager de datos
     * @param filter  Filtro a aplicar
     * @return Cantidad de elementos totales
     * @throws Exception Error
     */
    private int findCountByFilter(Class manager, Map filter) throws Exception {
        String methodName = "findCountByFilter";
        Class[] paramTypes = {
                Map.class
        };
        Object[] params = {
                filter,
        };

        Method method = manager.getMethod(methodName, paramTypes);
        Object result = method.invoke(null, params);

        return (Integer) result;
    }

    /**
     * Ejecuta el metodo findByFilter del manager asociado al bean
     *
     * @param beanName Nombre del bean de datos
     * @param manager  Clase con el manager de datos
     * @param filter   Filtro a aplicar
     * @param start    Registro inicial
     * @param limit    Cantidad de registros
     * @param sort     Campo por el que se ordena
     * @param dir      Direccion de ordenacion
     * @return Lista de elementos
     * @throws Exception Error
     */
    private List findByFilter(String beanName, Class manager, Map filter, int start, int limit, String sort, String dir) throws Exception {
        String methodName = "findByFilter";
        Class[] paramTypes = {
                String.class,
                Map.class,
                Integer.TYPE,
                Integer.TYPE,
                String.class,
                String.class
        };
        Object[] params = {
                beanName,
                filter,
                start,
                limit,
                sort,
                dir
        };

        Method method = manager.getMethod(methodName, paramTypes);
        Object result = method.invoke(null, params);

        return (List) result;
    }

    /**
     * Ejecuta el metodo listAll del manager asociado al bean
     *
     * @param manager Clase con el manager de datos
     * @param sort    Campo por el que se ordena
     * @param dir     Direccion de ordenacion
     * @return Lista de los elementos
     * @throws Exception Error
     */
    private List listAll(Class manager, String sort, String dir) throws Exception {
        String methodName = "listAll";
        Class[] paramTypes = {
                String.class,
                String.class
        };
        Object[] params = {
                sort,
                dir
        };

        Method method = manager.getMethod(methodName, paramTypes);
        Object result = method.invoke(null, params);

        return (List) result;
    }

    /**
     * Ejecuta el metodo listAll del manager asociado al bean
     *
     * @param manager Clase con el manager de datos
     * @return Lista de los elementos
     * @throws Exception Error
     */
    private List listAll(Class manager) throws Exception {
        String methodName = "listAll";
        Class[] paramTypes = {
        };
        Object[] params = {
        };

        Method method = manager.getMethod(methodName, paramTypes);
        Object result = method.invoke(null, params);

        return (List) result;
    }


    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    public String executeSave() throws Exception {
        if (!Converter.isEmpty(dataToSave) && !"[]".equals(dataToSave) && !"null".equals(dataToSave)) {
            executeUpdate();
        }

        if (!Converter.isEmpty(elementsToDelete) && !"[]".equals(elementsToDelete) && !"null".equals(elementsToDelete)) {
            executeDelete();
        }

        return Action.SUCCESS;
    }


    /**
     * Construye un bean vacio.
     *
     * @return Bean
     * @throws Exception Error
     */
    protected EntityBean getEmptyBean() throws Exception {
        try {
            return (EntityBean) getBeanClass().newInstance();
        }
        catch (Exception e) {
            DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_EMPTYBEAN", e);
            log.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retorna la clase asociada al bean..
     *
     * @return Bean de datos
     */
    protected abstract Class getBeanClass();

    /**
     * Retorna la clase asociada al manager del bean..
     *
     * @return Manager del bean de datos
     */
    protected abstract Class getManagerClass();

    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    protected String executeUpdate() throws Exception {
        writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_UPDATE, APPBS_USUARIOS_LOG.LEVEL_COMMON_INFO, getBeanClass().getName());
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, this.dataToSave);
        EntityBean bean = getEmptyBean();

        // Se valida los tipos de loos campos.
        if (validateSave(bean, dataList)) {
            updateBeanElements(bean, dataList);
        }
        else {
            bodyResult.setSuccess(false);
        }
        return Action.SUCCESS;
    }


    /**
     * Ejecuta añadir una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    protected String executeInsert() throws Exception {
        return Action.SUCCESS;
    }


    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    protected String executeDelete() throws Exception {
        writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_DELETE, APPBS_USUARIOS_LOG.LEVEL_COMMON_INFO, getBeanClass().getName());
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, elementsToDelete);
        deleteBeanElements(getEmptyBean(), dataList);
        return Action.SUCCESS;
    }

    /**
     * Retorna si es necesario que el usuario este autentificado para realizar esta accion.
     *
     * @return <code>true</code>  si es necesario estar autentificado
     */
    public boolean needAuthetificated() {
        return true;
    }

    /**
     * Indica si se deben seguir salvando los elementos aunque se encuentre un error.
     *
     * @return Si para al encontrar el primer error
     */
    protected boolean stopSaveOnFirstError() {
        return true;
    }

    /**
     * Indica si se deben seguir validando los elementos aunque se encuentre un error.
     *
     * @param bean Bean que se va a borrar
     * @param row Map con los valores recibidos
     * @return Si para al encontrar el primer error
     */
    protected boolean stopSaveValidationOnFirstError(EntityBean bean, HashMap row) {
        return false;
    }

    /**
     * Indica si se deben seguir borrando los elementos aunque se encuentre un error.
     *
     * @return Si para al encontrar el primer error
     */
    protected boolean stopDeleteOnFirstError() {
        return true;
    }

    /**
     * Indica si se puede borrar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a borrar
     * @return <code>true</code> Si se puede borrar este registro
     * @throws Exception Error
     */
    protected String canDelete(Map values, EntityBean bean) throws Exception {
        return null;
    }

    /**
     * Indica si se puede modificar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a modificar
     * @return <code>true</code> Si se puede modificar este registro
     * @throws Exception Error
     */
    protected String canModify(Map values, EntityBean bean) throws Exception {
        return null;
    }

    /**
     * Indica si se puede insertar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a insertar
     * @return <code>true</code> Si se puede insertar este registro
     * @throws Exception Error
     */
    protected String canInsert(Map values, EntityBean bean) throws Exception {
        return null;
    }

    /**
     * Se llama cuando el registro ha sido modificado
     *
     * @param bean Bean modificado
     * @throws Exception Error
     */
    protected void wasModified(EntityBean bean) throws Exception {
    }

    /**
     * Se llama cuando el registro ha sido borrado
     *
     * @param bean Bean borrado
     * @throws Exception Error
     */
    protected void wasDeleted(EntityBean bean) throws Exception {
    }

    /*
     * Metodos Get/Set
     */
    public BodyResult getBodyResult() {
        return bodyResult;
    }

    public void setBodyResult(BodyResult bodyResult) {
        this.bodyResult = bodyResult;
    }

    public String getElementsToDelete() {
        return elementsToDelete;
    }

    public void setElementsToDelete(String elementsToDelete) {
        this.elementsToDelete = elementsToDelete;
    }

    public String getDataToSave() {
        return dataToSave;
    }

    public void setDataToSave(String dataToSave) {
        this.dataToSave = dataToSave;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public EntityBean getElement() {
        return element;
    }

    public void setElement(EntityBean element) {
        this.element = element;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


}