package com.denbondd.restaurant.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
public class Dish  implements Serializable {
    private long id;
    private String name;
    private long categoryId;
    private long price;
    private long weight;
    private String description;

}
