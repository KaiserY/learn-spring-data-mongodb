package com.kaisery.fs.controller;

import com.kaisery.fs.entity.User;
import com.kaisery.fs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public User findUserByUserName(@PathVariable("userName") String userName) {
        User user = userRepository.findByUserName(userName);
        return user;
    }
}
