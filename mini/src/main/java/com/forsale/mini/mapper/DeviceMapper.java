package com.forsale.mini.mapper;
import com.forsale.mini.dao.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {
    Device getById(String id);

    List<Device> getAll();

    int add(Device device);

    int  deleteById(Integer id);

    int updateById(Device device);

}
