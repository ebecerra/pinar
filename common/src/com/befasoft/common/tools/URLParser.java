package com.befasoft.common.tools;


import java.util.HashMap;

public class URLParser {

    private HashMap<String, String> params;

    public URLParser(String params) {
        String campos[] = params.split("&");
        this.params = new HashMap<String, String>();
        for(int i=0;i<campos.length;i++){
            String parameter[] = campos[i].split("=");
            this.params.put(parameter[0], parameter[1]);
        }
    }

    public String getString(String name) {
         return params.get(name);
    }

    public Long getLong(String name) {
        return Long.parseLong(params.get(name));
    }

    public Integer getInteger(String name) {
        return Integer.parseInt(params.get(name));
    }

    public Boolean getBoolean(String name) {
        return Converter.getBoolean(params.get(name));
    }
}
