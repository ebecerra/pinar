package com.befasoft.common.beans;

public class UTL_TREE_SUBMENU extends UTL_TREE_MENU_BASE {
    boolean leaf = true;
    boolean checked = false;

    public UTL_TREE_SUBMENU(String text) {
        this.text = text;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
