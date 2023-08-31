package com.example.json.domain.jackson;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@JSONType(includes = {"name","hireDate","als","sex"},ignores = {"age"})
@Data
public class Jackson3Vo implements Serializable {

    private String name;

    private int age;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date hireDate;


    /**
     * name:配置序列化的名称
     * ordinal:排序
     */
    @JSONField(name="gender",ordinal = 1)
    private String sex;

    @JSONField(ordinal = 2)
    private String als;

    private List<Integer> list;


}
