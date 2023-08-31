package com.example.json.domain.jackson;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 空值不传给前端
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class Jackson1Vo implements Serializable {
    /**
     * 表示忽略给前端
     */
    @JsonIgnore
    private Integer id;

    private List<Integer> list;

    /**
     * 用于指定前端传到后台时对应的key值
     */
    @JSONField(name="resType")
    private String aa;

    /**
     * 格式化时间给前台
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    /**
     * 服务端接收前端的时间,并格式化
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date getDate;
}
