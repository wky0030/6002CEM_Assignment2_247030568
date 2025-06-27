package com.forsale.mini.mapper;
import com.forsale.mini.dao.Calling;
import com.forsale.mini.dao.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface CallingMapper {
    Calling getById(Integer id);
    Calling getLastByDeviceId(String id);

    List<Calling> getAll(Integer pageNum, Integer pageSize);

    int add(Calling calling);
    long getCount();

    int  deleteById(Integer id);

    int updateById(Calling calling);

}
