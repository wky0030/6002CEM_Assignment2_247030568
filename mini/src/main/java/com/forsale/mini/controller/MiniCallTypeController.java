package com.forsale.mini.controller;

import com.forsale.mini.dao.CallType;
import com.forsale.mini.dao.R;
import com.forsale.mini.service.MiniCallTypeService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MiniCallTypeController {

    private final static Logger log = LoggerFactory.getLogger(MiniCallTypeController.class);

    @Autowired
    private MiniCallTypeService m_Service;

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllCallTypes")
    @ResponseBody
    public R getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        log.error("我是 getAllCallTypes  "+pageNum+pageSize);
        List<CallType> goodsList = m_Service.getAll((pageNum-1)*pageSize,pageSize);
        long count =m_Service.getCount();
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("devices", goodsList);
        return R.ok().data(map);
    }
    @RequestMapping("/getCallTypeById")
    @ResponseBody
    public R getDeviceById(@RequestParam("id")Integer id){
        CallType item = m_Service.getById(id);
        return R.ok().data("CallType",item);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/addCallType")
    @ResponseBody
    public R addDevice(CallType item){
        int i = m_Service.add(item);
        return R.ok().data("success",i>0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/deleteCallType")
    @ResponseBody
    public R deleteDeviceById(@RequestParam("id")Integer id){
        int i = m_Service.deleteById(id);
        return R.ok().data("success",i>0);
    }


    @RequestMapping("/updateCallType")
    @ResponseBody
    public R updateDeviceById(CallType item){
        int i = m_Service.updateById(item);
        return R.ok().data("success",i>0);
    }
}
