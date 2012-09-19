package com.befasoft.common.beans;

import java.util.List;

public class UTL_TREE_MENU_BASE {
    protected String text;
    protected List<UTL_TREE_MENU_BASE> children;

    public List<UTL_TREE_MENU_BASE> getChildren() {
        return children;
    }

    public void setChildren(List<UTL_TREE_MENU_BASE> children) {
        this.children = children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
