package com.befasoft.common.beans;

import java.io.File;
import java.util.Date;

public class UTL_FILE {
    String name, path;
    Date modified;
    boolean file;
    long size;

    public UTL_FILE(File f) {
        this.name = f.getName();
        this.path = f.getPath();
        this.modified = new Date(f.lastModified());
        this.file = f.isFile();
        this.size = f.length() / 1024;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
