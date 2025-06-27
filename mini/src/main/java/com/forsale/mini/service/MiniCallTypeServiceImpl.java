package com.forsale.mini.service;

import com.forsale.mini.dao.CallType;
import com.forsale.mini.dao.Calling;
import com.forsale.mini.mapper.CallTypeMapper;
import com.forsale.mini.mapper.CallingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniCallTypeServiceImpl implements MiniCallTypeService{
    private final CallTypeMapper m_CallingMapper;

    public MiniCallTypeServiceImpl(CallTypeMapper m_CallingMapper) {
        this.m_CallingMapper = m_CallingMapper;
    }

    @Override
    public CallType getById(Integer id) {
        return m_CallingMapper.getById(id);
    }


    @Override
    public List<CallType> getAll(Integer pageNum,Integer pageSize) {
        return m_CallingMapper.getAll(pageNum,pageSize);
    }

    @Override
    public Integer add(CallType calling) {
        return m_CallingMapper.add(calling);
    }

    @Override
    public Long getCount() {
        return  m_CallingMapper.getCount();
    }

    @Override
    public Integer deleteById(Integer id) {
        return m_CallingMapper.deleteById(id);
    }

    @Override
    public Integer updateById(CallType device) {
        return m_CallingMapper.updateById(device);
    }


}
