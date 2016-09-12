package com.kaisery.fs.entity;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Resource {

    @Id
    private ObjectId id = new ObjectId();

    private String type;

    private String name;

    private LocalDateTime lastModifiedTime;

    @DBRef
    private Folder parent;

    @DBRef
    private List<Folder> path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public List<Folder> getPath() {
        return path;
    }

    public void setPath(List<Folder> path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
