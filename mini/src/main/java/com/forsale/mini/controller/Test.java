package com.forsale.mini.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
       System.out.println( sdf.format(d)+"  sss" +d.getTime());
    }
}
