package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    private Integer id,sid,rid,fid;
    private Long createTime,updateTime;
    private String des,name,surl,rurl;
}
