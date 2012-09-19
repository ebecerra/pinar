package com.befasoft.common.beans;

public class ActionStateElement {
    public static final int STATE_CREATE        = 0;
    public static final int STATE_RUNNING       = 1;
    public static final int STATE_CANCELED      = 2;
    public static final int STATE_FINISH_OK     = 3;
    public static final int STATE_FINISH_ERRR   = 4;

    protected String elementId;               // Identificar unico del elemento
    protected Long total;                     // Cantidad total de Item a procesar
    protected Long current;                   // Numero de items procesados
    protected boolean cancel;                 // Indica si se ha cancelado por parte del usuario
    protected int status;                     // Estado de la ejecucion

    public ActionStateElement() {
        total = 0L;
        current = 0L;
        cancel = false;
        status = STATE_CREATE;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
