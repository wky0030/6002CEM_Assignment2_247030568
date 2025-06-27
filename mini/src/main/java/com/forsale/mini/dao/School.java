package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    private Integer id,uid;
    private String name, alias,imgs,content,content2,content3,des;
    private Long createTime,updateTime;
    public boolean isSave=false;
    private Double latitude=0d;
    private Double longitude=0d;
}
