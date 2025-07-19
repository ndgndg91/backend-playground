//package com.ndgndg91.codilitytest.document;
//
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "DOCUMENTS")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "DOC_TYPE", discriminatorType = DiscriminatorType.STRING)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "DOC_ID")
//    private Long id;
//
//    @Column(name = "REFERENCE_ID")
//    private String referenceId;
//
//    @Column(name = "AUTHOR_NAME")
//    private String authorName;
//
//    Document(String referenceId, String authorName) {
//        this.referenceId = referenceId;
//        this.authorName = authorName;
//    }
//
//    String getAuthorName() {
//        return authorName;
//    }
//}
//
