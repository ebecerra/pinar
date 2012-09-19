package com.befasoft.common.business;

import org.apache.commons.beanutils.Converter;

public class TimeConverter implements Converter {

    public Object convert(Class aClass, Object o) {
        String format = "HH:mm";
        String value = (String) o;
        if (value.indexOf("M") > 0)
            format = "hh:mm a";

        return com.befasoft.common.tools.Converter.getTime(value, format);
    }
}
