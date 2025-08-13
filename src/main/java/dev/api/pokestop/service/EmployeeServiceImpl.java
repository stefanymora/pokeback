package dev.api.pokestop.service;

import dev.api.pokestop.DAO.EmployeeDAO;
import dev.api.pokestop.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;


    @Override
    public String saveEmployee(Employee employee) {
        try {
            return employeeDAO.saveEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployee(String employee) {
        try {
            return employeeDAO.getEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteEmployee(String employee) {
        try {
            return employeeDAO.deleteEmployee(employee);
        } catch (Exception e) {
            return "Error al eliminar el empleado: " + e.getMessage();        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        try {
            return employeeDAO.getAll();
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public String updateEmployee(String id, Employee updateEmployee) {
        try {
            return employeeDAO.updateEmployee(id, updateEmployee);
        } catch (Exception e) {
            return "Error al actualizar el producto: " + e.getMessage();
        }
    }
}
