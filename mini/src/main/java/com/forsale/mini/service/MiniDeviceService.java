package com.forsale.mini.service;

import com.forsale.mini.dao.Device;

import java.util.List;

public interface MiniDeviceService {
    Device getById(String id);

    List<Device> getAll();

    Integer add(Device device);
    Integer deleteById(Integer id);
    Integer updateById(Device device);

}
