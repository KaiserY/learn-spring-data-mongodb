package com.kaisery.fs.entity;

import java.util.List;

public class Folder extends Resource {

    private List<Resource> child;

    public Folder() {
        this.setType("folder");
    }

    public List<Resource> getChild() {
        return child;
    }

    public void setChild(List<Resource> child) {
        this.child = child;
    }
}
