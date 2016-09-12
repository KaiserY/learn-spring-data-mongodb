package com.kaisery.fs.repository;

import com.kaisery.fs.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUserName(String userName);
}
