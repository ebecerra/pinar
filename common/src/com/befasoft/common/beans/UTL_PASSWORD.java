package com.befasoft.common.beans;

public class UTL_PASSWORD {
    int check, max, min, store, valid;
    boolean user;
    String login, err_min, err_max, err_check2, err_check3, err_check4, err_confirm;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public String getErr_min() {
        return err_min;
    }

    public void setErr_min(String err_min) {
        this.err_min = err_min;
    }

    public String getErr_max() {
        return err_max;
    }

    public void setErr_max(String err_max) {
        this.err_max = err_max;
    }

    public String getErr_check2() {
        return err_check2;
    }

    public void setErr_check2(String err_check2) {
        this.err_check2 = err_check2;
    }

    public String getErr_check3() {
        return err_check3;
    }

    public void setErr_check3(String err_check3) {
        this.err_check3 = err_check3;
    }

    public String getErr_check4() {
        return err_check4;
    }

    public void setErr_check4(String err_check4) {
        this.err_check4 = err_check4;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getErr_confirm() {
        return err_confirm;
    }

    public void setErr_confirm(String err_confirm) {
        this.err_confirm = err_confirm;
    }
}
