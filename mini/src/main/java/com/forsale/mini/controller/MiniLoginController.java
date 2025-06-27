package com.forsale.mini.controller;

import com.forsale.mini.dao.Employee;
import com.forsale.mini.dao.R;
import com.forsale.mini.dao.Warning;
import com.forsale.mini.service.MiniLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MiniLoginController {
    @Autowired
    private MiniLoginService m_MiniLoginService;
    private final static Logger log = LoggerFactory.getLogger(MiniLoginController.class);
    @RequestMapping("/testapi")
    public String test(){
        return "MiniLoginController";
    }


    @RequestMapping("/getAll")
    @ResponseBody
    public R getAll(){
        List<Employee> goodsList = m_MiniLoginService.getAllEmployees();
        return R.ok().data("goodsList",goodsList);
    }

    @RequestMapping(value = "/all")
    public List<Employee> getAllEmployees() {

        List<Employee> all_employees = m_MiniLoginService.getAllEmployees();

        return all_employees;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public R login(@RequestBody Employee employee) {
        log.error("我是 登陆 "+employee);
        List<Employee> all_employees = m_MiniLoginService.getAllEmployees();
        int i = 0;
        Employee newUser=null;
        for (; i < all_employees.size(); i++) {
            Employee item=all_employees.get(i);
            if(item!=null&&
                    item.getPassword()!=null&&
                    item.getUsername()!=null&&
                    item.getUsername().equals(employee.getUsername())&&
                item.getPassword().equals(employee.getPassword())){
                newUser=item;
                break;
            }
        }
        log.error("我是 登陆 "+newUser+"  i="+i);
        if(i<all_employees.size()){
            return R.ok().data("data",newUser);
        }else {
            return R.error().data("fail",i<all_employees.size());
        }
    }
    @RequestMapping("/addUser")
    @ResponseBody
    public R addUser(@RequestBody Employee employee){
        log.error("我是 zhuce "+employee);
        int i;
        if(employee.getId()==null){
            i = m_MiniLoginService.addEmployee(employee);
        }else {
            i = m_MiniLoginService.updateEmployeeById(employee);
        }
        return R.ok().data("success",i>0);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public R deleteUserById(@RequestParam("id")Integer id){
        int i = m_MiniLoginService.deleteEmployeeById(id);
        return R.ok().data("success",i>0);
    }

//    @RequestMapping("/getGoods")
//    @ResponseBody
//    public R getUserById(@RequestParam("id") Integer id){
//        Goods goods = m_MiniLoginService.findGoodsById(id);
//        return R.ok().data("goods",goods);
//    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public R updateUserById(Employee employee){
        int i = m_MiniLoginService.updateEmployeeById(employee);
        return R.ok().data("success",i>0);
    }
}
