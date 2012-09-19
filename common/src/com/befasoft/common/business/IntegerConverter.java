package com.befasoft.common.business;

import org.apache.commons.beanutils.Converter;

import java.util.Date;

public class IntegerConverter implements Converter {

    public Object convert(Class aClass, Object o) {
        if (o == null || o.toString().equals(""))
            return null;
        return com.befasoft.common.tools.Converter.getInt(o.toString());
    }
}
