//package com.ndgndg91.codilitytest.person;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "USERS")
//@PrimaryKeyJoinColumn(name = "PERSON_ID")
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Getter
//public class AppUser extends Person {
//    @Column(name = "LOGIN")
//    private String login;
//
//    @Column(name = "USER_NAME")
//    private String firstName;
//
//    @Column(name = "USER_SURNAME")
//    private String lastName;
//
//    AppUser(Long sessionId, String login, String firstName, String lastName) {
//        this.login = login;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    String getLogin() {
//        return login;
//    }
//}
//
