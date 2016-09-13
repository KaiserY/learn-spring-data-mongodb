package com.kaisery.fs.repository;

import com.kaisery.fs.entity.Folder;
import com.kaisery.fs.entity.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceRepository extends MongoRepository<Resource, String> {

    public Resource findByName(String name);
}
