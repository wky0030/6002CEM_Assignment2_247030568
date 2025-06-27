package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calling {
    private Integer id;
    private String deviceName;//设备名称
    private String deviceId;//设备id
    private long callTime;//设备呼叫时间
    private long answerTime;//设备响应时间
    private long finishTime;//设备完成呼叫时间
    private int typeId;//呼叫类型
    private String typeName;//呼叫名称
    private String des;//其它描述
}
