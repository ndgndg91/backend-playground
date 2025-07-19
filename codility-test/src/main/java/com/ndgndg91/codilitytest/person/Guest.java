//package com.ndgndg91.codilitytest.person;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "GUESTS")
//@PrimaryKeyJoinColumn(name = "PERSON_ID")
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Getter
//public class Guest extends Person{
//    @Column(name = "TEMPORARY_NAME")
//    private String tempName;
//
//    Guest(Long sessionId, String tempName) {
//        this.tempName = tempName;
//    }
//
//    String getTempName() {
//        return tempName;
//    }
//}
