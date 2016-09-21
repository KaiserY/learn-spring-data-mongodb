package com.kaisery.fs.service;

import com.kaisery.fs.entity.File;
import com.kaisery.fs.entity.Resource;
import com.kaisery.fs.repository.ResourceRepository;
import com.kaisery.fs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class MongoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ResourceRepository resourceRepository;

    @Async
    public Future<String> insertDocument(Object document, String collection, String text) {
        mongoTemplate.insert(document, collection);
        return new AsyncResult<String>(text);
    }

    @Async
    public Future<Void> doQuery(String text) {
        Query query = new Query(Criteria.where("name").is(text));
        Update update = new Update().set("maxVersion", 9);
        mongoTemplate.findAndModify(query, update, Resource.class);
        return new AsyncResult<Void>(null);
    }

    @Async
    public Future<Void> doFind(String text) {
        Query query = new Query(Criteria.where("name").is(text));
        List<Resource> resources = mongoTemplate.find(query, Resource.class);
        return new AsyncResult<Void>(null);
    }
}
