package com.befasoft.common.beans;

public class UTL_TREE_MENU extends UTL_TREE_MENU_BASE {
    String cls = "folder";

    public UTL_TREE_MENU(String text) {
        this.text = text;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
