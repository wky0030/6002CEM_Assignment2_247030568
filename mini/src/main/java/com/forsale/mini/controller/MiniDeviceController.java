package com.forsale.mini.controller;

import com.forsale.mini.dao.Device;
import com.forsale.mini.dao.R;
import com.forsale.mini.service.MiniCallingService;
import com.forsale.mini.service.MiniDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class MiniDeviceController {

    private final static Logger log = LoggerFactory.getLogger(MiniDeviceController.class);

    @Autowired
    private MiniCallingService m_CallService;
    @Autowired
    private MiniDeviceService m_Service;
    @RequestMapping("/testapiDevices")
    public String test(){
        return "123";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getAllDevices")
    @ResponseBody
    public R getAllDevices(){
        List<Device> arr = m_Service.getAll();
        for (int i = 0; i <arr.size() ; i++) {
            Device device=arr.get(i);
            device.setCalling(m_CallService.getLastByDeviceId(device.getDeviceId()));
        }
        return R.ok().data("devices",arr);
    }
    @RequestMapping("/getDeviceById")
    @ResponseBody
    public R getDeviceById(@RequestParam("deviceId")String deviceId){
        Device device = m_Service.getById(deviceId);
        device.setCalling(m_CallService.getLastByDeviceId(device.getDeviceId()));
        device.setLastTime(new Date().getTime());
        m_Service.updateById(device);
        return R.ok().data("device",device);
    }

    @RequestMapping("/addDevice")
    @ResponseBody
    public R addDevice(Device item){
        log.error("我是 addDevice 添加"+item);
        int i = m_Service.add(item);
        return R.ok().data("success",i>0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/deleteDevice")
    @ResponseBody
    public R deleteDeviceById(@RequestParam("id")Integer id){
        int i = m_Service.deleteById(id);
        return R.ok().data("success",i>0);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/updateDevice")
    @ResponseBody
    public R updateDeviceById(@RequestBody  Device device){
        log.error("device="+device.toString());
        String deviceId = device.getDeviceId();
        Device newDevice = m_Service.getById(deviceId);
        int i=0;
        if(newDevice!=null){
            i = m_Service.updateById(device);
        }else {
            i = m_Service.add(device);
        }

        return R.ok().data("success",i>0);
    }
}
