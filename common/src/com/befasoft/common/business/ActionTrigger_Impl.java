package com.befasoft.common.business;

import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import org.hibernate.Session;

import java.util.List;

public class ActionTrigger_Impl implements ActionTrigger {

    public boolean beforeUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        return true;
    }

    public void afterUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
    }

    public boolean beforeInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        return true;
    }

    public void afterInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
    }

    public boolean beforeDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        return true;
    }

    public void afterDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {
    }
}
