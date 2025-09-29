//package com.ndgndg91.codilitytest.document;
//
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@DiscriminatorValue("REP")
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class Report extends Document {
//    @Column(name = "BEGIN")
//    private LocalDate startDate;
//
//    @Column(name = "FINISH")
//    private LocalDate endDate;
//
//    Report(String referenceId, String authorName, LocalDate startDate, LocalDate endDate) {
//        super(referenceId, authorName);
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    String getAuthorName() {
//        return super.getAuthorName();
//    }
//}
