package com.kaisery.fs.service;

import com.kaisery.fs.entity.File;
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
        File file = mongoTemplate.findAndModify(query, update, File.class);
        return new AsyncResult<Void>(null);
    }
}
