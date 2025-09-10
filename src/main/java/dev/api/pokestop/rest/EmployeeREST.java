package dev.api.pokestop.rest;


import dev.api.pokestop.DTO.EmployeeDTO;
import dev.api.pokestop.entity.Employee;
import dev.api.pokestop.entity.Product;
import dev.api.pokestop.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeREST {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employees = employeeServiceImpl.getAllEmployee();
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{employee}")
    public ResponseEntity<EmployeeDTO> getEmployeeByName(@PathVariable String employee) {
        EmployeeDTO employee1 = employeeServiceImpl.getEmployee(employee);

        if (employee1 != null) {
            return ResponseEntity.ok(employee1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{employee}")
    public ResponseEntity<String> updateEmployee(@PathVariable String employee, @RequestBody Employee updateEmployee) {
        String result = employeeServiceImpl.updateEmployee(employee, updateEmployee);

        if (result.startsWith("Empleado actualizado")) {
            return ResponseEntity.ok(result);
        } else if (result.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping
    public ResponseEntity<String> newEmployee(@RequestBody Employee employeeB) {
        if (employeeB.getName() == null || employeeB.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre del empleado obligatorio.");
        }

        String employeeMessage = employeeServiceImpl.saveEmployee(employeeB);
        return ResponseEntity.ok(employeeMessage);
    }


    @DeleteMapping("/{employee}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employee) {
        String result = employeeServiceImpl.deleteEmployee(employee);

        if (result.contains("Empleado eliminado correctamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }


    // ---------------------------------------------------------------------------------------

    @PostMapping("/login")
    public ResponseEntity<EmployeeDTO> login(@RequestBody Employee employeeB) {
        EmployeeDTO employee =  employeeServiceImpl.login(employeeB.getUsername(), employeeB.getPassword());
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employee);
        }
    }


}
