package com.kaisery.fs.repository;

import com.kaisery.fs.entity.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {

    @Query("{ 'name' : ?0 }")
    public Resource findByName(String name);

    @Query("{ 'maxVersion' : ?0 }")
    public List<Resource> findByMaxVersion(Integer version);
}
