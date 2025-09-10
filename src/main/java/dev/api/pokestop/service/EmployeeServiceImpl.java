package dev.api.pokestop.service;

import dev.api.pokestop.DAO.EmployeeDAO;
import dev.api.pokestop.DTO.EmployeeDTO;
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
    public EmployeeDTO getEmployee(String employee) {
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
    public List<EmployeeDTO> getAllEmployee() {
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

    @Override
    public EmployeeDTO login(String username, String password) {

        try{
           boolean found =  employeeDAO.login(username, password);
           if (found){
                EmployeeDTO employee = employeeDAO.getUserByUsername(username);
               return employee;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
