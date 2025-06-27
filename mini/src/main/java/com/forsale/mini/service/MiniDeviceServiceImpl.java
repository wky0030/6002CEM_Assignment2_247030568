package com.forsale.mini.service;

import com.forsale.mini.dao.Device;
import com.forsale.mini.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniDeviceServiceImpl implements MiniDeviceService{
    private final DeviceMapper m_DeviceMapper;

    public MiniDeviceServiceImpl(DeviceMapper m_DeviceMapper) {
        this.m_DeviceMapper = m_DeviceMapper;
    }

    @Override
    public Device getById(String id) {
        return m_DeviceMapper.getById(id);
    }


    @Override
    public List<Device> getAll() {
        return m_DeviceMapper.getAll();
    }

    @Override
    public Integer add(Device device) {
        return m_DeviceMapper.add(device);
    }

    @Override
    public Integer deleteById(Integer id) {
        return m_DeviceMapper.deleteById(id);
    }

    @Override
    public Integer updateById(Device device) {
        return m_DeviceMapper.updateById(device);
    }


}
