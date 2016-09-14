package com.kaisery.fs.test;

import com.kaisery.fs.entity.*;
import com.kaisery.fs.repository.ResourceRepository;
import com.kaisery.fs.repository.UserRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

        Queue<File> files = new ConcurrentLinkedQueue<File>();

        User user = new User();
        user.setId(new ObjectId().toString());
        user.setUserName("user");
        user.setPassword("12345678");
        user.setSpace(315641556L);
        user.setCreatedTime(LocalDateTime.now());

        userRepository.save(user);

        UserBrief userBrief = new UserBrief();
        userBrief.setUserName(user.getUserName());
        userBrief.setToken(user.getToken());
        userBrief.setId(user.getId());

        Folder rootFolder = new Folder();
        rootFolder.setId(new ObjectId().toString());
        rootFolder.setName("user root folder");
        rootFolder.setLastModifiedTime(LocalDateTime.now());
        rootFolder.setOwner(userBrief);
        rootFolder.setChild(new ArrayList<ResourceBrief>());

        resourceRepository.save(rootFolder);

        FolderBrief rootFolderBrief = new FolderBrief();
        rootFolderBrief.setId(rootFolder.getId());
        rootFolderBrief.setName(rootFolder.getName());

        user.setRootFolder(rootFolderBrief);

        for (int i = 1; i <= 1000; i++) {
            Folder folder = new Folder();
            folder.setId(new ObjectId().toString());
            folder.setName("user folder " + i);
            folder.setLastModifiedTime(LocalDateTime.now());
            folder.setOwner(userBrief);
            folder.setChild(new ArrayList<ResourceBrief>());
            folder.setParent(rootFolderBrief);
            folder.setPath(Collections.singletonList(rootFolderBrief));

            resourceRepository.save(folder);

            FolderBrief folderBrief = new FolderBrief();
            folderBrief.setId(folder.getId());
            folderBrief.setName(folder.getName());

            rootFolder.getChild().add(folderBrief);

            for (int j = 1; j <= 1000; j++) {
                File file = new File();
                file.setId(new ObjectId().toString());
                file.setName("user folder " + i + " file " + j);
                file.setOwner(userBrief);
                file.setLastModifiedTime(LocalDateTime.now());
                file.setParent(folderBrief);
                file.setPath(Arrays.asList(rootFolderBrief, folderBrief));
                file.setMaxVersion(9);
                file.setVersions(new ArrayList<FileVersion>());

                for (int k = 0; k < 10; k++) {
                    FileVersion fileVersion = new FileVersion();
                    fileVersion.setCreatedTime(LocalDateTime.now());
                    fileVersion.setCurrentVersion(false);
                    fileVersion.setDescription("user folder " + i + " file " + j + " version " + k);
                    fileVersion.setInsertedTime(LocalDateTime.now());
                    fileVersion.setLastModifiedTime(LocalDateTime.now());

                    file.getVersions().add(fileVersion);
                }

                files.add(file);

                FileBrief fileBrief = new FileBrief();
                fileBrief.setId(file.getId());
                fileBrief.setName(file.getName());

                folder.getChild().add(fileBrief);
            }

            resourceRepository.save(folder);
        }

        resourceRepository.save(rootFolder);
        userRepository.save(user);

        for (File file : files) {
            resourceRepository.save(file);
        }
    }
}
