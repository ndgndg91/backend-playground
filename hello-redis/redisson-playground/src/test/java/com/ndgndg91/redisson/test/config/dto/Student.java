package com.ndgndg91.redisson.test.config.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
    private String city;
    private List<Integer> marks;
}
