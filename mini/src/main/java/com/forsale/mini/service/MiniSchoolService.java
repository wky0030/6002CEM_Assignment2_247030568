package com.forsale.mini.service;

import com.forsale.mini.dao.School;
import com.forsale.mini.dao.Msg;

import java.util.List;

public interface MiniSchoolService {
    School getById(String id);

    List<School> getAll();
    List<School> getAllByKey(String  keyword);

    List<School> getAllByUId(Integer  uid);


    int  deleteSave(Integer uid, Integer fid);

    Integer add(Integer uid,Integer fid);
    Integer deleteById(Integer id);
    Integer updateById(School School);

    Integer addMsg(Msg msg);

    List<Msg> getAllMsgByFid(Integer sid, Integer rid, Integer fid);

    List<Msg> getAllMsg(Integer uid);

    Integer addSchool(School School);
    Integer deleteMsgById(Msg msg);
    String  getAbout();
    String  getNews();
}
    