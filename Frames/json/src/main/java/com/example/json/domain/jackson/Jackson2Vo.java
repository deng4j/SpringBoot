package com.example.json.domain.jackson;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

import java.io.Serializable;

/**
 * @JsonFilter("empFilter"):条件过滤某些字段,empFilter是过滤器名
 */
@JsonFilter("empFilter")
@Data
public class Jackson2Vo implements Serializable {

    private Integer id;

    private String name;

    private String sex;
}
