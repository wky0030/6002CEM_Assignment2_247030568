package com.forsale.mini.service;

import com.forsale.mini.dao.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MiniLoginService {
    Employee getEmployeeById(Integer id);
    Employee getEmployeeByImg(String img);

    List<Employee> getAllEmployees();

    Integer addEmployee(Employee employee);
    Integer deleteEmployeeById(Integer id);
    Integer updateEmployeeById(Employee employee);

}
