//package com.ndgndg91.codilitytest.document;
//
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@DiscriminatorValue("INV")
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class Invoice extends Document {
//    @Column(name = "PAY_DATE")
//    private LocalDate paymentDate;
//
//    Invoice(String referenceId, String authorName, LocalDate paymentDate) {
//        super(referenceId, authorName);
//        this.paymentDate = paymentDate;
//    }
//
//    String getAuthorName() {
//        return super.getAuthorName();
//    }
//}
//
