package com.ndgndg91.redisson.test.assignment;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOrder {
    private int id;
    private Category category;
}
