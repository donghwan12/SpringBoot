package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void t1(){
        assertNotNull(bookRepository);
    }

    @Test
    public void insert(){
        Book book=Book.builder()
                .bookCode(1011L)
                .bookName("이것이 JAVA다")
                .publisher("한빛미디어")
                .isbn("1111-1111")
                .build();
       Book result =bookRepository.save(book);
        System.out.println(book);
        System.out.println(result);
    }

    @Test
    public void update(){
        Book book=Book.builder()
                .bookCode(1011L)
                .bookName("화산귀환")
                .publisher("네이버")
                .isbn("1111-1212")
                .build();
        bookRepository.save(book);
    }
    @Test
    public void selectOne(){
        Optional<Book> bookOptional=bookRepository.findById(1011L);
        if(!bookOptional.isEmpty()){
            Book book=bookOptional.get();
            System.out.println(book);
        }
    }
    @Test
    public void selectAll(){
        List<Book>bookList= bookRepository.findAll();
        if(!bookList.isEmpty()){
           bookList.forEach(book->System.out.println(book));

        }
    }

    @Test
    public void delete(){
        Book book=Book.builder()
                .bookCode(1010L)
                .bookName("이것이 JAVA다")
                .publisher("한빛미디어")
                .isbn("1111-1111")
                .build();
        bookRepository.delete(book);
    }

    //함수추가해서 확인
    @Test
    public void SelectByBookname(){
        List<Book> list=bookRepository.findByBookName("캐슬");
        list.forEach(dto->{System.out.println(dto);});
    }
}