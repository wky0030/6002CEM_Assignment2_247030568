package com.example.sign.net;

import com.example.sign.bean.User;

import java.util.List;

public class MyUser<T> {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
