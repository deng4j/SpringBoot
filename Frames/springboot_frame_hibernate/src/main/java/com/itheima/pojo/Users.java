package com.itheima.pojo;



import javax.persistence.*;
import java.util.Date;

/**
 * jpa自动生成相应的表，默认和实体类名相同,@Table指定表名
 */
@Entity
@Table(name = "t_user")
public class Users {

    /**
     * 设置主键，及其策略:GenerationType.AUTO自动增长，会生成主键表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id        ;
    //设置字段名
//    @Column(name = "username")

    private String name      ;
    private String password  ;
    private String email     ;
    private Date birthday  ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
