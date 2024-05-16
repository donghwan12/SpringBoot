package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {
    //bookname과 일치하는 Book LIst 가져오기
    List<Book> findByBookName(String bookName);
}
