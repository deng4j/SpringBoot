package com.itheima.vote.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Star implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer ticket;

}
