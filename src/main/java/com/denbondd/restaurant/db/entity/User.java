package com.denbondd.restaurant.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//
public class User implements Serializable {

    private long id;
    private String login;
    private long roleId;
    private Date createDate;

//


}
