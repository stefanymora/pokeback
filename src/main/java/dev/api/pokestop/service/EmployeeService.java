package dev.api.pokestop.service;

import dev.api.pokestop.entity.Employee;

import java.util.List;

public interface EmployeeService {

    String saveEmployee(Employee employee) ;

    Employee getEmployee(String employee);

    String deleteEmployee(String employee);

    List<Employee> getAllEmployee();

    String updateEmployee(String id, Employee updateEmployee);
}
