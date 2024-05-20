package com.example.app.domain.repository;

import com.example.app.domain.entity.Book;
import com.example.app.domain.entity.Lend;
import com.example.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LendRepository  extends JpaRepository<Lend,Long> {

    //명령법
    //user에서 가져온다
    List<Lend> findByUser(User user);
    //Book에서 가져온다.
    List<Lend> findByBook(Book book);
    List<Lend> findByRageDate(LocalDate rageDate);
    List<Lend> findByReturnDate(LocalDate returnDate);


    //JPQL

}
