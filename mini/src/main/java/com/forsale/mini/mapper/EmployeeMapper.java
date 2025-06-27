package com.forsale.mini.mapper;
import com.forsale.mini.dao.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface EmployeeMapper {
    Employee getEmployeeById(Integer id);
    Employee getEmployeeByImg(String img);

    List<Employee> getAllEmployees();

    int addEmployee(Employee employee);

    int  deleteEmployeeById(Integer  id);

    int updateEmployeeById(Employee employee);

}
