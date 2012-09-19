package com.befasoft.common.beans;

import java.util.Date;

public class UTL_MINMAX {
    long l_min, l_max;
    double f_min, f_max;
    Date d_min, d_max;

    public UTL_MINMAX(long l_min, long l_max) {
        this.l_min = l_min;
        this.l_max = l_max;
    }

    public UTL_MINMAX(double f_min, double f_max) {
        this.f_min = f_min;
        this.f_max = f_max;
    }

    public UTL_MINMAX(Date d_min, Date d_max) {
        this.d_min = d_min;
        this.d_max = d_max;
    }

    public UTL_MINMAX() {
    }

    /*

    * Metodos Get/Set
    */

    public long getL_min() {
        return l_min;
    }

    public void setL_min(long l_min) {
        this.l_min = l_min;
    }

    public long getL_max() {
        return l_max;
    }

    public void setL_max(long l_max) {
        this.l_max = l_max;
    }

    public double getF_min() {
        return f_min;
    }

    public void setF_min(double f_min) {
        this.f_min = f_min;
    }

    public double getF_max() {
        return f_max;
    }

    public void setF_max(double f_max) {
        this.f_max = f_max;
    }

    public Date getD_min() {
        return d_min;
    }

    public void setD_min(Date d_min) {
        this.d_min = d_min;
    }

    public Date getD_max() {
        return d_max;
    }

    public void setD_max(Date d_max) {
        this.d_max = d_max;
    }
}
