package com.forsale.mini.mapper;
import com.forsale.mini.dao.School;
import com.forsale.mini.dao.Msg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchoolMapper {
    School getById(String id);

    List<School> getAll();
    List<School> getAllByKey(String  keyword);
    List<School> getAllByUId(Integer  uid);

    int  deleteSave(Integer uid,Integer fid);
    Integer add(Integer uid,Integer fid);

    int  deleteById(Integer id);

    int updateById(School caoYao);
    Integer addMsg(Msg msg);

    List<Msg> getAllMsgByFid(Integer sid, Integer rid, Integer fid);
    List<Msg> getAllMsg(Integer uid);
    Integer addSchool(School School);
    int  deleteMsgById(Msg msg);
    String  getAbout();
    String  getNews();

}
