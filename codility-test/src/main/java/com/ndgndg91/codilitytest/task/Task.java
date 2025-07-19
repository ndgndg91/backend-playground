//package com.ndgndg91.codilitytest.task;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@MappedSuperclass
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Setter
//public abstract class Task {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "TASK_Id")
//    private Long id;
//    @Column(name = "NAME")
//    private String name;
//    @Column(name = "AUTHOR")
//    private String author;
//
//    Task(String name, String author) {
//        this.name = name;
//        this.author = author;
//    }
//
//    String getName() {
//        return name;
//    }
//}
//
