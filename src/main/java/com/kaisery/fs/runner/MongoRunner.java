package com.kaisery.fs.runner;

import com.kaisery.fs.entity.File;
import com.kaisery.fs.entity.Folder;
import com.kaisery.fs.entity.Resource;
import com.kaisery.fs.entity.User;
import com.kaisery.fs.repository.ResourceRepository;
import com.kaisery.fs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Component
public class MongoRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public void run(String... strings) throws Exception {
        userRepository.deleteAll();
        resourceRepository.deleteAll();


        Folder rootFolder = new Folder();
        rootFolder.setName("root folder");
        rootFolder.setParent(null);
        rootFolder.setPath(new ArrayList<Folder>());
        rootFolder.setLastModifiedTime(LocalDateTime.now());
        rootFolder.setChild(new ArrayList<Resource>());

        Folder user1RootFolder = new Folder();
        user1RootFolder.setName("user 1 root folder");
        user1RootFolder.setParent(rootFolder);
        user1RootFolder.setPath(Collections.singletonList(rootFolder));
        user1RootFolder.setLastModifiedTime(LocalDateTime.now());
        user1RootFolder.setChild(new ArrayList<Resource>());

        rootFolder.getChild().add(user1RootFolder);

        for (int i = 1; i <= 5; i++) {
            Folder folder = new Folder();
            folder.setName("user 1 folder" + i);
            folder.setParent(user1RootFolder);
            folder.setPath(Arrays.asList(rootFolder, user1RootFolder));
            folder.setLastModifiedTime(LocalDateTime.now());
            folder.setChild(new ArrayList<Resource>());

            for (int j = 1; j <= 5; j++) {
                File file = new File();
                file.setName("user 1 folder" + i + " file " + j);
                file.setParent(folder);
                file.setPath(Arrays.asList(rootFolder, user1RootFolder, folder));
                file.setLastModifiedTime(LocalDateTime.now());
                file.setVersion(1);

                resourceRepository.save(file);

                folder.getChild().add(file);
            }

            resourceRepository.save(folder);

            user1RootFolder.getChild().add(folder);
        }

        User user = new User();
        user.setUserName("user 1");
        user.setPassword("12345678");
        user.setRootFolder(user1RootFolder);

        resourceRepository.save(rootFolder);
        resourceRepository.save(user1RootFolder);
        userRepository.save(user);
    }
}
