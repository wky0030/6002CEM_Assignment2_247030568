package com.forsale.mini.dao;

public class Warning {
    private long time;
    private double xyacc;
    private String username;

    @Override
    public String toString() {
        return "Warning{" +
                "time=" + time +
                ", xyacc=" + xyacc +
                ", username='" + username + '\'' +
                '}';
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getXyacc() {
        return xyacc;
    }

    public void setXyacc(double xyacc) {
        this.xyacc = xyacc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
