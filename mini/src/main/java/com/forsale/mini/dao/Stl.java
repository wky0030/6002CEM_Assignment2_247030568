package com.forsale.mini.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stl {
    private Integer id;
    private String name;//文件名称
    private String dirName;//目录名称
    private long createTime;//创建时间
    private long updateTime;//更新时间
    private String des;//描述
}
