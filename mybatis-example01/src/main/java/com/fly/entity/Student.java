package com.fly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) //开启链式调用
public class Student {
    private Integer id;
    private String stuName;
    private String stuNo;
    private Integer age;
    private Integer sex;
    private String address;
    private String birthday;
}
