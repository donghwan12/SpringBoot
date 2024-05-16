package com.example.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="username",length = 100)
    private String username;
    private String password;
    private String role;
}
