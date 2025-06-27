package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private Integer id;
    private String deviceName;//设备名称
    private String deviceId;//设备名称
    private long lastTime;//设备最后在线时间
    private String pwd;//设备密码
    private Calling calling;
}
