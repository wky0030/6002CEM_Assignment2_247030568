package com.example.sign.net;

import java.util.List;

public class MyResponse<T> {
    private int count;
    private List<T> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
