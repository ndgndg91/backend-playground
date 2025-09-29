//package com.ndgndg91.codilitytest.task;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "EPICS")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class Epic extends Task {
//    @Column(name = "PROJECT_ID")
//    private Long projectId;
//    @Column(name = "PRODUCT_OWNER_NAME")
//    private String productOwner;
//
//    Epic(String name, String author, Long projectId, String productOwner) {
//        super(name, author);
//        this.projectId = projectId;
//        this.productOwner = productOwner;
//    }
//
//    String getName() {
//        return super.getName();
//    }
//
//    @Override
//    public void setAuthor(String author) {
//        super.setAuthor(author);
//    }
//}
