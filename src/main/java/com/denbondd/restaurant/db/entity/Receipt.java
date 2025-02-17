package com.denbondd.restaurant.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Receipt implements Serializable {
    private long id;
    private long userId;
    private Status status;
    private int total;
    private long managerId;
    private Date createDate;
    private List<Dish> dishes;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dish {
        private long id;
        private String name;
        private int count;
        private int price;

    }
}

