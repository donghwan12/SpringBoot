package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import com.example.app.domain.entity.Lend;
import com.example.app.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LendRepositoryTest {

    @Autowired
    LendRepository lendRepository;

    @Test
    public void t1(){
        User user=new User("이동환","1597","ROLE_USER");
        Book book=new Book(1011L,"화산귀환","1111-111",	"비기");

        Lend lend=new Lend();
        lend.setRageDate(LocalDate.now());

        lend.setUser(user);
        lend.setBook(book);

        lendRepository.save(lend);
    }

    @Test
    public void t2(){
        List<Lend> list=lendRepository.findAll();
        System.out.println(list);
    }

    @Test
    public void t3(){
        List<Lend> list=lendRepository.findByUser(new User("이동환","1597","ROLE_USER"));
        System.out.println(list);

    }
}