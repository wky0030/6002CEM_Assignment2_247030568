package com.example.sign.util;

import android.location.Location;
import android.os.Build;

import com.example.sign.bean.School;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolDistanceHelper {
    /**
     * 计算学校与当前位置的距离并排序
     * @param myLocation 当前位置
     * @param schoolList 学校列表
     * @return 按距离从近到远排序的学校列表
     */
    public static List<SchoolWithDistance> calculateAndSort(Location myLocation, List<School> schoolList) {
        List<SchoolWithDistance> result = new ArrayList<>();

        // 计算每个学校的距离
        for (School school : schoolList) {
            if(myLocation!=null){

                double distance = DistanceCalculator.calculateDistance(
                        myLocation.getLatitude(),
                        myLocation.getLongitude(),
                        school.getLatitude(),
                        school.getLongitude());
                result.add(new SchoolWithDistance(school, distance));
            }else {

                result.add(new SchoolWithDistance(school, 0));
            }
        }

        // 按距离从近到远排序
        Collections.sort(result, new Comparator<SchoolWithDistance>() {
            @Override
            public int compare(SchoolWithDistance o1, SchoolWithDistance o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        return result;
    }

    /**
     * Java 8+ 简洁写法
     */
    public static List<SchoolWithDistance> calculateAndSortJava8(Location myLocation, List<School> schoolList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return schoolList.stream()
                    .map(school -> new SchoolWithDistance(
                            school,
                            DistanceCalculator.calculateDistance(
                                    myLocation.getLatitude(),
                                    myLocation.getLongitude(),
                                    school.getLatitude(),
                                    school.getLongitude())))
                    .sorted(Comparator.comparingDouble(SchoolWithDistance::getDistance))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}