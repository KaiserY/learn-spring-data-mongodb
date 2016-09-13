package com.kaisery.fs.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Resource {

    @Id
    private String id;

    private String identity;

    private String type;

    private String name;

    private UserBrief owner;

    private LocalDateTime lastModifiedTime;

    private FolderBrief parent;

    private List<FolderBrief> path;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBrief getOwner() {
        return owner;
    }

    public void setOwner(UserBrief owner) {
        this.owner = owner;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public FolderBrief getParent() {
        return parent;
    }

    public void setParent(FolderBrief parent) {
        this.parent = parent;
    }

    public List<FolderBrief> getPath() {
        return path;
    }

    public void setPath(List<FolderBrief> path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
