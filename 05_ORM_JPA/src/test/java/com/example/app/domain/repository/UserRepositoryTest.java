package com.example.app.domain.repository;

import com.example.app.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insert(){
        User user=User.builder()
                .username("이동환")
                .password("1234")
                .role("학생")
                .build();
        User result=userRepository.save(user);
        System.out.println(user);
        System.out.println(result);
    }
    @Test
    public void update(){
        User user=User.builder()
                .username("이동환")
                .password("1597")
                .role("웹툰")
                .build();
        userRepository.save(user);
    }

}