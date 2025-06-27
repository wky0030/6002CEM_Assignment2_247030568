package com.example.sign.bean;

public class LabelInfo {
    private int num;
    private String place;
    private String day;

    @Override
    public String toString() {
        return "LabelInfo{" +
                "num='" + num + '\'' +
                ", place='" + place + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
