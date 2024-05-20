package com.example.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Getter
@Setter
@ToString
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String name;
    private int age;
    private String addr;


}