package com.kaisery.fs.runner;

import com.kaisery.fs.entity.User;
import com.kaisery.fs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... strings) throws Exception {
        userRepository.deleteAll();

        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword("12345678");

            userRepository.save(user);
        }
    }
}
