package com.example.app.domain.repository;

import com.example.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Entity 명, PK 자료형을 넣어준다
public interface UserRepository extends JpaRepository<User,String> {
}
