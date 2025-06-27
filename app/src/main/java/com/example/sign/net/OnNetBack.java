package com.example.sign.net;

public interface OnNetBack <T> {
    void onSuccess(T obj);
    void onFail(String msg);
}
