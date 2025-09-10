package dev.api.pokestop.service;

import dev.api.pokestop.DTO.EmployeeDTO;
import dev.api.pokestop.entity.Employee;

import java.util.List;

public interface EmployeeService {

    String saveEmployee(Employee employee) ;

    EmployeeDTO getEmployee(String employee);

    String deleteEmployee(String employee);

    List<EmployeeDTO> getAllEmployee();

    String updateEmployee(String id, Employee updateEmployee);

    EmployeeDTO login (String username, String password);
}
