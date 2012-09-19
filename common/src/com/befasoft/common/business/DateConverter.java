package com.befasoft.common.business;

import org.apache.commons.beanutils.Converter;

import java.util.Date;

public class DateConverter implements Converter {

    public Object convert(Class aClass, Object o) {
        Date d = com.befasoft.common.tools.Converter.getDate((String) o, "yyyy-MM-dd'T'HH:mm:ss");
        if (d == null)
            d = com.befasoft.common.tools.Converter.getDate((String) o, "dd/MM/yyyy HH:mm:ss");
        return d;
    }
}
