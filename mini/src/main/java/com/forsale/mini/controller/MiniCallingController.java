package com.forsale.mini.controller;

import com.forsale.mini.dao.Calling;
import com.forsale.mini.dao.R;
import com.forsale.mini.service.MiniCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MiniCallingController {

    private final static Logger log = LoggerFactory.getLogger(MiniCallingController.class);

    @Autowired
    private MiniCallingService m_Service;


    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllCallings")
    @ResponseBody
    public R getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
//        List<Calling> goodsList = m_Service.getAll();
        log.error("我是 getAllCallings  "+ pageNum+"  -- "+pageSize+" -- "+(pageNum-1)*pageSize+"  --  "+pageNum*pageSize);
        List<Calling> goodsList = m_Service.getAll((pageNum-1)*pageSize,pageSize);
        long count =m_Service.getCount();
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("devices", goodsList);
        log.error("我是 getAllCallings  "+count+goodsList.size());
        return R.ok().data(map);
    }
    @RequestMapping("/getCallingById")
    @ResponseBody
    public R getCallingById(@RequestParam("id")Integer id){
        Calling device = m_Service.getById(id);
        return R.ok().data("calling",device);
    }
    @RequestMapping("/getLastByDeviceId")
    @ResponseBody
    public R getLastByDeviceId(@RequestParam("id")String id){
        Calling device = m_Service.getLastByDeviceId(id);
        return R.ok().data("calling",device);
    }
    @RequestMapping("/addCalling")
    @ResponseBody
    public R addDevice(Calling item){
        int i = m_Service.add(item);
        return R.ok().data("success",i>0);
    }

    @RequestMapping("/deleteCalling")
    @ResponseBody
    public R deleteDeviceById(@RequestParam("id")Integer id){
        int i = m_Service.deleteById(id);
        return R.ok().data("success",i>0);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/updateCalling")
    @ResponseBody
    public R updateCalling(@RequestBody Calling device){
        log.error("我是 updateCalling "+device);
        int i =0;
        if(device.getId()==null){
            i = m_Service.add(device);
        }else {
            i = m_Service.updateById(device);
        }

        return R.ok().data("success",i>0);
    }
}
