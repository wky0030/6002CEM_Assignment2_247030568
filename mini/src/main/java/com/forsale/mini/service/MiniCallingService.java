package com.forsale.mini.service;

import com.forsale.mini.dao.Calling;
import com.forsale.mini.dao.Device;

import java.util.List;

public interface MiniCallingService {
    Calling getById(Integer id);
    Calling getLastByDeviceId(String id);

    List<Calling> getAll(Integer pageNum,Integer pageSize);
    Long getCount( );

    Integer add(Calling device);
    Integer deleteById(Integer id);
    Integer updateById(Calling device);

}
