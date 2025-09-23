package dev.api.pokestop.service;

import dev.api.pokestop.DTO.EmployeeDTO;
import dev.api.pokestop.entity.Employee;
import dev.api.pokestop.entity.Sale;
import org.springframework.stereotype.Service;

import java.util.List;
public interface SaleService {
    String saveSale(Sale sale) ;

    Sale getSale(String sale);

    String deleteSale(String sale);

    List<Sale> getAllSales();


}
