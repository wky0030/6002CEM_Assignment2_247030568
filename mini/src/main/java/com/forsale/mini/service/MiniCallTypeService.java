package com.forsale.mini.service;

import com.forsale.mini.dao.CallType;
import com.forsale.mini.dao.Calling;

import java.util.List;

public interface MiniCallTypeService {
    CallType getById(Integer id);

    List<CallType> getAll(Integer pageNum,Integer pageSize);

    Integer add(CallType device);
    Long getCount( );
    Integer deleteById(Integer id);
    Integer updateById(CallType device);

}
