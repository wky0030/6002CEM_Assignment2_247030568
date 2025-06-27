package com.example.sign.util;

import com.example.sign.bean.School;

import java.util.Locale;

public class SchoolWithDistance {
    private School school;
    private double distance; // 单位：米

    public SchoolWithDistance(School school, double distance) {
        this.school = school;
        this.distance = distance;
    }

    public School getSchool() {
        return school;
    }

    public double getDistance() {
        return distance;
    }

    // 获取格式化后的距离字符串（例如：1.2km 或 500m）
    public String getFormattedDistance() {
        if (distance >= 1000) {
            return String.format(Locale.getDefault(), "%.1fkm", distance / 1000);
        } else {
            return String.format(Locale.getDefault(), "%.0fm", distance);
        }
    }
}