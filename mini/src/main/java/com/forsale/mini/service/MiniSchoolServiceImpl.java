package com.forsale.mini.service;

import com.forsale.mini.dao.School;
import com.forsale.mini.dao.Msg;
import com.forsale.mini.mapper.SchoolMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniSchoolServiceImpl implements MiniSchoolService {
    private final SchoolMapper m_SchoolMapper;

    public MiniSchoolServiceImpl(SchoolMapper m_SchoolMapper) {
        this.m_SchoolMapper = m_SchoolMapper;
    }

    @Override
    public School getById(String id) {
        return m_SchoolMapper.getById(id);
    }
    @Override
    public   List<School>  getAllByKey(String id) {
        return m_SchoolMapper.getAllByKey(id);
    }


    @Override
    public   List<School> getAllByUId(Integer  uid) {
        return m_SchoolMapper.getAllByUId(uid);
    }


    @Override
    public int  deleteSave(Integer uid, Integer fid) {
        return m_SchoolMapper.deleteSave(uid,fid);
    }


    @Override
    public List<School> getAll() {
        return m_SchoolMapper.getAll();
    }

    @Override
    public Integer add(Integer uid,Integer fid) {
        return m_SchoolMapper.add(uid, fid);
    }

    @Override
    public Integer deleteById(Integer id) {
        return m_SchoolMapper.deleteById(id);
    }

    @Override
    public Integer updateById(School School) {
        return m_SchoolMapper.updateById(School);
    }

    @Override
    public Integer addMsg(Msg msg) {
        return m_SchoolMapper.addMsg(msg);
    }

    @Override
    public List<Msg> getAllMsgByFid(Integer sid, Integer rid, Integer fid) {
        return m_SchoolMapper.getAllMsgByFid(sid,rid,fid);
    }
    @Override
    public List<Msg> getAllMsg(Integer uid) {
        return m_SchoolMapper.getAllMsg(uid);
    }

    @Override
    public Integer addSchool(School School) {
        return m_SchoolMapper.addSchool(School);

    }

    @Override
    public Integer deleteMsgById(Msg msg) {
        return m_SchoolMapper.deleteMsgById(msg);
    }

    @Override
    public String getAbout() {
        return m_SchoolMapper.getAbout();
    }

    @Override
    public String getNews() {
        return m_SchoolMapper.getNews();
    }


}
