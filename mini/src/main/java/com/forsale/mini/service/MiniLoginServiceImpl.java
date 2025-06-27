package com.forsale.mini.service;

import com.forsale.mini.dao.Employee;
import com.forsale.mini.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class MiniLoginServiceImpl implements MiniLoginService{
    @Autowired
    private EmployeeMapper m_EmployeeMapper;

    @Override
    public Employee getEmployeeById(Integer id) {
        return m_EmployeeMapper.getEmployeeById(id);
    }

    @Override
    public Employee getEmployeeByImg(String img) {
        return m_EmployeeMapper.getEmployeeByImg(img);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return m_EmployeeMapper.getAllEmployees();
    }

    @Override
    public Integer addEmployee(Employee employee) {
        return m_EmployeeMapper.addEmployee(employee);
    }

    @Override
    public Integer deleteEmployeeById(Integer id) {

        return m_EmployeeMapper.deleteEmployeeById(id);
    }

    @Override
    public Integer updateEmployeeById(Employee employee) {
        return m_EmployeeMapper.updateEmployeeById(employee);
    }


}
