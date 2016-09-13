package com.kaisery.fs.entity;

import java.util.List;

public class Folder extends Resource {

    private List<ResourceBrief> child;

    private FolderExtension extension;

    public Folder() {
        this.setType("folder");
    }

    public List<ResourceBrief> getChild() {
        return child;
    }

    public void setChild(List<ResourceBrief> child) {
        this.child = child;
    }

    public FolderExtension getExtension() {
        return extension;
    }

    public void setExtension(FolderExtension extension) {
        this.extension = extension;
    }
}
