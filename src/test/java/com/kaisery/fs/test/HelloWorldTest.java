package com.kaisery.fs.test;

import com.kaisery.fs.entity.*;
import com.kaisery.fs.repository.ResourceRepository;
import com.kaisery.fs.repository.UserRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HelloWorldTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    public void helloWorldTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("Hello World!")));
    }

    @Test
    public void insertTest() throws Exception {

        User user = new User();
        user.setUserName("user");
        user.setPassword("12345678");
        user.setSpace(315641556L);
        user.setCreatedTime(LocalDateTime.now());

        userRepository.save(user);

        user = userRepository.findByUserName("user");

        UserBrief userBrief = new UserBrief();
        userBrief.setUserName(user.getUserName());
        userBrief.setToken(user.getToken());
        userBrief.setId(user.getId());

        Folder rootFolder = new Folder();
        rootFolder.setName("user root folder");
        rootFolder.setLastModifiedTime(LocalDateTime.now());
        rootFolder.setOwner(userBrief);
        rootFolder.setChild(new ArrayList<ResourceBrief>());

        resourceRepository.save(rootFolder);

        rootFolder = (Folder) resourceRepository.findByName("user root folder");

        FolderBrief rootFolderBrief = new FolderBrief();
        rootFolderBrief.setId(rootFolder.getId());
        rootFolderBrief.setName(rootFolder.getName());

        user.setRootFolder(rootFolderBrief);

        for (int i = 1; i <= 100; i++) {
            Folder folder = new Folder();
            folder.setName("user folder " + i);
            folder.setLastModifiedTime(LocalDateTime.now());
            folder.setOwner(userBrief);
            folder.setChild(new ArrayList<ResourceBrief>());
            folder.setParent(rootFolderBrief);
            folder.setPath(Collections.singletonList(rootFolderBrief));

            resourceRepository.save(folder);

            folder = (Folder) resourceRepository.findByName(folder.getName());

            FolderBrief folderBrief = new FolderBrief();
            folderBrief.setId(folder.getId());
            folderBrief.setName(folder.getName());

            rootFolder.getChild().add(folderBrief);

            for (int j = 1; j <= 100; j++) {
                File file = new File();
                file.setName("use folder " + i + " file " + j);
                file.setOwner(userBrief);
                file.setLastModifiedTime(LocalDateTime.now());
                file.setParent(folderBrief);
                file.setPath(Arrays.asList(rootFolderBrief, folderBrief));

                resourceRepository.save(file);

                file = (File) resourceRepository.findByName(file.getName());

                FileBrief fileBrief = new FileBrief();
                fileBrief.setId(file.getId());
                fileBrief.setName(file.getName());

                folder.getChild().add(fileBrief);
            }

            resourceRepository.save(folder);
        }

        resourceRepository.save(rootFolder);
        userRepository.save(user);
    }
}
