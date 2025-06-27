package com.forsale.mini.service;

import com.forsale.mini.dao.Calling;
import com.forsale.mini.dao.Device;
import com.forsale.mini.mapper.CallingMapper;
import com.forsale.mini.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniCallingServiceImpl implements MiniCallingService{
    private final CallingMapper m_CallingMapper;

    public MiniCallingServiceImpl(CallingMapper m_CallingMapper) {
        this.m_CallingMapper = m_CallingMapper;
    }

    @Override
    public Calling getById(Integer id) {
        return m_CallingMapper.getById(id);
    }

    @Override
    public Calling getLastByDeviceId(String id) {
        return m_CallingMapper.getLastByDeviceId(id);
    }


    @Override
    public List<Calling> getAll(Integer pageNum,Integer pageSize) {
        return m_CallingMapper.getAll(pageNum,pageSize);
    }
    @Override
    public Long getCount() {
        return  m_CallingMapper.getCount();
    }
    @Override
    public Integer add(Calling calling) {
        return m_CallingMapper.add(calling);
    }

    @Override
    public Integer deleteById(Integer id) {
        return m_CallingMapper.deleteById(id);
    }

    @Override
    public Integer updateById(Calling device) {
        return m_CallingMapper.updateById(device);
    }


}
