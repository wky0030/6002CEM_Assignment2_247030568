package com.forsale.mini.mapper;
import com.forsale.mini.dao.CallType;
import com.forsale.mini.dao.Calling;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CallTypeMapper {
    CallType getById(Integer id);

    List<CallType> getAll(Integer pageNum,Integer pageSize);

    int add(CallType callType);
    long getCount();

    int  deleteById(Integer id);

    int updateById(CallType callType);

}
