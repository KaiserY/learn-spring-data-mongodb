package com.kaisery.fs.entity;

public class File extends Resource {

    private int version;

    public File() {
        this.setType("file");
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
