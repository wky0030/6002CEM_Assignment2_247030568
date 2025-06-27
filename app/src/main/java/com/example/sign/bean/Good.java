package com.example.sign.bean;

import java.util.List;

public class Good {
    private Integer id;
    private String name;
    private List<GoodDetail> data;

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

    public List<GoodDetail> getData() {
        return data;
    }

    public void setData(List<GoodDetail> data) {
        this.data = data;
    }
}
