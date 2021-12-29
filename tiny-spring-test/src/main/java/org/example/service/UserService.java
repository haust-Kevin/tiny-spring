package org.example.service;


import org.example.beans.Bean;
import org.example.pojo.User;

@Bean
public class UserService {

    public User getUser() {
        return User.builder().age(12).name("Wang Dingbang").build();
    }

}
