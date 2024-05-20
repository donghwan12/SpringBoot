package com.example.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Book")
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookcode")
    private Long bookCode;
    @Column(name="bookname")
    private String bookName;
    @Column(name="publisher")
    private String publisher;
    @Column(name="isbn",length = 100)
    private String isbn;

}
