package com.befasoft.common.beans;

import com.befasoft.common.model.EntityBean;

public class ErrorResult {
    EntityBean item;
    String message;

    public ErrorResult(EntityBean item, String message) {
        this.item = item;
        this.message = message;
    }

    public EntityBean getItem() {
        return item;
    }

    public void setItem(EntityBean item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
