//package com.ndgndg91.codilitytest.person;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "PERSONS")
//@Inheritance(strategy = InheritanceType.JOINED)
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public abstract class Person {
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    @Column(name = "PERSON_ID")
//    private Long id;
//
//    @Column(name = "SESSION_ID")
//    private Long sessionId;
//
//    protected Person(Long sessionId) {
//        this.sessionId = sessionId;
//    }
//}
//
