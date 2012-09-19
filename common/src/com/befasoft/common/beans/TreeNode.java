package com.befasoft.common.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: javier
 * Date: 31-mar-2011
 * Time: 17:49:51
 * To change this template use File | Settings | File Templates.
 */
public class TreeNode {

    private String text;
    private String id;
    private List children= new ArrayList();
    private String origen;
    private boolean leaf= false;
    private Boolean checked =null;

    private String msgTexto;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getMsgTexto() {
        return msgTexto;
    }

    public void setMsgTexto(String msgTexto) {
        this.msgTexto = msgTexto;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
