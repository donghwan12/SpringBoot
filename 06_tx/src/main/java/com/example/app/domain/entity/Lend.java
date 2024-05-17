package com.example.app.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lend")
public class Lend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //User 1:n Lend테이블과연결
    @ManyToOne
    @JoinColumn(name = "username",foreignKey = @ForeignKey(name="FK_LEND_USER",
    foreignKeyDefinition = "FOREIGN KEY(username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    //Book 1:n Lden 관계
    @ManyToOne
    @JoinColumn(name = "bookcode",foreignKey = @ForeignKey(name="FK_LEND_BOOK",
            foreignKeyDefinition = "FOREIGN KEY(bookcode) REFERENCES book(bookcode) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Book book;

    @Column(name="ragedate")
    private LocalDate rageDate;

    @Column(name="returndate")
    private LocalDate returnDate;

}
