package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String username;
    private String phone;
    private String password;
    private String img;
    private String utype;
}
