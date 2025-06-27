package com.example.sign.util;

public class DistanceCalculator {
    /**
     * 计算两个经纬度之间的距离（单位：米）
     * @param lat1 位置1纬度
     * @param lon1 位置1经度
     * @param lat2 位置2纬度
     * @param lon2 位置2经度
     * @return 距离（米）
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 地球半径（米）
        final int R = 6371000;

        // 将纬度、经度转换为弧度
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // 使用Haversine公式计算距离
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}